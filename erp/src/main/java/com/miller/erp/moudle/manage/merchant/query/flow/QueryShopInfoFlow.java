package com.miller.erp.moudle.manage.merchant.query.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.manage.merchant.query.request.QueryShopInfoRequestDTO;
import com.miller.erp.moudle.manage.merchant.query.response.QueryShopInfoResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_商家-查询商家信息
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/06/25 16:45:46
 */
public class QueryShopInfoFlow {
    /**
     * 接口_查询店铺信息
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/module/get";

    /**
     * 流程_查询店铺信息
     */
    public static QueryShopInfoResponseDTO queryShopInfo(QueryShopInfoRequestDTO queryShopInfoRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(queryShopInfoRequestDTO),
                null, QueryShopInfoResponseDTO.class);
    }

    /**
     * 查询店铺信息，通过 shop id
     *
     * @param shopId 商家id
     * @return QueryShopInfoResponseDTO
     */
    public static QueryShopInfoResponseDTO queryShopInfoByShopId(Long shopId) {
        QueryShopInfoRequestDTO queryShopInfoRequestDTO = new QueryShopInfoRequestDTO();
        queryShopInfoRequestDTO.setShopId(shopId);
        return queryShopInfo(queryShopInfoRequestDTO);
    }

}