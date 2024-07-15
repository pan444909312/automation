package com.miller.exception;

import com.miller.common.util.Response;
import com.miller.common.util.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

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
// 指定需要扫描的包，否则访问swagger也会被拦截返回统一响应体
//@RestControllerAdvice(basePackages = "com.miller.controller")
@RestControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 处理所有不可知的异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response exception(Exception exception) {
        log.error("全局异常信息 exception={}", exception.getMessage(), exception);
        return new Response(ResponseCode.FAILURE_SERVICE_ERROR.getCode(), ResponseCode.FAILURE_SERVICE_ERROR.getMessage(), exception);
    }

    /**
     * 处理 RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    public Response handleRuntimeException(RuntimeException e) {
        log.error("运行时异常:{}", e.getMessage());
        return new Response(ResponseCode.FAILURE_SERVICE_ERROR.getCode(), ResponseCode.FAILURE_SERVICE_ERROR.getMessage(), e);
    }

    /**
     * 处理业务异常CTException
     */
    @ExceptionHandler(TestCaseException.class)
    public Response handleCTException(TestCaseException e) {
        log.error("业务异常:{}", e.getMessage());
        ResponseCode code = e.getCode();
        return new Response(code.getCode(), code.getMessage(), e);
    }

}