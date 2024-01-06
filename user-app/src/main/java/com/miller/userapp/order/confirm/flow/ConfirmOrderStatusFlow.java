package com.miller.userapp.order.confirm.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.order.confirm.request.ConfirmOrderStatusRequestDTO;
import com.miller.userapp.order.confirm.response.ConfirmOrderStatusResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_用户确认订单已送到
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/25 11:45:30
 */
public class ConfirmOrderStatusFlow {
    /**
     * 接口_更新订单状态
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/order/status/update";

    /**
     * 用户确认订单已送到
     *
     * @param confirmOrderStatusRequestDTO 创建订单请求DTO
     * @return 创建订单响应DTO
     */
    public static ConfirmOrderStatusResponseDTO confirmOrderIsReceived(ConfirmOrderStatusRequestDTO confirmOrderStatusRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfForm(confirmOrderStatusRequestDTO),
                null, ConfirmOrderStatusResponseDTO.class);
    }
}
