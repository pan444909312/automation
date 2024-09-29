package com.miller.common.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应体结构
 *
 * @author Miller Shan
 * @since 2024/7/3 13:14:30
 */
@ApiModel("统一响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {
    @ApiModelProperty("响应的状态码")
    private Integer code;

    @ApiModelProperty("响应的消息")
    private String message;

    @ApiModelProperty("响应的数据")
    private T data;

    // 通用的成功响应构造方法
    public static <T> Response<T> success(T data) {
        return new Response<T>(ResponseEnum.SUCCESS_200.getCode(), ResponseEnum.SUCCESS_200.getMessage(), data);
    }

    // 通用的错误响应构造方法
    public static <T> Response<T> fail(String message) {
        return new Response<T>(ResponseEnum.FAILURE_SERVICE_ERROR.getCode(), ResponseEnum.FAILURE_SERVICE_ERROR.getMessage(), null);
    }

}