package com.miller.bdm.app.publicShop.list.flow;

import com.miller.bdm.app.publicShop.list.request.PublicShopRequestDTO;
import com.miller.bdm.app.publicShop.list.response.PublicShopResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;

import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_bdm-移动端公海池-公海池商家列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/1/2 11:10:46
 */
public class PublicShopFlow {
    /**
     * bdm-移动端公海池-公海池商家列表
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/mobile/public-shop/page";

    /**
     * bdm-移动端公海池-公海池商家列表
     */
    public static PublicShopResponseDTO getPageList(PublicShopRequestDTO publicShopRequestDTO) {

        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(publicShopRequestDTO), null, PublicShopResponseDTO.class);
    }

}