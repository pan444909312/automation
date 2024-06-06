package com.miller.bdm.app.publicShop.list1;


import com.miller.bdm.app.publicShop.list1.flow.PublicShopSearchFlow;
import com.miller.bdm.app.publicShop.list1.request.PublicShopSeachRequestDTO;
import com.miller.bdm.app.publicShop.list1.response.PublicShopSeachResponseDTO;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * bdm-移动端公海池-公海池搜索后商家列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:11:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-移动端公海池-公海池搜索后商家列表")
public class PublicShopSeachTests {
    @MethodSource("com.miller.bdm.app.publicShop.list1.provider.PublicShopSeachDataProvider#PublicShopList1")
    @ParameterizedTest
    @DisplayName("bdm-移动端公海池-公海池搜索后商家列表")
    void shouldQueryRefundListSuccessfully(PublicShopSeachRequestDTO publicShopSeachRequestDTO) {
        PublicShopSeachResponseDTO publicShopSeachResponseDTO = PublicShopSearchFlow.queryRefundList(publicShopSeachRequestDTO);
        assertThat(publicShopSeachResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(publicShopSeachResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
    }

}
