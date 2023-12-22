package com.miller.deliveryapp.order.pickup.list.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.order.pickup.list.request.PickUpListRequestDTO;
import com.miller.deliveryapp.order.pickup.list.response.PickUpListResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程-待取餐列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 11:45:30
 */
public class PickUpListFlow {
    /**
     * 待取餐列表
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/order/waitPickUpList";

    public static PickUpListResponseDTO pickUpList(PickUpListRequestDTO pickUpListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(pickUpListRequestDTO),
                null, PickUpListResponseDTO.class);
    }

}
