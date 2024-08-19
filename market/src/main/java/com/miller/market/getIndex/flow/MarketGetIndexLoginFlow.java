package com.miller.market.getIndex.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.getIndex.response.MarketGetIndexResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 首页
 *
 */
public class MarketGetIndexLoginFlow {
    /**
     * 首页接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/index/getIndex";

    public static MarketGetIndexResponseDTO getIndex() {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 无请求参数
                RequestUtils.putBodyOfJson(RequestUtils.getHeaders()), null, MarketGetIndexResponseDTO.class);
    }


}