package com.miller.market.address.deleteAddress.flow;

import com.miller.market.address.deleteAddress.request.MarketDeleteAddressRequestDTO;
import com.miller.market.address.deleteAddress.response.MarketDeleteAddressResponseDTO;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 删除收货地址
 *
 */
public class MarketDeleteAddressFlow {
    /**
     * 删除收货地址接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/address/deleteAddress";

    public static MarketDeleteAddressResponseDTO deleteAddress(MarketDeleteAddressRequestDTO requestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 请求参数
                RequestUtils.putBodyOfJson(requestDTO), null, MarketDeleteAddressResponseDTO.class);
    }


}