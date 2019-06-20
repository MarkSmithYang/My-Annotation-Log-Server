package com.yb.annotation.log.server.filter;

import com.yb.annotation.log.server.model.LoginUser;
import com.yb.annotation.log.server.utils.LoginUserUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;


/**
 * @author biaoyang
 */
@Component
public class MyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //这里为了模拟登陆的认证授权(因为业务需要用户登录的信息),所以这里直接设置即可
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId("1");
        loginUser.setAreaCode(520200000000L);
        loginUser.setFullName("jack smith");
        loginUser.setIp(getIpAddress(request));
        loginUser.setOrgCode("JG01");
        loginUser.setOrgName("机构01");
        loginUser.setPerms(new HashSet<>(Arrays.asList("admin")));
        loginUser.setRoles(new HashSet<>(Arrays.asList("boss")));
        loginUser.setUsername("jack");
        //添加设置数据到获取用户信息的工具里去
        LoginUserUtils.setUser(loginUser);
        filterChain.doFilter(request,response);
    }

    /**
     * 获取登录用户的ip
     *
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        String[] headers = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip = null;
        String unknown = "unknown";
        for (String header : headers) {
            ip = request.getHeader(header);
            if (StringUtils.isNotBlank(ip) && !unknown.equalsIgnoreCase(ip)) {
                break;
            }
        }
        if (StringUtils.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
