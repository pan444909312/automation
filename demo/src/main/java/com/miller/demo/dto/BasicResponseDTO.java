package com.miller.demo.dto;

import lombok.Data;

/**
 * 基础_响应实体类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 20:48:25
 */
@Data
public class BasicResponseDTO {
    private Integer code;
    private String message;
    /**
     * Use Object Because data maybe is String, Integer, Boolean and so on.
     */
    private Object data;
}
