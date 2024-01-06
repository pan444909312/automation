package com.miller.userapp.order.refund.submit.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.order.refund.submit.request.SubmitRefundRequestDTO;
import com.miller.userapp.order.refund.submit.response.SubmitRefundResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_申请退款-提交
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/29 11:05:30
 */
public class SubmitRefundFlow {
    /**
     * 接口_申请退款-提交
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/order/refundSubmit";

    /**
     * 流程_申请退款-提交
     */
    public static SubmitRefundResponseDTO applyRefund(SubmitRefundRequestDTO submitRefundRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(submitRefundRequestDTO),
                null, SubmitRefundResponseDTO.class);
    }

}
