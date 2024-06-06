package com.miller.bdm.app.privateShop.list;

import com.miller.bdm.app.privateShop.list.flow.PrivateShopFlow;
import com.miller.bdm.app.privateShop.list.request.PrivateShopRequestDTO;
import com.miller.bdm.app.privateShop.list.response.PrivateShopResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * bdm-移动端私海池-私海池商家列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-移动端公海池-公海池商家列表")
public class PrivateShopTests {
    @MethodSource("com.miller.bdm.app.privateShop.list.provider.PrivateShopDataProvider#PrivateShopList")
    @ParameterizedTest
    @DisplayName("bdm-移动端公海池-公海池商家列表")
    void QueryPrivateShopList(PrivateShopRequestDTO privateShopRequestDTO) {
        PrivateShopResponseDTO privateShopResponseDTO = PrivateShopFlow.getPageList(privateShopRequestDTO);
        assertThat(privateShopResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(privateShopResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        assertThat(privateShopResponseDTO.getData().getList()).isNotNull();
    }

    @Test
    @DisplayName("bdm-移动端公海池-根据城市搜索-公海池商家列表")
    void QueryPrivateShopListByCity() {
        PrivateShopResponseDTO privateShopResponseDTO = PrivateShopFlow.getPageListByCity(BusinessConstantOfERP.TEST_CITY);
        assertThat(privateShopResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(privateShopResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        assertThat(privateShopResponseDTO.getData().getList()).isNotNull();
    }

}
