package com.miller.bdm.app.privateShop.add.flow;


import com.miller.bdm.app.privateShop.add.request.AddPrivateShopRequestDTO;
import com.miller.bdm.app.privateShop.add.response.AddPrivateShopResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_bdm-移动端私海池-私海池商家-新增
 *
 * @author lipan
 * @version 1.0
 * @since 2024/1/2 11:10:46
 */


public class AddPrivateShopFlow {
    /**
     * bdm-移动端私海池-私海池商家-新增
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/mobile/private-shop";

    /**
     * bdm-移动端私海池-私海池商家-新增
     */
    public static AddPrivateShopResponseDTO getPageList(AddPrivateShopRequestDTO PrivateShopRequestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(PrivateShopRequestDTO), null, AddPrivateShopResponseDTO.class);
    }

}