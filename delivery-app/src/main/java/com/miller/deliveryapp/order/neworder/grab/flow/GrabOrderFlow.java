package com.miller.deliveryapp.order.neworder.grab.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.order.neworder.grab.request.GrabOrderRequestDTO;
import com.miller.deliveryapp.order.neworder.grab.response.GrabOrderResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程-骑手抢新订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 13:45:30
 */
public class GrabOrderFlow {
    /**
     * 待取餐列表
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/order/grabOrder";

    public static GrabOrderResponseDTO grabOrder(GrabOrderRequestDTO grabOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(grabOrderRequestDTO),
                null, GrabOrderResponseDTO.class);
    }

}
