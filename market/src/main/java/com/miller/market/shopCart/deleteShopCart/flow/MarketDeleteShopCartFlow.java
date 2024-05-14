package com.miller.market.shopCart.deleteShopCart.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.shopCart.deleteShopCart.request.MarketDeleteShopCartRequestDTO;
import com.miller.market.shopCart.deleteShopCart.response.MarketDeleteShopCartResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 购物车删除
 *
 */
public class MarketDeleteShopCartFlow {
    /**
     * 购物车删除接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/shopcart/deleteShopCart";

    public static MarketDeleteShopCartResponseDTO deleteShopCart(MarketDeleteShopCartRequestDTO marketAddShopCartRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketAddShopCartRequestDTO), null, MarketDeleteShopCartResponseDTO.class);
    }


}