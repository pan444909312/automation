package com.miller.userapp.module.pay.payment.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.pay.payment.request.DefaultPaymentRequest;
import com.miller.userapp.module.pay.payment.response.DefaultPaymentResponse;
import com.miller.userapp.util.RequestUtils;


public class DefaultPaymentFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/payment";
    public static DefaultPaymentResponse payment(DefaultPaymentRequest request,String countryCode ,String channel) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode",countryCode);
        RequestUtils.getHeaders().put("channel",channel);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(request), null, DefaultPaymentResponse.class);

    }
}
