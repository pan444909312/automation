package com.miller.market.pay.payment.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.pay.payment.request.MarketPaymentRequestDTO;
import com.miller.market.pay.payment.response.MarketPaymentResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 支付接口
 *
 */
public class MarketPaymentFlow {
    /**
     * 支付接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/payment";

    public static MarketPaymentResponseDTO getPaymentPattern(MarketPaymentRequestDTO requestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.getHeaders().put("channel",50);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(requestDTO), null, MarketPaymentResponseDTO.class);
    }


}