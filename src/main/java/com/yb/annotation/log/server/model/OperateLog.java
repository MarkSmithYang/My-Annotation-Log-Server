package com.yb.annotation.log.server.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 日志格式的模板
 * 因为用户名需要做唯一设置,所以可以直接通过用户名查询相关的操作日志
 * 当然了还可以添加用户id来查询日志信息
 * @author biaoyang
 */
@Setter
@Getter
@ApiModel(description = "日志格式信息封装类")
public class OperateLog extends Model<OperateLog> implements Serializable {
    private static final long serialVersionUID = -2211013133169943016L;

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("所属区域码)")
    private Long areaCode;

    @ApiModelProperty("用户姓名")
    private String fullName;

    @ApiModelProperty("所属部门(机构名称)")
    private String orgName;

    @ApiModelProperty("所属部门(机构码,解决名称变更造成的问题)")
    private String orgCode;

    @ApiModelProperty("操作时间")
    private LocalDateTime operateTime;

    @ApiModelProperty("操作内容")
    private String operateContent;

    @ApiModelProperty("IP地址")
    private String ip;

    public OperateLog() {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.operateTime = LocalDateTime.now();
    }
}
