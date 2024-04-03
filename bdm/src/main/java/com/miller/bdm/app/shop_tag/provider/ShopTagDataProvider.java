package com.miller.bdm.app.shop_tag.provider;

import com.miller.bdm.app.shop_tag.request.ShopTagRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-商家標籤列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@SuppressWarnings(value = "unused")
public class ShopTagDataProvider {
    static Stream<Arguments> ShopTagList() {
        ShopTagRequestDTO ShopTagRequestDTO = new ShopTagRequestDTO();

        return Stream.of(
                arguments(ShopTagRequestDTO)
        );
    }

}
