package com.miller.bdm.app.shop_tag.flow;


import com.miller.bdm.app.shop_tag.request.ShopTagRequestDTO;
import com.miller.bdm.app.shop_tag.response.ShopTagResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_bdm-商家標籤列表
 *
 * @author Miller lipan
 * @version 1.0
 * @since 2024/3/26
 */


public class ShopTagFlow {
    /**
     * bdm-bdm-商家標籤列表
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/opportunityTag/tagList";

    /**
     * bdm-商家標籤列表
     */
    public static ShopTagResponseDTO getPageList() {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendGetRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(),null,ShopTagResponseDTO.class);

    }



}