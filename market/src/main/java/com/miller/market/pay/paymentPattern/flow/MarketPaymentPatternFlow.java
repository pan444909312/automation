package com.miller.market.pay.paymentPattern.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.order.orderDetail.response.MarketOrderDetailResponseDTO;
import com.miller.market.pay.paymentPattern.request.MarketPaymentPatternRequestDTO;
import com.miller.market.pay.paymentPattern.response.MarketPaymentPatternResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 获取支付信息接口
 *
 */
public class MarketPaymentPatternFlow {
    /**
     * 获取支付信息接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/payment/v2/checkOut/paymentPattern";

    public static MarketPaymentPatternResponseDTO getPaymentPattern(MarketPaymentPatternRequestDTO requestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(requestDTO), null, MarketPaymentPatternResponseDTO.class);
    }


}