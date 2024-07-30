package com.miller.userapp.shop.base;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.shop.base.flow.InfoFlow;
import com.miller.userapp.shop.base.request.InfoRequestDTO;
import com.miller.userapp.shop.base.response.InfoResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @description:
 * @author: hey
 * @create: 2024-04-30 16:52
 */
@EnvTag.Test
@TestFramework
@DisplayName("店铺首页-店铺信息")
public class InfoTests {
    @MethodSource("com.miller.userapp.shop.base.provider.InfoDataProvider#getBaseInfo")
    @ParameterizedTest
    @DisplayName("正常流程_店铺首页-店铺信息")
    void shouldgetShopBaseInfoSuccessfully(InfoRequestDTO infoRequestDTO) {
        InfoResponseDTO infoResponseDTO = InfoFlow.getShopInfo(infoRequestDTO);
        assertThat(infoResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(infoResponseDTO.getSuccess()).isTrue();
        // 校验接口返回的商品ID是添加时的商品ID
        assertThat(infoResponseDTO.getResult().getShopId()).isEqualTo(infoRequestDTO.getShopId());
    }

}
