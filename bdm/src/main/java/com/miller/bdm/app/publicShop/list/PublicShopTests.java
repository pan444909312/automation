package com.miller.bdm.app.publicShop.list;

import com.miller.bdm.app.publicShop.list.flow.PublicShopFlow;
import com.miller.bdm.app.publicShop.list.request.PublicShopRequestDTO;


import com.miller.bdm.app.publicShop.list.response.PublicShopResponseDTO;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * bdm-移动端公海池-公海池商家列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/1/2 11:11:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-移动端公海池-公海池商家列表")
public class PublicShopTests {
    @MethodSource("com.miller.bdm.app.publicShop.list.provider.PublicShopDataProvider#PublicShopList")
    @ParameterizedTest
    @DisplayName("bdm-移动端公海池-公海池商家列表")
    void QueryPublicShopList(PublicShopRequestDTO publicShopRequestDTO) {
        PublicShopResponseDTO publicShopResponseDTO = PublicShopFlow.getPageList(publicShopRequestDTO);
        assertThat(publicShopResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(publicShopResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        assertThat(publicShopResponseDTO.getData().getList()).isNotNull();

    }

}
