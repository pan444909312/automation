package com.miller.bdm.app.publicShop.list.provider;

import com.miller.bdm.app.publicShop.list.request.PublicShopRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-移动端公海池-公海池商家列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/1/2 11:10:12
 */
@SuppressWarnings(value = "unused")
public class PublicShopDataProvider {
    static Stream<Arguments> PublicShopList() {
        PublicShopRequestDTO publicShopRequestDTO = new PublicShopRequestDTO();
        publicShopRequestDTO.setPageNo(1);
        publicShopRequestDTO.setPageSize(20);
        publicShopRequestDTO.setLocationLatitude(BigDecimal.valueOf(30.203607));
        publicShopRequestDTO.setLocationLongitude(BigDecimal.valueOf(120.2171314));

        return Stream.of(
                arguments(publicShopRequestDTO)
        );
    }
}
