package com.yb.annotation.log.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yb.annotation.log.server.mapper.OperateLogMapper;
import com.yb.annotation.log.server.model.OperateLog;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author biaoyang
 */
@Service
@EnableScheduling
@AllArgsConstructor
public class LogService {
    private final OperateLogMapper operateLogMapper;

    /**
     * 查询操作日志列表
     *
     * @param page
     * @param size
     * @return
     */
    public IPage<OperateLog> queryLogList(Integer page, Integer size) {
        //构造分页对象
        Page<OperateLog> operateLogPage = new Page<>(page, size);
        //根据操作时间设置排序
        operateLogPage.setDesc("operate_time");
        //查询数据
        return operateLogMapper.selectPage(operateLogPage, null);
    }

    /**
     * 根据用户名查询操作日志
     *
     * @param username
     * @return
     */
    public List<OperateLog> queryByUsername(String username) {
        return operateLogMapper.selectList(new QueryWrapper<OperateLog>().eq("username", username));
    }
}
