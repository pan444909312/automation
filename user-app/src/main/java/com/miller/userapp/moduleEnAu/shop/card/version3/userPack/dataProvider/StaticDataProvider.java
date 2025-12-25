package com.miller.userapp.moduleEnAu.shop.card.version3.userPack.dataProvider;

import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.request.ShopListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class StaticDataProvider {
    static Stream<Arguments> StaticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 自取频道店铺流必须传经纬度
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setLongitude("115.954100");
        shopListRequestDTO.setLatitude("29.660580");
        shopListRequestDTO.setIsNeedMarketCategory(1);
        shopListRequestDTO.setMarketCategoryId(0);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
