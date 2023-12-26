package com.miller.merchant.order.evaluate.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.order.evaluate.request.EvaluateOrderRequestDTO;
import com.miller.merchant.order.evaluate.response.EvaluateOrderResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程-用户评价订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 15:45:30
 */
public class EvaluateOrderFlow {
    /**
     * 商家评价订单接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/merchant/order/detail/eva/reply";

    /**
     * 流程-用户评价订单
     */
    public static EvaluateOrderResponseDTO evaluateOrder(EvaluateOrderRequestDTO evaluateOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        // RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        return HttpUtils.sendGetRequestReturnJavaObject(uri, RequestUtils.putParams(evaluateOrderRequestDTO),
                RequestUtils.getHeaders(), null, EvaluateOrderResponseDTO.class);
    }

}
