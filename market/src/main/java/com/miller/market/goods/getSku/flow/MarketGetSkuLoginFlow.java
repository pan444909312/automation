package com.miller.market.goods.getSku.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.goods.getSku.request.MarketGetSkuRequestDTO;
import com.miller.market.goods.getSku.response.MarketGetSkuResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 商品sku
 *
 */
public class MarketGetSkuLoginFlow {
    /**
     * 商品sku
     */
    private static final String uri = BusinessConstant.DOMAIN + "/goods/getSku";

    public static MarketGetSkuResponseDTO getGoods(MarketGetSkuRequestDTO requestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 有请求参数
                RequestUtils.putBodyOfJson(requestDTO), null, MarketGetSkuResponseDTO.class);
    }


}