package com.miller.userapp.module.order.shopping.car.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.order.shopping.car.request.ShoppingCarRequestDTO;
import com.miller.userapp.module.order.shopping.car.response.ShoppingCarResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_购物车
 * 注意: 购物车和结算页面仅仅只是用于计算商品价格、优惠等信息，并不会真正的入库，所以在主流中可以不用加购物车和结算的逻辑，这块可以单独测试。
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/10 13:45:30
 */
public class ShoppingCarFlow {
    /**
     * 接口_购物车
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/order/v3/shoppingCart";

    /**
     * 流程_购物车
     */
    public static ShoppingCarResponseDTO addProductToShoppingCar(ShoppingCarRequestDTO shoppingCarRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        var shoppingCarResponseDTO =HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(shoppingCarRequestDTO), null, ShoppingCarResponseDTO.class);
        return shoppingCarResponseDTO;
    }

}
