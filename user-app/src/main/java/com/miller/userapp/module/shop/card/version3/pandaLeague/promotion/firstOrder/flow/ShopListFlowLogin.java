package com.miller.userapp.module.shop.card.version3.pandaLeague.promotion.firstOrder.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.shop.card.version3.pandaLeague.promotion.firstOrder.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.pandaLeague.promotion.firstOrder.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class ShopListFlowLogin {
    /**
     * 接口_首页店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v2/index/shopList";

    /**
     * 流程_获取首页店铺流
     */
    public static ShopListResponseDTO getShopList(ShopListRequestDTO shopListRequestDTO) {
        // 这里需要测试已登陆新人人群1，所以仅传入Content-Type
        RequestUtils.getHeaders().put("Content-Type", "application/json");
//        RequestUtils.getHeaders().put("authorization", null);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }
}
