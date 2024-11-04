package com.miller.userapp.module.shop.card.version2.pandaLeague.promotion.firstOrder.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.shop.card.version2.pandaLeague.promotion.firstOrder.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.promotion.firstOrder.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;

public class ShopListFlowNoLogin {
    /**
     * 接口_首页店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v2/index/shopList";

    /**
     * 流程_获取首页店铺流
     */
    public static ShopListResponseDTO getShopList(ShopListRequestDTO shopListRequestDTO) {
        // 这里需要测试未登录的情况，所以 RequestUtils.setHeaders(header)
        var myheaders = new HashMap<String, Object>();
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String deviceId="d88a89d4913c70bd";
        myheaders.put("Content-Type", "application/json");
        myheaders.put("uniquetoken", deviceId);
        RequestUtils.setHeaders(myheaders);
//        RequestUtils.getHeaders().put("authorization", null);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }
}
