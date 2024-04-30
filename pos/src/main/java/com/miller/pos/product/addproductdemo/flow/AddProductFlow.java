package com.miller.pos.product.addproductdemo.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.product.addproductdemo.request.AddProductRequestDTO;
import com.miller.pos.product.addproductdemo.response.AddProductResponseDTO;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.constants.JsonLibraryEnum;
import com.miller.service.framework.http.HttpUtils;

import static com.miller.pos.constants.BusinessConstant.menuId;



/**
 * 查询店铺详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class AddProductFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/menu/categories/"+menuId+"/items";

    public static AddProductResponseDTO addProduct(AddProductRequestDTO addProductRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(addProductRequestDTO),
                null, AddProductResponseDTO.class, JsonLibraryEnum.JACKSON);
    }

}
