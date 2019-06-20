package com.yb.annotation.log.server.utils;

import com.yb.annotation.log.server.model.LoginUser;
import java.util.Optional;
import java.util.Set;

/**
 * 获取登录用户信息的工具类
 * @author biaoyang
 */
public class LoginUserUtils {

    private static InheritableThreadLocal<LoginUser> userInfo = new InheritableThreadLocal<>();

    /**
     * 设置用户信息
     **/
    public static void setUser(LoginUser user) {
        userInfo.set(user);
    }

    /**
     * 获取所有用户信息
     **/
    public static Optional<LoginUser> getUser() {
        return Optional.ofNullable(userInfo.get());
    }

    /**
     * 获取用户id
     **/
    public static Optional<String> getUserId() {
        return getUser()
                .map(LoginUser::getUserId);
    }

    /**
     * 获取用户名
     */
    public static Optional<String> getUsername() {
        return getUser()
                .map(LoginUser::getUsername);
    }


    /**
     * 获取姓名
     */
    public static Optional<String> getName() {
        return getUser()
                .map(LoginUser::getFullName);
    }

    /**
     * 获取区划代码
     * @return 区划代码
     */
    public static Optional<Long> getAreaCode(){
        return getUser()
                .map(LoginUser::getAreaCode);
    }

    /**
     * 获取机构代码
     * @return 机构代码
     */
    public static Optional<String> getOrgCode(){
        return getUser()
                .map(LoginUser::getOrgCode);
    }

    /**
     * 获取机构名称(部门)
     * @return 机构名称
     */
    public static Optional<String> getOrgName(){
        return getUser()
                .map(LoginUser::getOrgName);
    }

    /**
     * 获取角色
     */
    public static Optional<Set<String>> getRoles() {
        return getUser()
                .map(LoginUser::getRoles);
    }

    /**
     * 获取客户端
     */
    public static Optional<String> getFrom() {
        return getUser()
                .map(LoginUser::getFrom);
    }

    /**
     * 获取IP
     */
    public static Optional<String> getIp() {
        return getUser()
                .map(LoginUser::getIp);
    }

    /**
     * 获取uri
     */
    public static Optional<String> getUri() {
        return getUser()
                .map(LoginUser::getUri);
    }

    /**
     * 获取jti(唯一登陆随机码)
     */
    public static Optional<String> getJti(){
        return getUser()
                .map(LoginUser::getJti);
    }
}
