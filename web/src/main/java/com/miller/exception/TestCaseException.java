package com.miller.exception;


import com.miller.entity.util.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常处理类
 *
 * @author Miller Shan
 * @since 2024/7/3 15:22:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCaseException extends RuntimeException {
    private int code;
    private String message;

    public TestCaseException() {
    }

    public TestCaseException(ResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    public TestCaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }
}
