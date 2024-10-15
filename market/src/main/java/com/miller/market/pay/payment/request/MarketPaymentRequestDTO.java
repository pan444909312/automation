package com.miller.market.pay.payment.request;


import com.panda.market.service.dto.PayCenterDTO;

/**
 * 支付接口请求参数
 */
public class MarketPaymentRequestDTO extends PayCenterDTO {

    private String sysType;
    public void setSysType(final String sysType) {
        this.sysType = sysType;
    }
}
