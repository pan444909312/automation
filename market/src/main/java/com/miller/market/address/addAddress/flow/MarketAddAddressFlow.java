package com.miller.market.address.addAddress.flow;

import com.miller.market.address.addAddress.request.MarketAddAddressRequestDTO;
import com.miller.market.address.addAddress.response.MarketAddAddressResponseDTO;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 收货地址
 *
 */
public class MarketAddAddressFlow {
    /**
     * 新增收货地址接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/address/addAddress";

    public static MarketAddAddressResponseDTO addAddress(MarketAddAddressRequestDTO marketAddAddressRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 请求参数
                RequestUtils.putBodyOfJson(marketAddAddressRequestDTO), null, MarketAddAddressResponseDTO.class);
    }


}