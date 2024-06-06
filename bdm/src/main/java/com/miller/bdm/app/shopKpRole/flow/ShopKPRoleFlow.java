package com.miller.bdm.app.shopKpRole.flow;


import com.miller.bdm.app.shopKpRole.response.ShopKPRoleResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_bdm-商家KP角色-列表
 *
 * @author Miller lipan
 * @version 1.0
 * @since 2024/3/26
 */


public class ShopKPRoleFlow {
    /**
     * bdm-商家KP角色-列表
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/shop/key-person/positions";

    /**
     * bdm-商家KP角色-列表
     */
    public static ShopKPRoleResponseDTO getPageList() {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendGetRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(),null, ShopKPRoleResponseDTO.class);

    }

}