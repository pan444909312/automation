package com.miller.exception;

import com.miller.common.util.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常处理类
 *
 * @author Miller Shan
 * @since 2024-12-14 20:22:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AutomationException extends RuntimeException {
    private ResponseEnum code;

    public AutomationException(ResponseEnum responseCode) {
        this.code = responseCode;
    }

    public AutomationException(Throwable t, ResponseEnum responseCode) {
        super(t);
        this.code = responseCode;
    }
}
