package com.miller.deliveryapp.order.neworder.list.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.order.neworder.list.request.NewOrderListRequestDTO;
import com.miller.deliveryapp.order.neworder.list.response.NewOrderListResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_新订单列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 10:45:30
 */
public class NewOrderListFlow {
    /**
     * 骑手新订单列表接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/order/newOrderList";

    public static NewOrderListResponseDTO newOrderList(NewOrderListRequestDTO newOrderListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(newOrderListRequestDTO),
                null, NewOrderListResponseDTO.class);
    }

}
