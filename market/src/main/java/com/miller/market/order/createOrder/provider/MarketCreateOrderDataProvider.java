package com.miller.market.order.createOrder.provider;

import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.order.createOrder.request.MarketCreateOrderRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_订单数据
 *
 */
@SuppressWarnings(value = "unused")
public class MarketCreateOrderDataProvider {

    /**
     * 送货上门
     * deliveryMethod=1
     */
    static Stream<Arguments> marketDeliveryTimeDataProvider() {
        MarketCreateOrderRequestDTO order1 = new MarketCreateOrderRequestDTO();
        order1.setIsPfRedPacket(0);
        // 配送方式：1配送
        order1.setDeliveryMethod(1);
        order1.setAddressId(TestCaseDataForMarketConstant.addressId);
        order1.setDeliveryTimeId(TestCaseDataForMarketConstant.deliveryTime.getDeliveryTimeId());
        order1.setDeliveryInTimeId(TestCaseDataForMarketConstant.deliveryTime.getDeliveryInTimeId());
        order1.setDeliveryTime(TestCaseDataForMarketConstant.deliveryTime.getTimeFulls().get(0).getDeliveryTime());
        order1.setDeliveryDate(TestCaseDataForMarketConstant.deliveryTime.getDate());
        // 小费
        order1.setTips("1.11");
        // 交付方式
        order1.setDeliverableAction(2);
        order1.setRemark("接口测试配送订单");
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order1)
        );
    }

    /**
     * 自提
     * deliveryMethod=2
     */
    static Stream<Arguments> marketTakeTimeDataProvider() {
        MarketCreateOrderRequestDTO order1 = new MarketCreateOrderRequestDTO();
        order1.setIsPfRedPacket(0);
        // 配送方式：2自取
        order1.setDeliveryMethod(2);
        order1.setAddressId(TestCaseDataForMarketConstant.addressId);
        order1.setTakesTimeId(TestCaseDataForMarketConstant.takesTime.getTakesTimeId());
        order1.setTakesTime(TestCaseDataForMarketConstant.takesTime.getTimeFulls().get(0).getDeliveryTime());
        order1.setTakesDate(TestCaseDataForMarketConstant.takesTime.getDate());
        // 小费
        order1.setTips("1.11");
        // 交付方式
        order1.setDeliverableAction(2);
        order1.setRemark("接口测试自取订单");
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order1)
        );
    }
}
