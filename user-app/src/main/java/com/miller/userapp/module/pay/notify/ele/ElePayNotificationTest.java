package com.miller.userapp.module.pay.notify.ele;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.payserver.api.enums.PayChannelEnum;
import com.hungrypanda.payserver.biz.payment.elepay.model.ElePayNotificationModel;
import com.hungrypanda.payserver.entity.PayOrder;
import com.miller.common.util.MD5Util;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.module.data.pay.db.PayOrderSql;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.notify.ele.flow.ElePayNotificationFlow;
import com.miller.userapp.module.pay.notify.ele.request.ElePayNotificationRequest;

import com.miller.userapp.util.DBUtils;
import io.elepay.client.charge.pojo.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("ElePay回调接口")
public class ElePayNotificationTest {
    private static PayOrderSql payOrderSql;
    private static String orderSn;
    @BeforeAll
    public void beforeAll(){
        SqlSession sqlPaySession = DBUtils.getDBOfPandaPayTest();
        payOrderSql = new PayOrderSql(sqlPaySession);
        CreateOrderResponseDTO createOrderResponseDTO = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class);
        orderSn = createOrderResponseDTO.getResult().getOrderSn();
    }
    @MethodSource("elePayNotificationDataProvider")
    @ParameterizedTest
    @DisplayName("ElePay回调接口")
    void elePayNotificationTest(ElePayNotificationRequest elePayNotificationRequest) {
        String result= ElePayNotificationFlow.elePaymentNotification(elePayNotificationRequest);
        System.out.println("result: "+result);
        assertThat(result).isEqualTo(PayChannelEnum.ELE_PAY.getResult());
        //修改表有延迟
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        PayOrder payOrder = payOrderSql.getPayOrder(elePayNotificationRequest.getData().getObject().getOrderNo(),true,true);
        System.out.println("payOrder: "+JSON.toJSONString(payOrder));
        assertThat(payOrder.getTradeStatus()).isEqualTo(1);
    }

    static Stream<Arguments> elePayNotificationDataProvider(){
        PayOrder payOrder = payOrderSql.getPayOrder(orderSn,false);
        Long timeStamp =Instant.now().toEpochMilli() ;
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
        chargeDto.setOrderNo(payOrder.getTradeNo());
        String payExtraChannel = payOrder.getPayExtraChannel().toLowerCase();
//        switch (payExtraChannel){
//            case "payPay":
//            case "auPay":
//                payExtraChannel = payExtraChannel.toLowerCase();
//                break;
//            case "aliApp":
//                payExtraChannel = payExtraChannel.toLowerCase()+"plus";
//                break;
//            default:
//                payExtraChannel = payExtraChannel.toLowerCase();
//                break;
//        }
        chargeDto.paymentMethod(PaymentMethodType.fromValue(payExtraChannel));
        chargeDto.setObject("charge");
        chargeDto.setStatus(ChargeStatusType.CAPTURED);
        chargeDto.setPaidTime(timeStamp);
        chargeDto.setCreateTime(timeStamp);
        CardInfo cardInfo = new CardInfo();
        cardInfo.setThreeDSecure(false);
        cardInfo.setWallet(payExtraChannel);
        chargeDto.setCardInfo(cardInfo);
        elePayNotificationRequest.setData(new ElePayNotificationModel.DataObject().setObject(chargeDto));
        elePayNotificationRequest.setLiveMode(true);
        elePayNotificationRequest.setId("wh_et"+appId);
        elePayNotificationRequest.setType("charge.succeeded");
        elePayNotificationRequest.setObject("event");
        elePayNotificationRequest.setCreateTime(timeStamp);

        return Stream.of(
                Arguments.of(elePayNotificationRequest)
        );
    }

}
