package com.miller.userapp.pay.card.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.pay.card.request.CreatePaymentMethodRequestDTO;
import com.miller.userapp.pay.card.response.CreatePaymentMethodResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class CreatePaymentMethodFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/stripeCreateAndAttachPaymentMethod";

    /**
     * Stripe 绑定卡
     * @param createPaymentMethodRequestDTO
     * @return
     */
    public static CreatePaymentMethodResponseDTO createPaymentMethod(CreatePaymentMethodRequestDTO createPaymentMethodRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode","SG");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(createPaymentMethodRequestDTO), null, CreatePaymentMethodResponseDTO.class);
    }
}
