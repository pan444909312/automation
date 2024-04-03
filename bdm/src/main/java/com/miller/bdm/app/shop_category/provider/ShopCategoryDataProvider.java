package com.miller.bdm.app.shop_category.provider;

import com.miller.bdm.app.shop_category.request.ShopCategoryRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-商家类目列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@SuppressWarnings(value = "unused")
public class ShopCategoryDataProvider {
    static Stream<Arguments> ShopCategoryList() {
        ShopCategoryRequestDTO ShopCategoryRequestDTO = new ShopCategoryRequestDTO();

        return Stream.of(
                arguments(ShopCategoryRequestDTO)
        );
    }

}
