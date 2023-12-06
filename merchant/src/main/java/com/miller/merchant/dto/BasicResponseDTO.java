package com.miller.merchant.dto;

import lombok.Data;

/**
 * 基础_响应实体类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/6 18:15:26
 */
@Data
public class BasicResponseDTO<T> {
    /**
     * 结果状态码
     */
    private int resultCode;
    /**
     * 异常信息
     */
    private String error;
    /**
     * 原因
     */
    private String reason;

    /**
     * 结果数据
     */
    private T result;

    /**
     * 货币符号
     */
    private String currency;

    /**
     * 老版本接口迁过来需要使用
     */
    private Boolean success;

    /**
     * 当前时间
     */
    private long nowTime = System.currentTimeMillis();

    private Object queryList;
}
