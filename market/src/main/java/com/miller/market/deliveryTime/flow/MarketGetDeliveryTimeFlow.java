package com.miller.market.deliveryTime.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.deliveryTime.request.MarketGetDeliveryTimeRequestDTO;
import com.miller.market.deliveryTime.response.MarketGetDeliveryTimeResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 获取配送时间接口
 *
 */
public class MarketGetDeliveryTimeFlow {
    /**
     * 获取配送时间接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/deliveryTime/getDeliveryTime";

    public static MarketGetDeliveryTimeResponseDTO getDeliveryTime(MarketGetDeliveryTimeRequestDTO marketGetDeliveryTimeRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketGetDeliveryTimeRequestDTO), null, MarketGetDeliveryTimeResponseDTO.class);
    }


}