package com.miller.deliveryapp.order.delivery.list.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.order.delivery.list.request.DeliveryListRequestDTO;
import com.miller.deliveryapp.order.delivery.list.response.DeliveryListResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_待配送列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 12:45:30
 */
public class DeliveryListFlow {
    /**
     * 待配送列表
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/order/waitDeliveringList";

    public static DeliveryListResponseDTO deliveryList(DeliveryListRequestDTO deliveryListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(deliveryListRequestDTO),
                null, DeliveryListResponseDTO.class);
    }

}
