package com.miller.bdm.app.shopAccountInfo.flow;

import com.miller.bdm.app.shopAccountInfo.request.ShopAccountInfoRequestDTO;
import com.miller.bdm.app.shopAccountInfo.respones.ShopAccountInfoResponseDTO;

import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

public class ShopAccountInfoFlow {

    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/shop/account/info";



    public static ShopAccountInfoResponseDTO getShowAccountInfo(ShopAccountInfoRequestDTO shopAccountInfoRequestDTO) {

        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(shopAccountInfoRequestDTO),
                null, ShopAccountInfoResponseDTO.class);
    }

}
