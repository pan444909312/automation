package com.miller.merchant.summi.order.complete.statistics.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.summi.order.complete.statistics.request.OrderCompleteStatisticsRequestDTO;
import com.miller.merchant.summi.order.complete.statistics.response.OrderCompleteStatisticsResponseDTO;
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
public class OrderCompleteStatisticsFlow {
    /**
     * 已完成订单统计接口 （统计订单数量&预计收入）
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/merchant/complete/order/statistics";

    /**
     * 获取已完成订单列表统计数据
     *
     * @param orderCompleteStatisticsRequestDTO 已完成订单统计请求DTO
     * @return 已完成订单统计响应DTO
     */
    public static OrderCompleteStatisticsResponseDTO orderCompleteStatistics(OrderCompleteStatisticsRequestDTO orderCompleteStatisticsRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        OrderCompleteStatisticsResponseDTO OrderCompleteStatisticsResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(
                uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(orderCompleteStatisticsRequestDTO),
                null, OrderCompleteStatisticsResponseDTO.class);

        return OrderCompleteStatisticsResponseDTO;
    }

}
