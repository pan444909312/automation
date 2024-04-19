package com.miller.bdm.app.public_shop.list1.provider;

import com.miller.bdm.app.public_shop.list1.request.PublicShopSeachRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-移动端公海池-公海池搜索后商家列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:10:12
 */
@SuppressWarnings(value = "unused")
public class PublicShopSeachDataProvider {
    static Stream<Arguments> PublicShopList1() {
        PublicShopSeachRequestDTO publicShopSeachRequestDTO = new PublicShopSeachRequestDTO();
        publicShopSeachRequestDTO.setPageNo(1);
        publicShopSeachRequestDTO.setPageSize(20);
        publicShopSeachRequestDTO.setLocationLatitude(BigDecimal.valueOf(30.203607));
        publicShopSeachRequestDTO.setLocationLongitude(BigDecimal.valueOf(120.2171314));
        publicShopSeachRequestDTO.setSearchContent("测试");

        return Stream.of(
                arguments(publicShopSeachRequestDTO)
        );
    }
}
