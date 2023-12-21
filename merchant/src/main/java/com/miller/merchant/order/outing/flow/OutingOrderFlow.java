package com.miller.merchant.order.outing.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.order.outing.request.OutingOrderRequestDTO;
import com.miller.merchant.order.outing.response.OutingOrderResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 商家出餐流程
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 19:45:30
 */
public class OutingOrderFlow {
    /**
     * 商家接单并备餐接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/merchant/diningOut-order";

    /**
     * 商家接单并备餐流程
     */
    public static OutingOrderResponseDTO outingOrder(OutingOrderRequestDTO outingOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        OutingOrderResponseDTO outingOrderResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(outingOrderRequestDTO),
                null, OutingOrderResponseDTO.class);

        return outingOrderResponseDTO;
    }

}
