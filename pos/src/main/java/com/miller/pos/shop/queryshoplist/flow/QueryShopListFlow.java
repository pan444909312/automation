package com.miller.pos.shop.queryshoplist.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.shop.queryshoplist.request.QueryShopListRequestDTO;
import com.miller.pos.shop.queryshoplist.response.QueryShopListResponseDTO;
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
public class QueryShopListFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/shop/shop_list";

    public static QueryShopListResponseDTO queryshoplist(QueryShopListRequestDTO queryShopListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("Authorization", "lPWgzWw5aXp3iX0RCSN+vsUV/Li2K7vzxTAvEutqw+c=");
        return HttpUtils.sendGetRequestReturnJavaObject(uri, RequestUtils.putBodyOfForm(queryShopListRequestDTO),
                RequestUtils.getHeaders(), null,
                 QueryShopListResponseDTO.class);
    }

}
