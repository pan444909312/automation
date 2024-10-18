package com.miller.deliveryapp.module.order.grab.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.module.order.grab.request.DeliveryGrabOrderRequestDTO;
import com.miller.deliveryapp.module.order.grab.response.DeliveryGrabOrderResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

public class DeliveryGrabOrderFlow {
    private static final String gradURL = BusinessConstant.DOMAIN + "/api/delivery/app/order/grabOrder";

    public static DeliveryGrabOrderResponseDTO grabOrder(DeliveryGrabOrderRequestDTO deliveryGrabOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(gradURL, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(deliveryGrabOrderRequestDTO),
                null, DeliveryGrabOrderResponseDTO.class);
    }
}
