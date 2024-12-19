package com.baomidou.mybatisplus.extension.exceptions;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
 * 请勿删除此类。market 项目需要使用老版本的MBP。
 *
 * @author Miller Shan
 * @since 2024/12/19 21:41:55
 */
public class ApiException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5885155226898287919L;

    /**
     * 错误码
     */
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
