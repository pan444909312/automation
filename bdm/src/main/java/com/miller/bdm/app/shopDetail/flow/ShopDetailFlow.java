package com.miller.bdm.app.shopDetail.flow;

import com.miller.bdm.app.shopDetail.respones.ShopDetailResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

public class ShopDetailFlow {

    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/merchant/finance/config/detail?shopId=352297264";



    public static ShopDetailResponseDTO getShowDetail() {

        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
//        RequestUtils.getHeaders().put("Content-Type");

        return HttpUtils.sendGetRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(),
                null, ShopDetailResponseDTO.class);
    }

}
