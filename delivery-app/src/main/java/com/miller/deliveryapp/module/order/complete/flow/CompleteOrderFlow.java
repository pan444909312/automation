package com.miller.deliveryapp.module.order.complete.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.module.order.complete.request.CompleteOrderRequesDTO;
import com.miller.deliveryapp.module.order.complete.response.CompleteOrderRequesResponse;
import com.miller.deliveryapp.order.status.request.ModifyOrderStatusRequestDTO;
import com.miller.deliveryapp.order.status.response.ModifyOrderStatusResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

public class CompleteOrderFlow {
    /**
     * 修改订单状态
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/order/modifyDeliveryStatus";

    public static CompleteOrderRequesResponse modifyOrderStatus(CompleteOrderRequesDTO completeOrderRequesDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(completeOrderRequesDTO),
                null, CompleteOrderRequesResponse.class);
    }
}
