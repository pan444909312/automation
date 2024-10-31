package com.miller.market.address.search.flow;

import com.miller.market.address.search.request.MarketSearchAddressRequestDTO;
import com.miller.market.address.search.response.MarketSearchAddressResponseDTO;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 地址搜索
 *
 */
public class MarketSearchAddressFlow {
    /**
     * 地址搜索接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/address/search";

    public static MarketSearchAddressResponseDTO searchAddress(MarketSearchAddressRequestDTO requestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 请求参数
                RequestUtils.putBodyOfJson(requestDTO), null, MarketSearchAddressResponseDTO.class);
    }


}