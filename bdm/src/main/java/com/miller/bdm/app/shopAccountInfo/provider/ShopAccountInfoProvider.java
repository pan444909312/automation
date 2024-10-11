package com.miller.bdm.app.shopAccountInfo.provider;

import com.miller.bdm.app.shopAccountInfo.request.ShopAccountInfoRequestDTO;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**352297264
 * 数据提供者_登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 18:10:12
 */
public class ShopAccountInfoProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> ShopTagList() {
        ShopAccountInfoRequestDTO shopAccountInfoRequestDTO = new ShopAccountInfoRequestDTO();
        shopAccountInfoRequestDTO.setShopId(352297264L);

        return Stream.of(
                arguments(shopAccountInfoRequestDTO)
        );
    }
}
