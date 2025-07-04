package com.miller.erp.moudle.pc.product.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.pc.authlogin.response.AuthLoginResponseDTO;
import com.miller.erp.moudle.pc.product.request.ProductSaveRequest;
import com.miller.erp.moudle.pc.product.response.ProductSaveResponse;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


public class ProductSaveFlow {



    private static  String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/merchant/product/save";


    public static ProductSaveResponse productSave(ProductSaveRequest productSaveRequest) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(), productSaveRequest, null, ProductSaveResponse.class);
    }


    
    
}