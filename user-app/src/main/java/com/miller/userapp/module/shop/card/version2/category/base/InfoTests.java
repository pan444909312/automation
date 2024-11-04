package com.miller.userapp.module.shop.card.version2.category.base;

import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.shop.card.version2.category.base.flow.InfoFlow;
import com.miller.userapp.module.shop.card.version2.category.base.request.InfoRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.base.response.InfoResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author heyuan
 * @version 1.0
 * @create: 2024-04-30 16:52
 */
@EnvTag.Test
@Scenario(scenarioID = "01J5CW57N1KMMD1W7D0S2SKWYC", scenarioName = "店铺首页-店铺信息", developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("店铺首页-店铺信息")
public class InfoTests {
    @MethodSource("getBaseInfo")
    @ParameterizedTest
    @DisplayName("正常流程_店铺首页-店铺信息")
    void shouldgetShopBaseInfoSuccessfully(InfoRequestDTO infoRequestDTO) {
        InfoResponseDTO infoResponseDTO = InfoFlow.getShopInfo(infoRequestDTO);
        assertThat(infoResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(infoResponseDTO.getSuccess()).isTrue();
        // 校验接口返回的商品ID是添加时的商品ID
        assertThat(infoResponseDTO.getResult().getShopId()).isEqualTo(infoRequestDTO.getShopId());
    }

    static Stream<Arguments> getBaseInfo() {
        InfoRequestDTO infoRequestDTO = new InfoRequestDTO();
        infoRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);

        return Stream.of(Arguments.of(infoRequestDTO));
    }

}
