package com.yb.annotation.log.server.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yb.annotation.log.server.annotation.LogRecord;
import com.yb.annotation.log.server.common.Result;
import com.yb.annotation.log.server.model.OperateLog;
import com.yb.annotation.log.server.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author biaoyang
 */
@Api(tags = "操作日志测试类")
@Validated
@RestController
@AllArgsConstructor
public class LogController {
    private final LogService logService;

    @ApiOperation("查询操作日志列表")
    @GetMapping("/queryLogList")
    @LogRecord(operateContent = "查询操作日志列表")
    public Result<IPage<OperateLog>> queryLogList(
            @ApiParam(value = "页码,从1开始", defaultValue = "1")
            @Min(value = 1, message = "页码有误")
            @RequestParam(required = false) Integer page,

            @ApiParam(value = "每页展示条数", defaultValue = "10")
            @Max(value = 50, message = "每页展示条数过多")
            @RequestParam(required = false) Integer size) {
        //查询数据,这里为了方便不再传其他条件了
        IPage<OperateLog> result = logService.queryLogList(page, size);
        return Result.success(result);
    }

    @ApiOperation("根据用户名查询操作日志")
    @GetMapping("/queryByUsername")
    @LogRecord(operateContent = "根据用户名查询操作日志")
    public Result<List<OperateLog>> queryByUsername(
            @ApiParam(value = "用户名",required = true)
            @NotBlank(message = "用户名不能为空")
            @Length(max=50,message = "用户名有误")
            @RequestParam String username) {
        //查询数据,这里为了方便不再传其他条件了
        List<OperateLog> result = logService.queryByUsername(username);
        return Result.success(result);
    }

}





