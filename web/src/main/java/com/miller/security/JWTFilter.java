package com.miller.security;

import com.alibaba.fastjson.JSON;

import com.miller.entity.util.Response;
import com.miller.entity.util.ResponseEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;


import java.io.IOException;
import java.io.PrintWriter;

/**
 * JWT 过滤器
 *
 * @author Miller Shan
 * @since 2024/12/26 20:58:37
 */
@Slf4j
public class JWTFilter extends AuthenticatingFilter {
    /**
     * 如果不先调用login接口进行登入，而是直接调用接口，那么此时session里是没有这个用户信息的，就无法与系统交互。
     * 但是如果发送的请求头携带了 Authorization 这个参数，则直接根据 Authorization 参数值去创建一个JwtToken进行与系统交互。
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.debug("createToken invoked....");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 未登录的接口所有的请求头需要添加这个属性，所以前端所有请求建议都加上这个参数
        String jwt = request.getHeader("Authorization");
        if (StringUtils.isEmpty(jwt)) {
            return null;
        }
        return new JWTToken(jwt);
    }

    /**
     * 主要的拦截器,判断账号是否登录
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.debug("onAccessDenied invoked....");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        // 如果前端没有传 token 那么可能是在登陆页面，交给对应的Controller处理即可。
        if (StringUtils.isEmpty(jwt)) {
            return true;
        } else {
            // 解析 token
            Claims claim = JWTUtils.parseJWT(jwt);
            if (claim == null || JWTUtils.isTokenExpired(claim.getExpiration())) {
//                HttpServletResponse response = (HttpServletResponse) servletResponse;
                servletResponse.setContentType("application/plain;charset=utf-8");
                PrintWriter writer = servletResponse.getWriter();
                writer.write(JSON.toJSONString(new Response(ResponseEnum.ACCOUNT_EXPIRE.getCode(), ResponseEnum.ACCOUNT_EXPIRE.getMessage(), null)));
                return false;
            }

            // 登录的过滤器
            return executeLogin(servletRequest, servletResponse);
        }
    }

    /**
     * 登陆失败的时候处理方式
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.debug("onLoginFailure invoked....");

//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Response result = new Response(ResponseEnum.SUCCESS.getCode(), e.getMessage(), null);
        String json = JSON.toJSONString(result);

        try {
            response.getWriter().print(json);
        } catch (IOException ioException) {
            log.warn("login failure:{}", ioException);
        }
        return false;
    }

    /**
     * 跨域处理，这里暂时写在这里，后面实际会把这种都放到nginx中处理
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.debug("preHandle invoked....");

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 前端发送请求之前会先发一个预请求，然后才会发送真正的请求，如果是预请求 OPTIONS 直接返回正常的HTTP状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
