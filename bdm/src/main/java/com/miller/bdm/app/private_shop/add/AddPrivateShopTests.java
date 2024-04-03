package com.miller.bdm.app.private_shop.add;

import com.miller.bdm.app.private_shop.add.flow.AddPrivateShopFlow;
import com.miller.bdm.app.private_shop.add.request.AddPrivateShopRequestDTO;
import com.miller.bdm.app.private_shop.add.response.AddPrivateShopResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * bdm-移动端私海池-私海池商家-新增
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-移动端私海池-私海池商家-新增")
public class AddPrivateShopTests {
    @MethodSource("com.miller.bdm.app.private_shop.add.provider.AddPrivateShopDataProvider#AddPrivateShopList")
    @ParameterizedTest
    @DisplayName("bdm-移动端私海池-私海池商家-新增")
    void QueryPrivateShopList(AddPrivateShopRequestDTO privateShopRequestDTO) {
        AddPrivateShopResponseDTO privateShopResponseDTO = AddPrivateShopFlow.getPageList(privateShopRequestDTO);
        assertThat(privateShopResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(privateShopResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        CacheUtils.set(BusinessConstantOfERP.PRIVATE_SHOP_ID_KEY, privateShopResponseDTO);
    }

}
