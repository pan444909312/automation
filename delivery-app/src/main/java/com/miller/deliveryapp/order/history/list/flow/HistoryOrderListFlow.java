package com.miller.deliveryapp.order.history.list.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.order.history.list.request.HistoryOrderListRequestDTO;
import com.miller.deliveryapp.order.history.list.response.HistoryOrderListResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 骑手app历史订单列表
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/15 20:45:30
 */
public class HistoryOrderListFlow {
    /**
     * 骑手历史订单列表
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/order/v2/historyList";

    public static HistoryOrderListResponseDTO historyOrderList(HistoryOrderListRequestDTO historyOrderListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(historyOrderListRequestDTO),
                null, HistoryOrderListResponseDTO.class);
    }

}
