package com.miller.bdm.app.privateShop.list.flow;


import com.miller.bdm.app.privateShop.list.request.PrivateShopRequestDTO;
import com.miller.bdm.app.privateShop.list.response.PrivateShopResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.math.BigDecimal;

/**
 * 流程_bdm-移动端私海池-私海池商家列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/1/2 11:10:46
 */


public class PrivateShopFlow {
    /**
     * bdm-移动端私海池-私海池商家列表
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/mobile/private-shop/page";

    /**
     * bdm-移动端私海池-私海池商家列表
     */
    public static PrivateShopResponseDTO getPageList(PrivateShopRequestDTO PrivateShopRequestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(PrivateShopRequestDTO), null, PrivateShopResponseDTO.class);
    }

    /**
     * bdm-移动端私海池-根据城市搜索-私海池商家列表
     * @param city
     * @return
     */
    public static PrivateShopResponseDTO getPageListByCity(String city) {
        PrivateShopRequestDTO privateShopRequestDTO = new PrivateShopRequestDTO();
        privateShopRequestDTO.setPageNo(1);
        privateShopRequestDTO.setPageSize(20);
        privateShopRequestDTO.setLocationLatitude(BigDecimal.valueOf(30.203607));
        privateShopRequestDTO.setLocationLongitude(BigDecimal.valueOf(120.2171314));
        privateShopRequestDTO.setSearchContent(city);

        return getPageList(privateShopRequestDTO);
    }


}