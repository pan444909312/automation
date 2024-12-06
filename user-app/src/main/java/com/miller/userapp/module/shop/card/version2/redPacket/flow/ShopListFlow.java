package com.miller.userapp.module.shop.card.version2.redPacket.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.shop.card.version2.redPacket.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_红包适用商家列表店铺流
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/6/24 17:13:46
 */
public class ShopListFlow {
    /**
     * 接口_红包适用商家列表店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/redPacket/shopList/v1";


    /**
     * 流程_获取红包适用商家列表店铺流
     */
    public static ShopListResponseDTO getShopList(ShopListRequestDTO shopListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        shopListRequestDTO.setCityName("九江市");

        shopListRequestDTO.setShopCategoryIds("[5946]");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }
}