package com.miller.market.redPacket.exchangeRedPacket.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.order.cancelOrder.request.MarketCancelOrderRequestDTO;
import com.miller.market.order.cancelOrder.response.MarketCancelOrderResponseDTO;
import com.miller.market.redPacket.exchangeRedPacket.request.MarketExchangeRedPacketRequestDTO;
import com.miller.market.redPacket.exchangeRedPacket.response.MarketExchangeRedPacketResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 兑换活动红包接口
 *
 */
public class MarketExchangeRedPacketFlow {
    /**
     * 兑换活动红包接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/redPacket/exchangeRedPacket";

    public static MarketExchangeRedPacketResponseDTO exchangeRedPacket(MarketExchangeRedPacketRequestDTO marketExchangeRedPacketRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketExchangeRedPacketRequestDTO), null, MarketExchangeRedPacketResponseDTO.class);
    }


}