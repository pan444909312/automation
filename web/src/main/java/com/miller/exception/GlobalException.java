package com.miller.exception;

import com.alibaba.fastjson.JSON;

import com.miller.common.util.Response;
import com.miller.common.util.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;

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
 * @since 2024/7/3 15:32:08
 */
@ResponseBody
@Slf4j
// 指定需要扫描的包，否则访问swagger也会被拦截返回统一响应体
@RestControllerAdvice(basePackages = "com.miller.controller")
public class GlobalException implements ResponseBodyAdvice {
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
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    /**
     * 统一对响应体进行处理
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 处理空值
        if (o == null && StringHttpMessageConverter.class.isAssignableFrom(aClass)) {
            return null;
        }
        if (o instanceof String) {
            return JSON.toJSONString(new Response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), o));
        }
        // 判断响应的Content-Type为JSON格式的body
        if (MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_JSON_UTF8.equals(mediaType)) {
            // 如果响应返回的对象为统一响应体，则直接返回body
            if (o instanceof Response) {
                return o;
            } else {
                // 对于未处理的异常信息默认会返回SpringBoot的格式，这里先简单的做一些异常状态判断
                if (o instanceof LinkedHashMap) {
                    LinkedHashMap linkedHashMap = (LinkedHashMap) o;
                    String status = String.valueOf(linkedHashMap.get("status"));
                    // 如果客户端访问了未知地址返回404
                    if (status.equalsIgnoreCase("404")) {
                        return new Response(ResponseCode.RESOURCES_NOT_EXIST.getCode(), ResponseCode.RESOURCES_NOT_EXIST.getMessage(), o);
                    }else if (status.startsWith("4")) {
                        return new Response(ResponseCode.REQUEST_ARGS_ERROR.getCode(), ResponseCode.REQUEST_ARGS_ERROR.getMessage(), o);
                    }
                    else if (status.equalsIgnoreCase("500")) {
                        return new Response(ResponseCode.FAILURE_SERVICE_ERROR.getCode(), ResponseCode.FAILURE_SERVICE_ERROR.getMessage(), o);
                    } else {
                        log.warn("未捕获的SpringBoot异常状态处理.{}", linkedHashMap);
                    }
                }
                // 只有正常返回的结果才会进入这个判断流程，所以返回正常成功的状态码
                return new Response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), o);
            }
        } else {
            // 非JSON格式直接返回
            return o;
        }
    }
}