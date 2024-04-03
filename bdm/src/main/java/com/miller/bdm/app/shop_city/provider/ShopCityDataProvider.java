package com.miller.bdm.app.shop_city.provider;

import com.miller.bdm.app.shop_city.request.ShopCityRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-商家城市列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@SuppressWarnings(value = "unused")
public class ShopCityDataProvider {
    static Stream<Arguments> ShopCityList() {
        ShopCityRequestDTO ShopCityRequestDTO = new ShopCityRequestDTO();

        return Stream.of(
                arguments(ShopCityRequestDTO)
        );
    }

}
