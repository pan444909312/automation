package com.miller.userapp.shop.base.provider;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.userapp.shop.base.request.InfoRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * @description:
 * @author: hey
 * @create: 2024-04-30 16:24
 */
public class InfoDataProvider {
    static Stream<Arguments> getBaseInfo() {
        InfoRequestDTO infoRequestDTO = new InfoRequestDTO();
        infoRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);

        return Stream.of(Arguments.of(infoRequestDTO));
    }
}
