package com.miller.market.search.getUserOfSearchGoods.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.search.getUserOfSearchGoods.request.MarketGetUserOfSearchGoodsRequestDTO;
import com.miller.market.search.getUserOfSearchGoods.response.MarketGetUserOfSearchGoodsResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;

/**
 * 客户端搜索页面常买和热销榜单商品列表接口
 *
 */
public class MarketGetUserOfSearchGoodsWithoutLoginFlow {
    /**
     * 客户端搜索页面常买和热销榜单商品列表接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/search/getUserOfSearchGoods";

    public static MarketGetUserOfSearchGoodsResponseDTO search(MarketGetUserOfSearchGoodsRequestDTO marketGetUserOfSearchGoodsRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketGetUserOfSearchGoodsRequestDTO), null, MarketGetUserOfSearchGoodsResponseDTO.class);
    }


}