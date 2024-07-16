package com.miller.userapp.module.pay.card.stripe.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.pay.card.stripe.request.DetachPaymentMethodRequestDTO;
import com.miller.userapp.module.pay.card.stripe.response.DetachPaymentMethodResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class DetachPaymentMethodFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/stripeDetachPaymentMethod";

    /**
     * Stripe解绑卡
     * @param detachPaymentMethodRequestDTO
     * @return
     */
    public static DetachPaymentMethodResponseDTO detachPaymentMethod(DetachPaymentMethodRequestDTO detachPaymentMethodRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode","SG");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(detachPaymentMethodRequestDTO), null, DetachPaymentMethodResponseDTO.class);
    }
}
