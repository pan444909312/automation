package com.miller.merchant.summi.order.list.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.summi.order.list.request.OrderListRequestDTO;
import com.miller.merchant.summi.order.list.response.OrderListResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 订单列表流程
 *
 * <p>
 * 订单状态: 1(待接单列表); 2(备餐中列表); 3(待取餐列表); 4(配送中列表)
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 15:45:30
 */
public class OrderListFlow {
    /**
     * 订单列表接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/merchant/order/list";

    /**
     * 获取订单列表流程
     *
     * @param orderListRequestDTO 订单列表请求DTO
     * @return 订单列表响应DTO
     */
    public static OrderListResponseDTO orderList(OrderListRequestDTO orderListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        OrderListResponseDTO OrderListResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(
                uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(orderListRequestDTO),
                null, OrderListResponseDTO.class);
        return OrderListResponseDTO;
    }

}
