package com.miller.deliveryapp.order.status.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.order.status.request.ModifyOrderStatusRequestDTO;
import com.miller.deliveryapp.order.status.response.ModifyOrderStatusResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程-修改订单状态
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 16:45:30
 */
public class ModifyOrderStatusFlow {
    /**
     * 修改订单状态
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/order/modifyDeliveryStatus";

    public static ModifyOrderStatusResponseDTO modifyOrderStatus(ModifyOrderStatusRequestDTO modifyOrderStatusRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(modifyOrderStatusRequestDTO),
                null, ModifyOrderStatusResponseDTO.class);
    }

}
