package com.miller.userapp.module.shop.card.version2.category.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.shop.card.version2.category.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_首页店铺流
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/6/24 17:13:46
 */
public class ShopListFlow {
    /**
     * 接口_首页店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/category/channel";

    /**
     * 流程_获取首页店铺流-品类频道
     */
    public static ShopListResponseDTO getShopList(ShopListRequestDTO shopListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
//        九江市测试，指定品类为：水果串串，请确保需要测试的商家在该品类下(当前测试商家01-04已加入)
        shopListRequestDTO.setFiltering(Boolean.FALSE);
        shopListRequestDTO.setIsNeedMarketCategory(1);
        shopListRequestDTO.setMarketCategoryId(71);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }
}