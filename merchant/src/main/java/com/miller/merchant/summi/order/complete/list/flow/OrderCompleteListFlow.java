package com.miller.merchant.summi.order.complete.list.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.summi.order.complete.list.request.OrderCompleteListRequestDTO;
import com.miller.merchant.summi.order.complete.list.response.OrderCompleteListResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 订单列表流程
 *
 * <p>
 * 订单状态: 5(已完成订单)
 * </p>
 *
 * @author yuwei
 * @version 1.0
 * @since 2024/04/24 15:45:30
 */
public class OrderCompleteListFlow {
    /**
     * 订单列表接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/merchant/complete/order/list";

    /**
     * 获取已完成订单列表流程
     *
     * @param orderCompleteListRequestDTO 订单列表请求DTO
     * @return 订单列表响应DTO
     */
    public static OrderCompleteListResponseDTO orderCompleteList(OrderCompleteListRequestDTO orderCompleteListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        OrderCompleteListResponseDTO OrderCompleteListResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(
                uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(orderCompleteListRequestDTO),
                null, OrderCompleteListResponseDTO.class);

        return OrderCompleteListResponseDTO;
    }

}
