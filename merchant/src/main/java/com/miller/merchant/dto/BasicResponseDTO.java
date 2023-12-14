package com.miller.merchant.dto;

import com.panda.common.base.AppResult;
import lombok.Data;

/**
 * 基础_响应实体类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/6 18:15:26
 */
@Data
public class BasicResponseDTO<T> extends AppResult<T> {
    // AppResult 对象内部额外的字段

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
