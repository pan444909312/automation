package com.miller.merchant.manage.product.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.manage.product.request.ProductOnOrOffRequestDTO;
import com.miller.merchant.manage.product.response.ProductOnOrOffResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程-商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class ProductOnOrOffFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/app/merchant/product/onoff";

    public static ProductOnOrOffResponseDTO productOnOrOff(ProductOnOrOffRequestDTO productOnOrOffRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(productOnOrOffRequestDTO),
                null, ProductOnOrOffResponseDTO.class);
    }

}
