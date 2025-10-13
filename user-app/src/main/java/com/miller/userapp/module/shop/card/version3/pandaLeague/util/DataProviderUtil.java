package com.miller.userapp.module.shop.card.version3.pandaLeague.util;

import com.miller.userapp.module.shop.card.version3.pandaLeague.request.ShopListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * 测试数据提供工具类
 */
public class DataProviderUtil {
    /**
     * 测试用例数据提供者
     */
    public static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        shopListRequestDTO.setFiltering(false);
        shopListRequestDTO.setTabType((byte) 1);
        shopListRequestDTO.setRedPacketList(new ArrayList<>());
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
