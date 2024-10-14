package com.miller.exception;

import com.alibaba.fastjson.JSON;
import com.miller.common.util.Response;
import com.miller.common.util.ResponseEnum;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回统一的响应体
 * <p>
 * <code>@RestControllerAdvice</code>是 @RestController 注解的增强，可以实现三个方面的功能：
 * 1. 全局异常处理;
 * 2. 全局数据绑定;
 * 3. 全局数据预处理.
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @see GlobalException
 * @since 2024/7/15 14:03:52
 */
// 指定需要扫描的包，否则访问swagger也会被拦截返回统一响应体
@RestControllerAdvice(basePackages = "com.miller.controller")
//@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 是否支持advice功能
     *
     * @return true 支持，false 不支持
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 响应体拦截
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (body instanceof String) {
            return JSON.toJSONString(new Response(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), body));
        }
        // 如果返回的是Response对象，则直接返回
        if (body instanceof Response) return body;
        return new Response(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), body);
    }
}
