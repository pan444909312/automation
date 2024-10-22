package com.miller.market.topic.getSpecialTopicGoods.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.topic.getSpecialTopicGoods.request.MarketGetSpecialTopicGoodsRequestDTO;
import com.miller.market.topic.getSpecialTopicGoods.response.MarketGetSpecialTopicGoodsResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 专题商品接口
 *
 */
public class MarketGetSpecialTopicGoodsFlow {
    /**
     * 专题商品接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/topic/getSpecialTopicGoods";

    public static MarketGetSpecialTopicGoodsResponseDTO getSpecialTopicGoods(MarketGetSpecialTopicGoodsRequestDTO requestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(requestDTO), null, MarketGetSpecialTopicGoodsResponseDTO.class);
    }


}