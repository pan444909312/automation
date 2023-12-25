package com.miller.userapp.order.evaluate.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.order.evaluate.request.EvaluateOrderRequestDTO;
import com.miller.userapp.order.evaluate.response.EvaluateOrderResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程-用户评价订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/25 11:45:30
 */
public class EvaluateOrderFlow {
    /**
     * 用户评价订单接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/order/toEvaluate";

    /**
     * 流程-用户评价订单
     */
    public static EvaluateOrderResponseDTO evaluateOrder(EvaluateOrderRequestDTO evaluateOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfForm(evaluateOrderRequestDTO), null, EvaluateOrderResponseDTO.class);
    }

}
