package com.miller.userapp.order.refund.apply.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.order.refund.apply.request.ApplyRefundRequestDTO;
import com.miller.userapp.order.refund.apply.response.ApplyRefundResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_申请退款
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/29 11:05:30
 */
public class ApplyRefundFlow {
    /**
     * 接口_申请退款
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/order/refund";

    /**
     * 流程_申请退款
     */
    public static ApplyRefundResponseDTO applyRefund(ApplyRefundRequestDTO applyRefundRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(applyRefundRequestDTO),
                null, ApplyRefundResponseDTO.class);
    }

}
