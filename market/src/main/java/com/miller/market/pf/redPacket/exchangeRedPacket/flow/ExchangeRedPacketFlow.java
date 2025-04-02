package com.miller.market.pf.redPacket.exchangeRedPacket.flow;

import com.miller.market.constants.PFBusinessConstant;
import com.miller.market.util.PFRequestUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.market.pf.redPacket.exchangeRedPacket.request.ExchangeRedPacketRequestDTO;
import com.miller.market.pf.redPacket.exchangeRedPacket.response.ExchangeRedPacketResponseDTO;


import java.util.HashMap;

/**
 * 兑换活动红包接口
 *
 */
public class ExchangeRedPacketFlow {
    /**
     * 兑换活动红包接口
     */
    private static final String uri = PFBusinessConstant.DOMAIN + "/pandafresh/api/exchangeRedPacket";

    public static ExchangeRedPacketResponseDTO exchangeRedPacket(ExchangeRedPacketRequestDTO requestDTO) {
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("latitude","30.20111");
        myheaders.put("longitude","120.22136");
        PFRequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,PFRequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                PFRequestUtils.putBodyOfJson(requestDTO), null, ExchangeRedPacketResponseDTO.class);
    }


}