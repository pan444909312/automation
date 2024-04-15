package com.miller.deliveryapp.order.history.detail.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.order.history.detail.request.HistoryOrderDetailRequestDTO;
import com.miller.deliveryapp.order.history.detail.response.HistoryOrderDetailResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 骑手app历史订单详情
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/15 20:45:30
 */
public class HistoryOrderDetailFlow {
    /**
     * 骑手历史订单详情
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/order/orderDetail";

    public static HistoryOrderDetailResponseDTO historyOrderDetail(HistoryOrderDetailRequestDTO historyOrderDetailRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(historyOrderDetailRequestDTO),
                null, HistoryOrderDetailResponseDTO.class);
    }

}
