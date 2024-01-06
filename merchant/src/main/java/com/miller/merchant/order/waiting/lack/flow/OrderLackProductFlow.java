package com.miller.merchant.order.waiting.lack.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.order.waiting.lack.request.OrderLackProductRequestDTO;
import com.miller.merchant.order.waiting.lack.response.OrderLackProductResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_缺菜-换菜
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 16:45:30
 */
public class OrderLackProductFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/merchantTerminal/product/return-exchange";

    public static OrderLackProductResponseDTO orderLackProduct(OrderLackProductRequestDTO orderLackProductRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(orderLackProductRequestDTO),
                null, OrderLackProductResponseDTO.class);
    }

}
