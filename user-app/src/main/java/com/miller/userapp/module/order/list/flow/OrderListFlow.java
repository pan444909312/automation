package com.miller.userapp.module.order.list.flow;

import com.hungrypanda.app.server.common.enums.order.OrderViewStatusEnum;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.order.list.request.OrderListRequestDTO;
import com.miller.userapp.module.order.list.response.OrderListResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_订单列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/25 14:45:30
 */
public class OrderListFlow {
    /**
     * 接口_订单列表
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/order/processList";

    /**
     * 流程_获取订单列表。默认100条，20条历史订单，开发代码里写死的数据。
     */
    public static OrderListResponseDTO getOrderList(OrderListRequestDTO orderListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(orderListRequestDTO), null, OrderListResponseDTO.class);
    }

    /**
     * 根据订单状态获取订单列表
     *
     * @param orderStatus {@link OrderViewStatusEnum}
     * @return 订单列表
     */
    public static OrderListResponseDTO getOrderListByStatus(Integer orderStatus) {
        OrderListRequestDTO orderListRequestDTO = new OrderListRequestDTO();
        orderListRequestDTO.setPageNo(1);
        orderListRequestDTO.setOrderStatus(orderStatus);
        return getOrderList(orderListRequestDTO);
    }

    /**
     * 获取所有订单
     *
     * @return 订单列表
     */
    public static OrderListResponseDTO getAllOrder() {
        return getOrderListByStatus(OrderViewStatusEnum.PAYMENT_PENDING.getCode());
    }
}
