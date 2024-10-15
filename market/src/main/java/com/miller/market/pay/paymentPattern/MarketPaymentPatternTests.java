package com.miller.market.pay.paymentPattern;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.pay.paymentPattern.flow.MarketPaymentPatternFlow;
import com.miller.market.pay.paymentPattern.request.MarketPaymentPatternRequestDTO;
import com.miller.market.pay.paymentPattern.response.MarketPaymentPatternResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.dal.pay.PayItemBean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 获取支付信息接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("获取支付信息")
public class MarketPaymentPatternTests {

    @MethodSource("staticOrderDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_已登录_获取支付信息")
    void getPaymentPatternSuccessfully(MarketPaymentPatternRequestDTO requestDTO) {
        MarketPaymentPatternResponseDTO responseDTO = MarketPaymentPatternFlow.getPaymentPattern(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        //获取信用卡支付方式
        List<PayItemBean> patternDTOList = responseDTO.getData().getPatternDTOList();
        patternDTOList = patternDTOList.stream().filter(PayItemBean -> Objects.equals(PayItemBean.getPayWayName(),"信用卡")).collect(Collectors.toList());
        BusinessConstant.payItemBean = patternDTOList.get(0);
        System.out.println("打印打印打印"+patternDTOList);
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticOrderDataProvider() {
        MarketPaymentPatternRequestDTO requestDTO = new MarketPaymentPatternRequestDTO();

        requestDTO.setOrderSn(BusinessConstant.orderSn);

        return Stream.of(Arguments.of(requestDTO));
    }

}
