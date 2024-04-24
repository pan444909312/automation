package com.miller.bdm.app.public_shop.list1.flow;

import com.miller.bdm.app.public_shop.list1.request.PublicShopSeachRequestDTO;
import com.miller.bdm.app.public_shop.list1.response.PublicShopSeachResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_客户服务-退款审核-根据订单查询特殊单ID
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:10:46
 */
public class PublicShopSearchFlow {
    /**
     * bdm-移动端公海池-公海池搜索后商家列表
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/mobile/public-shop/page";

    /**
     * bdm-移动端公海池-公海池搜索后商家列表
     */
    public static PublicShopSeachResponseDTO queryRefundList(PublicShopSeachRequestDTO publicShopSeachRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        RequestUtils.setHeaders(headers);
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(publicShopSeachRequestDTO), null, PublicShopSeachResponseDTO.class);
    }

}