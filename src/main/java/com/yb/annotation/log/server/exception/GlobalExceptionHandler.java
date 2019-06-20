package com.yb.annotation.log.server.exception;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author biaoyang
 * Date: 2019/4/8 0008
 * Description: 接口统一异常捕捉类
 */
@Slf4j
@Profile({"test", "dev"})
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 这个是用来捕捉接口方法里的单个参数校验异常的捕捉方法
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public JSONObject constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        final String message = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .reduce((s, s2) -> s + ", " + s2)
                .orElse("");
        //封装数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.BAD_REQUEST);
        jsonObject.put("message", message);
        return jsonObject;
    }

    /**
     * 这个是用来捕捉通过实体类接收参数校验异常提示的
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JSONObject methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        //处理实体类里的参数校验的异常(以前的那个不需要这么处理就能获取到中文的校验信息的)
        List<ObjectError> allErrors = Optional.ofNullable(e.getBindingResult().getAllErrors()).orElse(Collections.emptyList());
        Set<String> errors = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.toSet());
        log.error(e.getMessage(), e);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.BAD_REQUEST);
        jsonObject.put("message", errors.toString());
        return jsonObject;
    }

    /**
     * 这个是认证异常捕捉方法
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public JSONObject authenticationExceptionHandler(AuthenticationException e) {
        log.error(e.getMessage(), e);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.UNAUTHORIZED);
        jsonObject.put("message", "无权访问");
        return jsonObject;
    }

    /**
     * 这个是非法参数异常捕捉方法
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public JSONObject illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.BAD_REQUEST);
        jsonObject.put("message", e.getCause().getMessage());
        return jsonObject;
    }

    /**
     * 运行时异常捕捉方法
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public JSONObject runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.BAD_REQUEST);
        jsonObject.put("message", "网络异常");
        return jsonObject;
    }

    /**
     * 最终异常捕捉处理方法
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public JSONObject exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.BAD_REQUEST);
        jsonObject.put("message", "网络异常");
        return jsonObject;
    }

}
