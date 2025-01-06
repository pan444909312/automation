package com.miller.exception;

import com.miller.common.util.Response;
import com.miller.common.util.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理及返回统一响应体结构
 * <p>
 * 全局异常信息捕获, 处理 Controller 运行时未捕获的异常和对所有响应结果进行统一处理。
 * {@link RestControllerAdvice @RestControllerAdvice} 是对 Controller 的增强处理,
 * 这里我直接使用value指定包来对这个包下的所有响应体都做统一的增强处理，也可以通过使用 annotations 属性
 * 指定只有使用了指定注解的类才做返回结果增强处理。
 * </p>
 *
 * @author Miller Shan
 * @see ResponseAdvice
 * @since 2024/7/3 15:32:08
 */
@ResponseBody
// 指定需要扫描的包，否则访问swagger也会被拦截返回统一响应体
//@RestControllerAdvice(basePackages = "com.miller.controller")
@RestControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 处理业务异常
     */
    @ExceptionHandler({TestCaseException.class, AutomationException.class})
    public Response<Object> handleAutomationException(TestCaseException testCaseException) {
        log.error("业务异常: {}", testCaseException.getMessage());
        return new Response<>(testCaseException.getCode(), testCaseException.getMessage(), testCaseException);
    }

    /**
     * 处理404异常
     *
     * @param httpServletRequest HttpServletRequest
     * @param e                  Exception
     * @return Response
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Response<Object> noHandlerFoundException(HttpServletRequest httpServletRequest, Exception e) {
        log.error("404异常 NoHandlerFoundException, method = {}, path = {} ", httpServletRequest.getMethod(), httpServletRequest.getServletPath(), e);
        return new Response<>(ResponseEnum.RESOURCES_NOT_EXIST.getCode(), ResponseEnum.RESOURCES_NOT_EXIST.getMessage(), e);
    }

    /**
     * 处理请求方式错误(405)异常
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return Response
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<Object> httpRequestMethodNotSupportedException(HttpServletRequest httpServletRequest, Exception e) {
        log.error("请求方式错误(405)异常 HttpRequestMethodNotSupportedException, method = {}, path = {}", httpServletRequest.getMethod(), httpServletRequest.getServletPath(), e);
        return new Response<>(ResponseEnum.REQUEST_METHOD_ERROR.getCode(), ResponseEnum.REQUEST_METHOD_ERROR.getMessage(), e);
    }

    @ExceptionHandler(value = ExpiredCredentialsException.class)
    public Response handler(ExpiredCredentialsException e) {
        log.warn("账号已过期：----------------{}", e.getMessage());
        return new Response(ResponseEnum.ACCOUNT_EXPIRE.getCode(), ResponseEnum.ACCOUNT_EXPIRE.getMessage(), null);
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    public Response handler(UnauthenticatedException e) {
        log.warn("账号被禁用：----------------{}", e);
        return new Response(ResponseEnum.ACCOUNT_DISABLE.getCode(), ResponseEnum.ACCOUNT_DISABLE.getMessage(), null);
    }

    @ExceptionHandler(value = UnknownAccountException.class)
    public Response handler(UnknownAccountException e) {
        log.warn("未知账号：----------------{}", e);
        return new Response(ResponseEnum.UNKNOWN_ACCOUNT.getCode(), ResponseEnum.UNKNOWN_ACCOUNT.getMessage(), null);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public Response handler(UnauthorizedException e) {
        log.warn("无权限操作：----------------{}", e.getMessage());
        return new Response(ResponseEnum.ACCOUNT_UNAUTHORIZED.getCode(), ResponseEnum.ACCOUNT_UNAUTHORIZED.getMessage(), null);
    }


    /**
     * 处理所有不可知的异常
     * 使用@ResponseStatus来指定客户端收到的http状态码，如果不指定，则默认返回200
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response<Object> exception(Exception exception) {
        log.error("未知异常: {}", exception.getMessage(), exception);
        return new Response<>(ResponseEnum.FAILURE_SERVICE_ERROR.getCode(), ResponseEnum.FAILURE_SERVICE_ERROR.getMessage(), exception);
    }
}