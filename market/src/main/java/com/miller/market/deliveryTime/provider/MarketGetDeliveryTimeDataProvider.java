package com.miller.market.deliveryTime.provider;

import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.deliveryTime.request.MarketGetDeliveryTimeRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_配送时间数据
 *
 */
@SuppressWarnings(value = "unused")
public class MarketGetDeliveryTimeDataProvider {

    /**
     * 送货上门
     */
    static Stream<Arguments> marketDeliveryTimeDataProvider() {
        MarketGetDeliveryTimeRequestDTO order1 = new MarketGetDeliveryTimeRequestDTO();
        order1.setIsPreOrder(0);
        order1.setAddressId(TestCaseDataForMarketConstant.addressId);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order1)
        );
    }

}
