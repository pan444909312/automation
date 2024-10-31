package com.miller.market.open.index.getGoodsFlow.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.open.index.getGoodsFlow.request.MarketGetGoodsFlowRequestDTO;
import com.miller.market.open.index.getGoodsFlow.response.MarketGetGoodsFlowResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;

/**
 * 首页商品流
 *
 */
public class MarketGetGoodsFlowWithoutLoginFlow {
    /**
     * 获取首页商品流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/open/index/getGoodsFlow";

    public static MarketGetGoodsFlowResponseDTO getGoodsList(MarketGetGoodsFlowRequestDTO requestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.setHeaders(header);
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
//        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 有请求参数
                RequestUtils.putBodyOfJson(requestDTO), null, MarketGetGoodsFlowResponseDTO.class);
    }


}