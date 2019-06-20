package com.yb.annotation.log.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.yb.annotation.log.server.annotation.LogRecord;
import com.yb.annotation.log.server.model.LoginUser;
import com.yb.annotation.log.server.model.OperateLog;
import com.yb.annotation.log.server.utils.LoginUserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.lang.reflect.Method;

/**
 * 注解@within(com.cxh.study.aop.controller.UserAccessAnnotation)
 * 表示拦截含有com.cxh.study.aop.controller.UserAccessAnnotation这个注解的类中所有方法
 * 注解@annotation(com.cxh.study.aop.controller.UserAccessAnnotation)
 * 表示拦截含有com.cxh.study.aop.controller.UserAccessAnnotation这个注解的方法
 * -----------这里其实可以把注解写成内部类的形式,这样的话,只需要引入一个类,就可以直接使用了-----------
 *
 * @author biaoyang
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
@EnableBinding(Processor.class)
public class LogRecordHandler {
    private final Processor processor;

    /**
     * 切入点签名,切入点表达式,可以指定包范围,可以指定注解,还可以两个一起用
     */
    @Pointcut(value = "@annotation(com.yb.annotation.log.server.annotation.LogRecord)")
    public void logRecordPointcut() {
    }

    /**
     * 这里只用了环绕切的方式,如有需要,可另行添加其他的方式
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "logRecordPointcut()")
    @Transactional(rollbackFor = Exception.class)
    public Object logRecordAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取签名
        Signature signature = joinPoint.getSignature();
        //判断签名是否属性指定的签名类型--->这里我只处理方法的签名(注解也只允许在方法上注解)
        if (signature instanceof MethodSignature) {
            //获取方法签名
            Method method = ((MethodSignature) signature).getMethod();
            //获取方法上的自定义注解
            LogRecord annotation = method.getAnnotation(LogRecord.class);
            //获取注解的参数值
            String operateContent = annotation.operateContent();
            //通过用户工具获取登录用户的相关信息(有过滤器处理用户登录设置)
            LoginUser loginUser = LoginUserUtils.getUser().orElseThrow(() -> new AuthenticationException("无效的用户登录信息"));
            //获取日志对象实例
            OperateLog operateLog = new OperateLog();
            //设置操作内容
            operateLog.setOperateContent(operateContent);
            //拷贝对应属性值到日志实体对象
            BeanUtils.copyProperties(loginUser, operateLog, "id", "operateTime", "operateContent");
            //1.直接保存到数据库,其实这里应该是互斥的,有一种方式处理日志数据即可
            operateLog.insert();
            //2.推送到kafka等 中间件(有其他服务处理)
            boolean send = processor.output()
                    .send(MessageBuilder.withPayload(JSONObject.toJSONString(operateLog)).build());
            //输出日志
            log.info(send ? "推送操作日志到kafka成功" : "推送操作日志到kafka失败");
        }
        //继续执行下一个通知或目标方法调用,参数是Object[]类型的
        return joinPoint.proceed();
    }

}
