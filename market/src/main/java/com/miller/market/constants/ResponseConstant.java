package com.miller.market.constants;


import com.panda.market.common.enums.BaseResponseCodeEnum;

import java.io.Serializable;

/**
 * 响应对象的常量
 *
 * @see BaseResponseCodeEnum
 */
public class ResponseConstant implements Serializable {
    /**
     * 业务响应的状态码
     */
    public static final Long code = BaseResponseCodeEnum.SUCCESS.getCode();

}
