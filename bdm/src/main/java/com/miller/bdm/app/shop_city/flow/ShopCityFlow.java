package com.miller.bdm.app.shop_city.flow;


import com.miller.bdm.app.shop_city.response.ShopCityResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_bdm-商家城市列表
 *
 * @author Miller lipan
 * @version 1.0
 * @since 2024/3/26
 */


public class ShopCityFlow {
    /**
     * bdm-bdm-商家城市列表
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/cities?withoutAllCity=1&checkPermissionStatus=1";

    /**
     * bdm-商家城市列表
     */
    public static ShopCityResponseDTO getPageList() {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendGetRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(),null, ShopCityResponseDTO.class);

    }



}