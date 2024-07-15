package com.miller.common.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

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
public class Response<T> implements Serializable {
    @ApiModelProperty("响应的状态码")
    private Integer code;

    @ApiModelProperty("响应的消息")
    private String message;

    @ApiModelProperty("响应的数据")
    private T data;
}