package com.miller.userapp.module.pay.card.stripe.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.pay.card.stripe.request.CreatePaymentMethodRequestDTO;
import com.miller.userapp.module.pay.card.stripe.response.CreatePaymentMethodResponseDTO;
import com.miller.service.util.AutoSignUtils;
import com.miller.userapp.util.RequestUtils;

import java.util.Map;
import java.util.Objects;

public class CreatePaymentMethodFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/stripeCreateAndAttachPaymentMethod";

    /**
     * Stripe 绑定卡
     *
     * @param createPaymentMethodRequestDTO
     * @return
     */
    public static CreatePaymentMethodResponseDTO createPaymentMethod(CreatePaymentMethodRequestDTO createPaymentMethodRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode", "SG");
        AutoSignUtils.signHandler(RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(RequestUtils.putBodyOfForm(createPaymentMethodRequestDTO)));

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(createPaymentMethodRequestDTO), null, CreatePaymentMethodResponseDTO.class);
    }

    public static CreatePaymentMethodResponseDTO createPaymentMethod(CreatePaymentMethodRequestDTO createPaymentMethodRequestDTO, Map<String, Object> headers) {
        if (Objects.isNull(headers)) return createPaymentMethod(createPaymentMethodRequestDTO);
        AutoSignUtils.signHandler(RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(RequestUtils.putBodyOfForm(createPaymentMethodRequestDTO)));

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, headers,
                RequestUtils.putBodyOfForm(createPaymentMethodRequestDTO), null, CreatePaymentMethodResponseDTO.class);
    }
}
