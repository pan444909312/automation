package com.miller.market.search.search.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.search.search.request.MarketSearchRequestDTO;
import com.miller.market.search.search.response.MarketSearchResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;

/**
 * 客户端搜索接口
 *
 */
public class MarketSearchWithoutLoginFlow {
    /**
     * 客户端搜索接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/search/search";

    public static MarketSearchResponseDTO search(MarketSearchRequestDTO marketSearchRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketSearchRequestDTO), null, MarketSearchResponseDTO.class);
    }


}