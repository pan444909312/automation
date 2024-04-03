package com.miller.bdm.app.shop_category.flow;


import com.miller.bdm.app.shop_category.request.ShopCategoryRequestDTO;
import com.miller.bdm.app.shop_category.response.ShopCategoryResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_bdm-商家类目列表
 *
 * @author Miller lipan
 * @version 1.0
 * @since 2024/3/26
 */


public class ShopCategoryFlow {
    /**
     * bdm-商家类目列表
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/category/query/list/business/type";

    /**
     * bdm-商家类目列表
     */
    public static ShopCategoryResponseDTO getPageList(ShopCategoryRequestDTO ShopCategoryRequestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(ShopCategoryRequestDTO), null, ShopCategoryResponseDTO.class);
    }



}