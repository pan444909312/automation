package com.miller.market.order.getPreOrderInfo.provider;

import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.order.getPreOrderInfo.request.MarketGetPreOrderInfoRequestDTO;
import org.junit.jupiter.params.provider.Arguments;


import java.util.stream.Stream;

/**
 * 数据提供者_预订单数据
 *
 */
@SuppressWarnings(value = "unused")
public class MarketGetPreOrderInfoDataProvider {

    /**
     * 送货上门-未选择时间
     * deliveryMethod=1
     */
    static Stream<Arguments> marketDeliveryWithoutTimeDataProvider() {
        MarketGetPreOrderInfoRequestDTO order1 = new MarketGetPreOrderInfoRequestDTO();
        order1.setIsPfRedPacket(1);
        order1.setDeliveryMethod(1);
        order1.setAddressId(TestCaseDataForMarketConstant.addressId);
        // 是否自动抵扣红包
        order1.setAutoDeductRedPacket(1);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order1)
        );
    }

    /**
     * 送货上门-选择时间
     * deliveryMethod=1
     */
    static Stream<Arguments> marketDeliveryTimeDataProvider() {
        MarketGetPreOrderInfoRequestDTO order2 = new MarketGetPreOrderInfoRequestDTO();
        order2.setIsPfRedPacket(0);
        // 配送方式：1配送
        order2.setDeliveryMethod(1);
        order2.setAddressId(TestCaseDataForMarketConstant.addressId);
        order2.setDeliveryTimeId(TestCaseDataForMarketConstant.deliveryTime.getDeliveryTimeId());
        order2.setDeliveryInTimeId(TestCaseDataForMarketConstant.deliveryTime.getDeliveryInTimeId());
        order2.setDeliveryTime(TestCaseDataForMarketConstant.deliveryTime.getTimeFulls().get(0).getDeliveryTime());
        order2.setDeliveryDate(TestCaseDataForMarketConstant.deliveryTime.getDate());
        // 是否自动抵扣红包
        order2.setAutoDeductRedPacket(0);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order2)
        );
    }

    /**
     * 自提-未选择时间
     * deliveryMethod=2
     */
    static Stream<Arguments> marketTakeWithoutTimeDataProvider() {
        MarketGetPreOrderInfoRequestDTO order1 = new MarketGetPreOrderInfoRequestDTO();
        order1.setIsPfRedPacket(1);
        order1.setDeliveryMethod(2);
        // 是否自动抵扣红包
        order1.setAutoDeductRedPacket(1);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order1)
        );
    }

    /**
     * 自取-选择时间
     * deliveryMethod=2
     */
    static Stream<Arguments> marketTakeTimeDataProvider() {
        MarketGetPreOrderInfoRequestDTO order1 = new MarketGetPreOrderInfoRequestDTO();
        order1.setIsPfRedPacket(0);
        // 配送方式：2自取
        order1.setDeliveryMethod(2);
        order1.setAddressId(TestCaseDataForMarketConstant.addressId);
        order1.setTakesTimeId(TestCaseDataForMarketConstant.takesTime.getTakesTimeId());
        order1.setDeliveryTime(TestCaseDataForMarketConstant.takesTime.getTimeFulls().get(0).getDeliveryTime());
        order1.setDeliveryDate(TestCaseDataForMarketConstant.takesTime.getDate());
        // 是否自动抵扣红包
        order1.setAutoDeductRedPacket(0);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order1)
        );
    }
}
