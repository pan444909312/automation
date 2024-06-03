package com.miller.pos.product.editproduct.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.product.addproduct.AddProductTests;
import com.miller.pos.product.addproduct.response.AddProductResponseDTO;
import com.miller.pos.product.editproduct.request.EditProductRequestDTO;
import com.miller.pos.product.editproduct.response.EditProductResponseDTO;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.constants.JsonLibraryEnum;
import com.miller.service.framework.http.HttpUtils;

import static com.miller.pos.constants.BusinessConstant.menuId;

import static com.miller.pos.constants.BusinessConstant.*;

/**
 * 查询店铺详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class EditProductFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/menu/items/";

    public static EditProductResponseDTO editProduct(EditProductRequestDTO editProductRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        AddProductResponseDTO productId = AddProductTests.addProductResponse.get("productId");

        return HttpUtils.sendPutRequestReturnJavaObject(uri+productId.getData().getProductId(), null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(editProductRequestDTO),
                null, EditProductResponseDTO.class, JsonLibraryEnum.JACKSON);
    }

}
