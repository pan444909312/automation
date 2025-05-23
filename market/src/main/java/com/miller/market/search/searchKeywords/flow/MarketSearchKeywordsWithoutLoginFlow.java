package com.miller.market.search.searchKeywords.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.search.searchKeywords.request.MarketSearchKeywordsRequestDTO;
import com.miller.market.search.searchKeywords.response.MarketSearchKeywordsResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;

/**
 * 客户端搜索联想词接口
 *
 */
public class MarketSearchKeywordsWithoutLoginFlow {
    /**
     * 客户端搜索联想词接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/search/searchKeywords";

    public static MarketSearchKeywordsResponseDTO search(MarketSearchKeywordsRequestDTO marketSearchKeywordsRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketSearchKeywordsRequestDTO), null, MarketSearchKeywordsResponseDTO.class);
    }


}