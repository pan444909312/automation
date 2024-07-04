package com.miller.exception;


import com.miller.common.util.ResponseCode;
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
    private ResponseCode code;

    public TestCaseException(ResponseCode responseCode) {
        this.code = responseCode;
    }

    public TestCaseException(Throwable t, ResponseCode responseCode) {
        super(t);
        this.code = responseCode;
    }
}
