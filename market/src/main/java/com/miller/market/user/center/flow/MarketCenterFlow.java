package com.miller.market.user.center.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.user.center.response.MarketCenterResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 个人中心
 *
 */
public class MarketCenterFlow {
    /**
     * 个人中心
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/user/center";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     */
    public static MarketCenterResponseDTO center() {
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 无请求参数
                RequestUtils.putBodyOfJson(RequestUtils.getHeaders()), null, MarketCenterResponseDTO.class);
    }


}