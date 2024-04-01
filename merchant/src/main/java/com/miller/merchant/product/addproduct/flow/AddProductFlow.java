package com.miller.merchant.product.addproduct.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.product.addproduct.request.AddProductRequestDTO;
import com.miller.merchant.product.addproduct.response.AddProductResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class AddProductFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/app/merchant/product/save";

    public static AddProductResponseDTO addProduct(AddProductRequestDTO AddProductRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(AddProductRequestDTO),
                null, AddProductResponseDTO.class);
    }

}
