package com.miller.bdm.app.shopVisit.flow;

import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.bdm.app.shopVisit.request.ShopVisitRequestDTO;
import com.miller.bdm.app.shopVisit.respones.ShopVisitResponseDTO;

public class ShopVisitFlow {

    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/mobile/shop/352297264/visit/page";



    public static ShopVisitResponseDTO getShowVisit(ShopVisitRequestDTO shopVisitRequestDTO) {

        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(shopVisitRequestDTO),
                null, ShopVisitResponseDTO.class);
    }

}
