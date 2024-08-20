package com.miller.market.goods.getGoodsByFirstCategory.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.goods.getGoodsByFirstCategory.request.MarketGetGoodsByFirstCategoryRequestDTO;
import com.miller.market.goods.getGoodsByFirstCategory.response.MarketGetGoodsByFirstCategoryResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;

/**
 * 分类页商品
 *
 */
public class MarketGetGoodsByFirstCategoryWithoutLoginFlow {
    /**
     * 获取分类下商品
     */
    private static final String uri = BusinessConstant.DOMAIN + "/goods/getGoodsByFirstCategory";

    public static MarketGetGoodsByFirstCategoryResponseDTO getCategoryGoods(MarketGetGoodsByFirstCategoryRequestDTO marketGetGoodsByFirstCategoryRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.setHeaders(header);
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
//        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 有请求参数
                RequestUtils.putBodyOfJson(marketGetGoodsByFirstCategoryRequestDTO), null, MarketGetGoodsByFirstCategoryResponseDTO.class);
    }


}