package com.miller.market.system.sendShortMessage;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.system.sendShortMessage.flow.MarketSendShortMessageFlow;
import com.miller.market.system.sendShortMessage.request.MarketSendShortMessageRequestDTO;
import com.miller.market.system.sendShortMessage.response.MarketSendShortMessageResponseDTO;
import com.miller.market.util.RedisUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.common.constants.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 发送短信
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_发信短信验证码")
public class MarketSendShortMessageWithoutLoginTests {


    @MethodSource("staticPhoneProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_发送验证码成功")
    void sendShortMessageSuccessfully(MarketSendShortMessageRequestDTO marketSendShortMessageRequestDTO) {
        MarketSendShortMessageResponseDTO marketSendShortMessageResponseDTO = MarketSendShortMessageFlow.sendShortMessage(marketSendShortMessageRequestDTO);

        Assertions.assertThat(marketSendShortMessageResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketSendShortMessageResponseDTO.getData().getResult()).isTrue();

        //提取缓存中的验证码
        String rediskey = Constants.SMS_CODE_REDIS_KEY + marketSendShortMessageRequestDTO.getAreaCode() + marketSendShortMessageRequestDTO.getPhone();
        String redisValue = RedisUtils.getRedisInstance().get(rediskey).toString();
        System.out.println("---rediskey为："+rediskey+"value为："+redisValue);
        Assertions.assertThat(redisValue).isNotEmpty();

        BusinessConstant.code = redisValue;

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticPhoneProvider() {
        // 查询待支付订单
        MarketSendShortMessageRequestDTO requestDTO = new MarketSendShortMessageRequestDTO();
        requestDTO.setPhone(BusinessConstant.phone);
        requestDTO.setAreaCode("86");
        return Stream.of(Arguments.of(requestDTO));
    }

}
