package com.miller.market.takesTime.provider;

import com.miller.market.takesTime.request.MarketGetTakesTimeRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_自取时间数据
 *
 */
@SuppressWarnings(value = "unused")
public class MarketGetTakesTimeDataProvider {

    /**
     * 自取
     */
    static Stream<Arguments> marketTakesTimeDataProvider() {
        MarketGetTakesTimeRequestDTO order1 = new MarketGetTakesTimeRequestDTO();
        order1.setIsPreOrder(0);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(order1)
        );
    }

}
