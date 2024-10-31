package com.miller.userapp.module.shop.card.version2.category.promotion.firstOrder.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.shop.card.version2.category.promotion.firstOrder.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.promotion.firstOrder.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class ShopListFlowControlGroup {
    /**
     * 接口_首页店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v2/index/shopList";

    /**
     * 流程_获取首页店铺流
     */
    public static ShopListResponseDTO getShopList(ShopListRequestDTO shopListRequestDTO) {
        // 这里将新人价实验组(默认)改为对照组,需要改两个实验
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        String newTestGroup=BusinessConstant.testGroup.replace("XRJ01","XRJ02").replace("SKXRB01","SKXRB02");
        RequestUtils.getHeaders().put("testGroup", newTestGroup);
//        RequestUtils.getHeaders().put("authorization", null);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }
}
