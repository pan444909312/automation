package com.miller.market.shopCart.settleShopCart.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.shopCart.settleShopCart.request.MarketSettleShopCartRequestDTO;
import com.miller.market.shopCart.settleShopCart.response.MarketSettleShopCartResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 购物车结算
 *
 */
public class MarketSettleShopCartFlow {
    /**
     * 购物车结算接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/shopcart/settleShopCart";

    public static MarketSettleShopCartResponseDTO settleShopCart(MarketSettleShopCartRequestDTO marketSettleShopCartRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketSettleShopCartRequestDTO), null, MarketSettleShopCartResponseDTO.class);
    }


}