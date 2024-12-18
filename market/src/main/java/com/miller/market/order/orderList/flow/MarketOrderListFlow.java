package com.miller.market.order.orderList.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.order.orderList.request.MarketOrderListlRequestDTO;
import com.miller.market.order.orderList.response.MarketOrderListlResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 订单列表接口
 *
 */
public class MarketOrderListFlow {
    /**
     * 订单列表接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/order/list";

    public static MarketOrderListlResponseDTO orderList(MarketOrderListlRequestDTO marketOrderListlRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketOrderListlRequestDTO), null, MarketOrderListlResponseDTO.class);
    }


}