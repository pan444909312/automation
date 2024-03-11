package com.miller.merchant.order.details.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.order.details.request.OrderDetailsRequestDTO;
import com.miller.merchant.order.details.response.OrderDetailsResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_获取订单详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 17:55:30
 */
public class OrderDetailsFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/app/merchant/order/details";

    /**
     * 根据查询 ID 查询订单状态
     */
    public static OrderDetailsResponseDTO getOrderDetails(OrderDetailsRequestDTO orderDetailsRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(orderDetailsRequestDTO),
                null, OrderDetailsResponseDTO.class);
    }

}
