package com.miller.deliveryapp.constants;

import com.panda.common.constants.PResultCode;

import java.io.Serializable;

/**
 * 响应对象的常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/13 18:32:00
 */
public class ResponseConstant implements Serializable {
    /**
     * 业务响应的状态码
     */
    public static final Integer resultCode = PResultCode.SUCCESS.getCode();
}
