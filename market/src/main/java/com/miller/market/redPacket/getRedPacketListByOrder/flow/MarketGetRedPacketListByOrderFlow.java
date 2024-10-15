package com.miller.market.redPacket.getRedPacketListByOrder.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.redPacket.getRedPacketListByOrder.request.MarketGetRedPacketListByOrderRequestDTO;
import com.miller.market.redPacket.getRedPacketListByOrder.response.MarketGetRedPacketListByOrderResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 订单红包列表接口
 *
 */
public class MarketGetRedPacketListByOrderFlow {
    /**
     * 订单红包列表接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/redPacket/getRedPacketListByOrder";

    public static MarketGetRedPacketListByOrderResponseDTO getRedPacketListByOrder(MarketGetRedPacketListByOrderRequestDTO marketGetRedPacketListByOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketGetRedPacketListByOrderRequestDTO), null, MarketGetRedPacketListByOrderResponseDTO.class);
    }


}