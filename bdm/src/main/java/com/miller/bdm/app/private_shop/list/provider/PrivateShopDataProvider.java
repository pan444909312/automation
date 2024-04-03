package com.miller.bdm.app.private_shop.list.provider;

import com.miller.bdm.app.private_shop.list.request.PrivateShopRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-移动端私海池-私海池商家列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@SuppressWarnings(value = "unused")
public class PrivateShopDataProvider {
    static Stream<Arguments> PrivateShopList() {
        PrivateShopRequestDTO privateShopRequestDTO = new PrivateShopRequestDTO();
        privateShopRequestDTO.setPageNo(1);
        privateShopRequestDTO.setPageSize(20);
        privateShopRequestDTO.setLocationLatitude(BigDecimal.valueOf(30.203607));
        privateShopRequestDTO.setLocationLongitude(BigDecimal.valueOf(120.2171314));
        return Stream.of(
                arguments(privateShopRequestDTO)
        );
    }

}
