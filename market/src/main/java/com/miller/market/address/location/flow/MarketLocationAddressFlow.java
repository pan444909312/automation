package com.miller.market.address.location.flow;

import com.miller.market.address.location.request.MarketLocationAddressRequestDTO;
import com.miller.market.address.location.response.MarketLocationAddressResponseDTO;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 地址定位
 *
 */
public class MarketLocationAddressFlow {
    /**
     * 地址定位接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/address/location";

    public static MarketLocationAddressResponseDTO searchAddress(MarketLocationAddressRequestDTO requestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 请求参数
                RequestUtils.putBodyOfJson(requestDTO), null, MarketLocationAddressResponseDTO.class);
    }


}