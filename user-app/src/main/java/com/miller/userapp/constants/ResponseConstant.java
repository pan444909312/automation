package com.miller.userapp.constants;


import com.hungrypanda.app.server.api.common.ResultCode;

import java.io.Serializable;

/**
 * 响应对象的常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/13 18:32:00
 * @see com.hungrypanda.app.server.api.common.ResultCode
 */
public class ResponseConstant implements Serializable {
    /**
     * 业务响应的状态码
     */
    public static final Integer resultCode = ResultCode.SUCCESS.getCode();
    public static final Integer BUY_LIMIT_MIN_FAIL=ResultCode.BUY_LIMIT_MIN_FAIL.getCode();
}
