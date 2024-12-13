package com.miller.market.redPacket.getRedPacketListByOrder;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.mapper.order.OrderMapper;
import com.miller.market.redPacket.getRedPacketListByOrder.flow.MarketGetRedPacketListByOrderFlow;
import com.miller.market.redPacket.getRedPacketListByOrder.request.MarketGetRedPacketListByOrderRequestDTO;
import com.miller.market.redPacket.getRedPacketListByOrder.response.MarketGetRedPacketListByOrderResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 获取订单可用红包列表
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_获取订单pf红包&优惠券列表")
public class MarketGetRedPacketListByOrderTests {
    private static OrderMapper orderMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        orderMapper = sqlSession.getMapper(OrderMapper.class);

    }
    @MethodSource("staticRedPacketProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程-获取订单pf红包列表")
    void getRedPacketListByOrderSuccessfully(MarketGetRedPacketListByOrderRequestDTO marketGetRedPacketListByOrderRequestDTO) {
        MarketGetRedPacketListByOrderResponseDTO marketGetRedPacketListByOrderResponseDTO = MarketGetRedPacketListByOrderFlow.getRedPacketListByOrder(marketGetRedPacketListByOrderRequestDTO);

        Assertions.assertThat(marketGetRedPacketListByOrderResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetRedPacketListByOrderResponseDTO.getData()).isNotNull();

    }

    @MethodSource("staticCouponProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程-获取订单pf优惠券列表")
    void getCouponListByOrderSuccessfully(MarketGetRedPacketListByOrderRequestDTO marketGetRedPacketListByOrderRequestDTO) {

        MarketGetRedPacketListByOrderResponseDTO marketGetRedPacketListByOrderResponseDTO = MarketGetRedPacketListByOrderFlow.getRedPacketListByOrder(marketGetRedPacketListByOrderRequestDTO);

        Assertions.assertThat(marketGetRedPacketListByOrderResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetRedPacketListByOrderResponseDTO.getData()).isNotNull();

    }

    /**
     * 获取pf红包
     */
    static Stream<Arguments> staticRedPacketProvider() {
        MarketGetRedPacketListByOrderRequestDTO requestDTO = new MarketGetRedPacketListByOrderRequestDTO();
        /**
         * 红包类型 默认 2 0：红包， 1：商品券  2:全部
         */
        requestDTO.setRedPacketCustomerType(0);
        requestDTO.setGoodPriceCount("999.99");
        return Stream.of(Arguments.of(requestDTO));
    }

    /**
     * 获取pf优惠券
     */
    static Stream<Arguments> staticCouponProvider() {
        MarketGetRedPacketListByOrderRequestDTO requestDTO = new MarketGetRedPacketListByOrderRequestDTO();
        /**
         * 红包类型 默认 2 0：红包， 1：商品券  2:全部
         */
        requestDTO.setRedPacketCustomerType(1);
        requestDTO.setGoodPriceCount("999.99");
        return Stream.of(Arguments.of(requestDTO));
    }

}
