package com.miller.deliveryapp.order.pendingorder.list.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.order.pendingorder.list.request.PendingOrderListRequestDTO;
import com.miller.deliveryapp.order.pendingorder.list.response.PendingOrderListResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_待接单列表
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/12 13:45:30
 */
public class PendingOrderListFlow {
    /**
     * 骑手待接单列表接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/orderPackage/list";

    public static PendingOrderListResponseDTO pendingOrderList(PendingOrderListRequestDTO pendingOrderListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(pendingOrderListRequestDTO),
                null, PendingOrderListResponseDTO.class);
    }

}
