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
        order1.setAutoDeductRedPacket(1);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order1)
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
        order1.setAutoDeductRedPacket(1);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order1)
        );
    }
}
