package com.miller.pos.product.queryproduct.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.product.addproduct.AddProductTests;
import com.miller.pos.product.addproduct.response.AddProductResponseDTO;
import com.miller.pos.product.queryproduct.request.QueryProductRequestDTO;
import com.miller.pos.product.queryproduct.response.QueryProductResponseDTO;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;



/**
 * 查询店铺详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class QueryProductFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/menu/items/";

    public static QueryProductResponseDTO queryProduct(QueryProductRequestDTO queryProductRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        AddProductResponseDTO productId = AddProductTests.addProductResponse.get("productId");
        return HttpUtils.sendGetRequestReturnJavaObject(uri+productId.getData().getProductId(),
                RequestUtils.putParams(queryProductRequestDTO),
                RequestUtils.getHeaders(),
                null, QueryProductResponseDTO.class);
    }

}
