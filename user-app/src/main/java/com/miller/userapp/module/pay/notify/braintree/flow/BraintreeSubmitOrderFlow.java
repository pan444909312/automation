package com.miller.userapp.module.pay.notify.braintree.flow;

import com.hungrypanda.payserver.utils.MD5Util;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.pay.notify.airwallex.flow.AirwallexPayNotificationFlow;
import com.miller.userapp.module.pay.notify.braintree.request.BTSubmitOrderRequest;
import com.miller.userapp.util.RequestUtils;
import org.springframework.util.StringUtils;

public class BraintreeSubmitOrderFlow {
//    private static final String uri = new PropertiesUtils().getProperty(AirwallexPayNotificationFlow.class,"pay.server.notification.app.url.domain")+ "/api/pandaPay/braintree/v2/submitOrder/{tradeNo}";
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/braintree/submitOrder/{tradeNo}";
    public static String BTSubmitOrder(BTSubmitOrderRequest request){
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnBody(
                uri.replace("{tradeNo}",request.getTradeNo()),
                null, RequestUtils.getHeaders(), JSONUtils.toJSONString(request),null);

    }
//    public static String doSign(String tradeNo, String amountStr, String baseTradeInfo) {
//        if (StringUtils.isEmpty(tradeNo) || StringUtils.isEmpty(amountStr) || StringUtils.isEmpty(baseTradeInfo)) {
//            return null;
//        }
//        String baseSign = MD5Util.md5(tradeNo + ":" + amountStr);
//        return MD5Util.md5(baseSign + tradeNo + baseSign + amountStr + baseSign + baseTradeInfo);
//    }
}
