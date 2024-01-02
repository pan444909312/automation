package com.miller.merchant.order.waiting.receiving.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.order.waiting.receiving.request.ReceivingOrderRequestDTO;
import com.miller.merchant.order.waiting.receiving.response.ReceivingOrderResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程-商家接单并备餐
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 19:45:30
 */
public class ReceivingOrderFlow {
    /**
     * 商家接单并备餐接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/merchant/receiving-order";

    /**
     * 商家接单并备餐流程
     *
     */
    public static ReceivingOrderResponseDTO receivingOrder(ReceivingOrderRequestDTO receivingOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        ReceivingOrderResponseDTO receivingOrderResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(receivingOrderRequestDTO),
                null, ReceivingOrderResponseDTO.class);

        return receivingOrderResponseDTO;
    }

}
