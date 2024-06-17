package com.miller.market.shopCart.getShopCartList.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.shopCart.getShopCartList.response.MarketGetShopCartListResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 购物车
 *
 */
public class MarketGetShopCartListFlow {
    /**
     * 购物车列表接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/shopcart/getShopCartList";

    public static MarketGetShopCartListResponseDTO getShopCartList( ) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(RequestUtils.getHeaders()), null, MarketGetShopCartListResponseDTO.class);
    }


}