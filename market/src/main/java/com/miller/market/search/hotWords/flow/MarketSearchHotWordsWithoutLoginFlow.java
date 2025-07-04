package com.miller.market.search.hotWords.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.search.hotWords.response.MarketSearchHotWordsResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;

/**
 * 客户端搜索热搜词
 *
 */
public class MarketSearchHotWordsWithoutLoginFlow {
    /**
     * 客户端搜索热搜词
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/search/hotWords";

    public static MarketSearchHotWordsResponseDTO search() {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 无请求参数
                RequestUtils.putBodyOfJson(RequestUtils.getHeaders()), null, MarketSearchHotWordsResponseDTO.class);
    }


}