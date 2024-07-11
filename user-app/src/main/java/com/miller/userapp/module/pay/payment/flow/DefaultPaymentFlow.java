package com.miller.userapp.module.pay.payment.flow;

import com.hungrypanda.app.server.api.req.payment.AliWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.AppWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.WechatWalletInstallReq;
import com.hungrypanda.app.server.api.res.payment.PaymentPatternDTO;
import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.card.flow.GetPaymentMethodsFlow;
import com.miller.userapp.module.pay.checkout.flow.PaymentPatternCheckOutFlow;
import com.miller.userapp.module.pay.checkout.response.PaymentPatternCheckOutResponseDTO;
import com.miller.userapp.module.pay.payment.request.DefaultPaymentRequest;
import com.miller.userapp.module.pay.payment.request.StripePaymentRequest;
import com.miller.userapp.module.pay.payment.response.DefaultPaymentResponse;
import com.miller.userapp.module.pay.payment.response.StripePaymentResponse;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DefaultPaymentFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/payment";
    @MethodSource("elePaymentDataProvider")
    public static DefaultPaymentResponse payment(DefaultPaymentRequest request,String countryCode ,String channel) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode",countryCode);
        RequestUtils.getHeaders().put("channel",channel);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(request), null, DefaultPaymentResponse.class);

    }
}
