package com.miller.market.goods.getGoods.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.goods.getGoods.request.MarketGetGoodsRequestDTO;
import com.miller.market.goods.getGoods.response.MarketGetGoodsByFirstCategoryResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 商品详情
 *
 */
public class MarketGetGoodsLoginFlow {
    /**
     * 商品详情
     */
    private static final String uri = BusinessConstant.DOMAIN + "/goods/getGoods";

    public static MarketGetGoodsByFirstCategoryResponseDTO getGoods(MarketGetGoodsRequestDTO requestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 有请求参数
                RequestUtils.putBodyOfJson(requestDTO), null, MarketGetGoodsByFirstCategoryResponseDTO.class);
    }


}