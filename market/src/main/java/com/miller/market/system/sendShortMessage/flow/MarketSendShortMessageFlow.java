package com.miller.market.system.sendShortMessage.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.system.sendShortMessage.request.MarketSendShortMessageRequestDTO;
import com.miller.market.system.sendShortMessage.response.MarketSendShortMessageResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;

/**
 * 发送短信接口
 *
 */
public class MarketSendShortMessageFlow {
    /**
     * 发送短信接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/system/sendShortMessage";

    public static MarketSendShortMessageResponseDTO sendShortMessage(MarketSendShortMessageRequestDTO marketSendShortMessageRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketSendShortMessageRequestDTO), null, MarketSendShortMessageResponseDTO.class);
    }


}