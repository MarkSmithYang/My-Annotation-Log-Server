package com.yb.annotation.log.server.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 封装登录用户信息的实体类
 * @author biaoyang
 */
@Getter
@Setter
public class LoginUser {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    @JSONField(name = "sub")
    private String username;

    /**
     * 用户姓名
     */
    private String fullName;
    /**
     * 区划代码
     */
    private Long areaCode;
    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 机构名称(部门)
     */
    private String orgName;

    /**
     * 角色
     */
    private Set<String> roles;

    /**
     * 权限
     */
    private Set<String> perms;

    /**
     * 用户ip
     */
    private String ip;

    /**
     * 用户请求uri
     */
    private String uri;

    /**
     * 登录终端
     */
    private String from;

    /**
     * jti 唯一代码
     */
    private String jti;

}
