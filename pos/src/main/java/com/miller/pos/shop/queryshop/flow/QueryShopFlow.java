package com.miller.pos.shop.queryshop.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.shop.queryshop.request.QueryShopRequestDTO;
import com.miller.pos.shop.queryshop.response.QueryShopResponseDTO;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import static com.miller.pos.constants.BusinessConstant.shop_id;


/**
 * 流程_商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class QueryShopFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/shop/"+shop_id;

    public static QueryShopResponseDTO queryShop(QueryShopRequestDTO queryShopRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendGetRequestReturnJavaObject(uri, RequestUtils.putBodyOfForm(queryShopRequestDTO),
                RequestUtils.getHeaders(), null,
                 QueryShopResponseDTO.class);
    }

}
