package com.miller.market.order.getPreOrderInfo.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.order.getPreOrderInfo.request.MarketGetPreOrderInfoRequestDTO;
import com.miller.market.order.getPreOrderInfo.response.MarketGetPreOrderInfoResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 预订单接口
 *
 */
public class MarketGetPreOrderInfoFlow {
    /**
     * 预订单接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/order/getPreOrderInfo";

    public static MarketGetPreOrderInfoResponseDTO getPreOrderInfo(MarketGetPreOrderInfoRequestDTO marketGetPreOrderInfoRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketGetPreOrderInfoRequestDTO), null, MarketGetPreOrderInfoResponseDTO.class);
    }


}