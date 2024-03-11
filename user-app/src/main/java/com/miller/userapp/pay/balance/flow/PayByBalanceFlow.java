package com.miller.userapp.pay.balance.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.pay.balance.request.PayByBalanceRequestDTO;
import com.miller.userapp.pay.balance.response.PayByBalanceResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_余额支付
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/13 16:27:07
 */
public class PayByBalanceFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pay/balance";

    /**
     * 余额支付
     *
     * @param payByBalanceRequestDTO 余额支付请求参数
     * @return 余额支付响应参数
     */
    public static PayByBalanceResponseDTO payByBalance(PayByBalanceRequestDTO payByBalanceRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(payByBalanceRequestDTO), null, PayByBalanceResponseDTO.class);
    }

}
