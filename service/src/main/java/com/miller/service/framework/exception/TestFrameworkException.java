package com.miller.service.framework.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常处理类
 *
 * @author Miller Shan
 * @since 2024/10/28 18:22:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestFrameworkException extends RuntimeException {
    private int code;
    private String message;

    public TestFrameworkException() {
    }

    public TestFrameworkException(String message) {
        super(message);
        this.message = message;
    }

    public TestFrameworkException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
