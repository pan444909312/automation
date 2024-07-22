package com.miller.userapp.module.pay.notify.ele.flow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hungrypanda.payserver.biz.payment.elepay.model.ElePayNotificationModel;
import com.hungrypanda.payserver.entity.PayOrder;
import com.miller.common.util.MD5Util;
import com.miller.common.util.Sha256Util;
import com.miller.service.framework.constants.JsonLibraryEnum;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.data.pay.db.PayOrderSql;
import com.miller.userapp.module.pay.notify.ele.request.ElePayNotificationRequest;
import com.miller.userapp.util.RequestUtils;
import io.elepay.client.charge.pojo.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import static com.miller.userapp.util.RequestUtils.putBodyOfJson;


public class ElePayNotificationFlow {
    private static final String uri = new PropertiesUtils().getProperty(ElePayNotificationFlow.class,"pay.server.notification.app.url.domain")+ "/api/pandaPay/v1/notify/payment/elePay/v1";
    public static void main(String[] args){
//        PayOrderSql payOrderSql = new PayOrderSql();
//        PayOrder payOrder = payOrderSql.getPayOrder("776068920042272387996",false);
//        System.out.println("result: "+payOrder.getTradeStatus());
//        Long timeStamp =Instant.now().toEpochMilli() ;
//        Long timeStamp = 1720666255347l;
//        ElePayNotificationRequest elePayNotificationRequest = new ElePayNotificationRequest();
////        String appId = MD5Util.string2MD5(UUID.randomUUID().toString()).substring(0,22);
//        String appId = "4c83162bfdf48e3638e74f";
//        ChargeDto chargeDto = new ChargeDto();
//        chargeDto.setLiveMode(true);
//        chargeDto.setAppId("app_"+appId);
//        chargeDto.setExtra(new HashMap<String,String>());
//        chargeDto.currency("JPY");
//        chargeDto.setRefunded(false);
//        chargeDto.setId(payOrder.getChannelTransactionNo());
//        chargeDto.setAuthorize(false);
//        chargeDto.setAmount(payOrder.getAmount());
//        chargeDto.setDisputed(false);
//        chargeDto.setResource(ResourceType.IOS);
//        chargeDto.setPaid(true);
//        chargeDto.setOrderNo(payOrder.getTradeNo());
//        String payExtraChannel = payOrder.getPayExtraChannel().toLowerCase();
//        chargeDto.paymentMethod(PaymentMethodType.fromValue(payExtraChannel));
//        chargeDto.setObject("charge");
//        chargeDto.setStatus(ChargeStatusType.CAPTURED);
//        chargeDto.setPaidTime(timeStamp);
//        chargeDto.setCreateTime(timeStamp);
//        CardInfo cardInfo = new CardInfo();
//        cardInfo.setThreeDSecure(false);
//        cardInfo.setWallet(payExtraChannel);
//        chargeDto.setCardInfo(cardInfo);
//        elePayNotificationRequest.setData(new ElePayNotificationModel.DataObject().setObject(chargeDto));
//        elePayNotificationRequest.setLiveMode(true);
//        elePayNotificationRequest.setId("wh_et"+appId);
//        elePayNotificationRequest.setType("charge.succeeded");
//        elePayNotificationRequest.setObject("event");
//        elePayNotificationRequest.setCreateTime(timeStamp);
//        System.out.println(JSON.toJSONString(elePayNotificationRequest, SerializerFeature.WriteEnumUsingToString));
//        String content = 1720666255379l+"."+JSON.toJSONString(elePayNotificationRequest,SerializerFeature.WriteEnumUsingToString);
//        String elePayNotificationKey = "ws_live_7f4d66faa982d0ce5dbc8";
//        String sign = Sha256Util.sha256Hmac(content,elePayNotificationKey);
//        System.out.println("sign: "+sign);
    }

    public static String elePaymentNotification(ElePayNotificationRequest elePayNotificationRequest) {
        //{"liveMode":true,"data":{"object":{"liveMode":true,"appId":"app_39a446a98af6a66186f7504","extra":{},"currency":"JPY","refunded":false,"id":"ch_5fda902ff7313819c1fed48","authorize":false,"amount":490,"disputed":false,"orderNo":"PD970568808175013888","cardInfo":{"wallet":"alipayplus","threeDSecure":false},"resource":"ios","paidTime":1720513250020,"createTime":1720513246527,"paid":true,"paymentMethod":"alipay","object":"charge","status":"captured"}},"createTime":1720513250056,"id":"wh_evt_fc6b61bf3a69f6e98abe954","type":"charge.succeeded","object":"event"}
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
//        RequestUtils.getHeaders().put("countryCode","JP");
        // //格式: elepay-signature: t=1581064080,sign=100dcc3d839c89cd91ecdd23d7305b2fdb8ae73b498c27efd812b25fc86ec702
        String timeStamp =String.valueOf(Instant.now().toEpochMilli()) ;
        String elePayNotificationRequestStr = JSON.toJSONString(elePayNotificationRequest,SerializerFeature.WriteEnumUsingToString);
        String content = timeStamp+"."+elePayNotificationRequestStr;
        String elePayNotificationKey = "ws_live_7f4d66faa982d0ce5dbc8";
        String sign = Sha256Util.sha256Hmac(content,elePayNotificationKey);
        RequestUtils.getHeaders().put("Elepay-Signature","t="+timeStamp+",sign="+sign);
        return HttpUtils.sendPostRequestReturnBody(uri, null, RequestUtils.getHeaders(),
                elePayNotificationRequestStr, null);

    }
    /*private ElePayNotificationRequest setElePayNotificationRequest(PayOrder payOrder,Long timeStamp){
        ElePayNotificationRequest elePayNotificationRequest = new ElePayNotificationRequest();
        String appId = MD5Util.string2MD5(UUID.randomUUID().toString()).substring(0,22);
        ChargeDto chargeDto = new ChargeDto();
        chargeDto.setLiveMode(true);
        chargeDto.setAppId("app_"+appId);
        chargeDto.setExtra(new HashMap<String,String>());
        chargeDto.currency("JPY");
        chargeDto.setRefunded(false);
        chargeDto.setId(payOrder.getChannelTransactionNo());
        chargeDto.setAuthorize(false);
        chargeDto.setAmount(payOrder.getAmount());
        chargeDto.setDisputed(false);
        chargeDto.setResource(ResourceType.IOS);
        chargeDto.setPaid(true);
        chargeDto.paymentMethod(PaymentMethodType.fromValue(payOrder.getPayExtraChannel()));
        chargeDto.setObject("charge");
        chargeDto.setStatus(ChargeStatusType.CAPTURED);
        chargeDto.setPaidTime(timeStamp);
        chargeDto.setCreateTime(timeStamp);
        CardInfo cardInfo = new CardInfo();
        cardInfo.setThreeDSecure(false);
        cardInfo.setWallet(payOrder.getPayExtraChannel());
        chargeDto.setCardInfo(cardInfo);
        elePayNotificationRequest.setData(new ElePayNotificationModel.DataObject().setObject(chargeDto));
        elePayNotificationRequest.setLiveMode(true);
        elePayNotificationRequest.setId("wh_et"+appId);
        elePayNotificationRequest.setType("charge.succeeded");
        elePayNotificationRequest.setObject("event");
        return elePayNotificationRequest;

    }*/
}
