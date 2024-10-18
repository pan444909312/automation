package com.miller.market.pay.notify.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.pay.notify.request.MarketPaymentNotifyRequestDTO;
import com.miller.market.pay.notify.response.MarketPaymentNotifyResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 支付客户端通知接口
 *
 */
public class MarketPaymentNotifyFlow {
    /**
     * 支付客户端通知接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/payment/notify";

    public static MarketPaymentNotifyResponseDTO getPaymentNotify(MarketPaymentNotifyRequestDTO requestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.getHeaders().remove("channel");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(requestDTO), null, MarketPaymentNotifyResponseDTO.class);
    }


}