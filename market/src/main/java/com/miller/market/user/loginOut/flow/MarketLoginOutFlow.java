package com.miller.market.user.loginOut.flow;

import com.alibaba.fastjson.JSON;
import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.user.login.request.MarketLoginRequestDTO;
import com.miller.market.user.loginOut.response.MarketLoginOutResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登出流程
 *
 */
public class MarketLoginOutFlow {
    /**
     * 登出接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/user/loginOut";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     */
    public static MarketLoginOutResponseDTO loginOut() {
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 无请求参数
                RequestUtils.putBodyOfJson(RequestUtils.getHeaders()), null,MarketLoginOutResponseDTO.class);
    }


}