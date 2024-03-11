package com.miller.deliveryapp.order.delivery.details.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.order.delivery.details.request.OrderDetailsRequestDTO;
import com.miller.deliveryapp.order.delivery.details.response.OrderDetailsResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_获取订单详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/16 14:55:30
 */
public class OrderDetailsFlow {

    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/order/orderDetail";

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
