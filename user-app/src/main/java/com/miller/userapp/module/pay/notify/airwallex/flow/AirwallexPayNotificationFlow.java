package com.miller.userapp.module.pay.notify.airwallex.flow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.miller.common.util.Sha256Util;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.pay.notify.airwallex.request.AirwallexPayNotificationRequest;
import com.miller.userapp.util.RequestUtils;

import java.time.Instant;

public class AirwallexPayNotificationFlow {
    private static final String uri = PropertiesUtils.getProperty("pay.server.notification.app.url.domain")+ "/api/pandaPay/v1/notify/payment/airwallexPay/v1";
    public static String AirwallexPayNotification(AirwallexPayNotificationRequest request) {
        //{"id":"evt_hkdmm9j99gy1xxx5wh7_zokt97","name":"refund.accepted","account_id":"acct_AxuZtIUDOGyvFrOZNovCBQ","accountId":"acct_AxuZtIUDOGyvFrOZNovCBQ","data":{"object":{"amount":1,"created_at":"2024-07-15T07:41:31+0000","currency":"AUD","id":"rfd_hkdmvlbvkgy1xxwqcm9_zokt97","metadata":{"tradeNo":"PDts972715448452538368"},"payment_attempt_id":"att_hkdmvlbvkgy1vzwe096_zokt97","payment_intent_id":"int_hkdmm9j99gy1vzokt97","request_id":"RFPDts972733258775945216","status":"ACCEPTED","updated_at":"2024-07-15T07:41:32+0000"}},"created_at":"2024-07-15T07:41:32+0000","createdAt":"2024-07-15T07:41:32+0000","version":"2022-11-11","sourceId":"int_hkdmm9j99gy1vzokt97"}
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String timeStamp =String.valueOf(Instant.now().toEpochMilli()) ;
        String airwallexPayNotificationRequestStr = JSON.toJSONString(request,config);
        String content = timeStamp+airwallexPayNotificationRequestStr;
        String airwallexPayNotificationKey = "whsec_QbwnkhlFVZqej0y00o1XMVnY8S1rHxad";
        String sign = Sha256Util.sha256Hmac(content,airwallexPayNotificationKey);
        RequestUtils.getHeaders().put("x-timestamp",timeStamp);
        RequestUtils.getHeaders().put("x-signature",sign);
        return HttpUtils.sendPostRequestReturnBody(uri, null, RequestUtils.getHeaders(),
                airwallexPayNotificationRequestStr, null);

    }
}
