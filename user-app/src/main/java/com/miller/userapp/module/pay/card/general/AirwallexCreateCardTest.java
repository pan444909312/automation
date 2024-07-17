package com.miller.userapp.module.pay.card.general;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.payserver.api.res.CommonPaymentCardDTO;
import com.hungrypanda.payserver.entity.PayCardRecord;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.data.pay.db.PayCardRecordSql;
import com.miller.userapp.module.pay.card.general.flow.GeneralCreateCardFlow;
import com.miller.userapp.module.pay.card.general.flow.GeneralDeleteCardFlow;
import com.miller.userapp.module.pay.card.general.flow.GeneralQueryCardListFlow;
import com.miller.userapp.module.pay.card.general.request.GeneralCreateCardRequest;
import com.miller.userapp.module.pay.card.general.request.GeneralDeleteCardRequest;
import com.miller.userapp.module.pay.card.general.request.GeneralQueryCardListRequest;
import com.miller.userapp.module.pay.card.general.response.GeneralCreateCardResponse;
import com.miller.userapp.module.pay.card.general.response.GeneralDeleteCardResponse;
import com.miller.userapp.module.pay.card.general.response.GeneralQueryCardListResponse;
import com.miller.userapp.module.pay.card.stripe.flow.AddCardRecordFlow;
import com.miller.userapp.module.pay.card.stripe.request.AddCardRecordRequestDTO;
import com.miller.userapp.module.pay.card.stripe.response.AddCardRecordResponseDTO;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.miller.common.util.MD5Util.string2MD5;
import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("airwallex绑卡接口测试")
public class AirwallexCreateCardTest {
    private PayCardRecordSql payCardRecordSql;
    private static String  cardNumberMd5;
    private PayCardRecord payCardRecord;
    private String userId;

    @BeforeAll
    public void beforeAll(){
        payCardRecordSql = new PayCardRecordSql();
        cardNumberMd5 = string2MD5(PaymentConstant.CARDNUMBER_AIRWALLEX.replaceAll(" ",""));
        userId = PropertiesUtils.getProperty("user.app.account.of.user002.account.id");
        payCardRecord = payCardRecordSql.getPayCardRecord(cardNumberMd5, userId,"AUS");
    }
    @MethodSource("createCardDataProvider4Airwallex")
    @ParameterizedTest
    @DisplayName("正常流程_airwallex创建卡")
    @DependsOnMethod("queryCardList4AirwallexTest")
    void createCard4AirwallexTest(GeneralCreateCardRequest request ) {
        GeneralCreateCardResponse result = GeneralCreateCardFlow.createCard(request,"AU");
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getResult().getCardNoMd5()).isEqualTo(cardNumberMd5);
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        payCardRecord = payCardRecordSql.getPayCardRecord(cardNumberMd5, userId,"AUS");
        System.out.println("payCardRecord: "+JSON.toJSON(payCardRecord));
        assertThat(payCardRecord).isNotNull();
    }
    public static void main(String[] args){
        String cardNumberMd5 = string2MD5(PaymentConstant.CARDNUMBER_AIRWALLEX.replaceAll(" ",""));
        String userId = PropertiesUtils.getProperty("user.app.account.of.user002.account.id");
        PayCardRecordSql sql = new PayCardRecordSql();
        PayCardRecord payCardRecord1 = sql.getPayCardRecord(cardNumberMd5,userId,"AUS");
        System.out.println("payCardRecord: "+JSON.toJSON(payCardRecord1));
        int value = sql.deletePayCardRecord(payCardRecord1);
        System.out.println("value: "+value);
    }

    @MethodSource("addCardRecordDataProvider4Airwallex")
    @ParameterizedTest
    @DisplayName("正常流程_airwallex添加卡")
    void addCardRecord4AirwallexTest(AddCardRecordRequestDTO addCardRecordRequestDTO) {
        AddCardRecordResponseDTO result = AddCardRecordFlow.addCardRecord(addCardRecordRequestDTO,"AU");
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getSuccess()).isTrue();
    }
    public

    @MethodSource("queryCardListDataProvider4Airwallex")
    @ParameterizedTest
    @DisplayName("正常流程_airwallex查询卡")
    void queryCardList4AirwallexTest(GeneralQueryCardListRequest request) {
        GeneralQueryCardListResponse result = GeneralQueryCardListFlow.queryCardList(request,"AU");
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getSuccess()).isTrue();
        if(!result.getResult().getList().isEmpty()){ //有数据则删除对应md5的卡
            List<CommonPaymentCardDTO> commonCardList = result.getResult().getList();
           Optional<CommonPaymentCardDTO> actualCard = commonCardList.stream().filter(a->a.getCardNoMd5().equals(cardNumberMd5)).findAny();
           if(actualCard.isPresent()){
               GeneralDeleteCardRequest deleteCardRequest = new GeneralDeleteCardRequest();
               deleteCardRequest.setPayChannel("airwallexPay");
               deleteCardRequest.setPaymentCardToken(actualCard.get().getPaymentCardToken());
               GeneralDeleteCardResponse deleteCardResponse = GeneralDeleteCardFlow.deleteCard(deleteCardRequest,"AU");
               assertThat(deleteCardResponse.getResultCode()).isEqualTo(ResponseConstant.resultCode);
               assertThat(deleteCardResponse.getSuccess()).isTrue();
           }
        }else if(Objects.nonNull(payCardRecord)){
            int value =  payCardRecordSql.deletePayCardRecord(payCardRecord);
            System.out.println("value: "+value);
        }
    }

    static Stream<Arguments> addCardRecordDataProvider4Airwallex(){
        AddCardRecordRequestDTO addCardRecordRequestDTO = new AddCardRecordRequestDTO();

//        String cardNumberMd5 = string2MD5(PaymentConstant.CARDNUMBER_AIRWALLEX.replaceAll(" ",""));
        addCardRecordRequestDTO.setCardMd5(cardNumberMd5);
        addCardRecordRequestDTO.setCardLast4(PaymentConstant.CARDNUMBER_AIRWALLEX.split(" ")[3]);
        return Stream.of(
                Arguments.of(addCardRecordRequestDTO)
        );
    }
    static Stream<Arguments> createCardDataProvider4Airwallex(){
        GeneralCreateCardRequest request = new GeneralCreateCardRequest();

//        String cardNumberMd5 = string2MD5(PaymentConstant.CARDNUMBER_AIRWALLEX.replaceAll(" ",""));
        request.setCardNoMd5(cardNumberMd5);
        request.setLast4(PaymentConstant.CARDNUMBER_AIRWALLEX.split(" ")[3]);
        request.setExpYear("2029");
        request.setExpMonth("07");
        request.setPayChannel("airwallexPay");
        return Stream.of(
                Arguments.of(request)
        );
    }
    static Stream<Arguments> queryCardListDataProvider4Airwallex(){
        GeneralQueryCardListRequest request = new GeneralQueryCardListRequest();
        request.setPayChannel("airwallexPay");
        return Stream.of(
                Arguments.of(request)
        );
    }
}
