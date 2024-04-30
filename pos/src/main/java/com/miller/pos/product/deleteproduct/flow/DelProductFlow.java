package com.miller.pos.product.deleteproduct.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.product.addproduct.AddProductTests;
import com.miller.pos.product.addproduct.response.AddProductResponseDTO;
import com.miller.pos.product.deleteproduct.request.DelProductRequestDTO;
import com.miller.pos.product.deleteproduct.response.DelProductResponseDTO;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 查询店铺详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class DelProductFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/menu/items/";

    public static DelProductResponseDTO delProduct(DelProductRequestDTO delProductRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        AddProductResponseDTO productId = AddProductTests.addProductResponse.get("productId");
        return HttpUtils.sendDeleteRequestReturnJavaObject(uri+productId.getData().getProductId(),
                RequestUtils.putParams(delProductRequestDTO),
                RequestUtils.getHeaders(),
                null,null, DelProductResponseDTO.class);
    }

}
