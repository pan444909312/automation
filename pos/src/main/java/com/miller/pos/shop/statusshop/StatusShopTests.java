package com.miller.pos.shop.statusshop;

import com.miller.pos.shop.statusshop.flow.StatusShopFlow;
import com.miller.pos.shop.statusshop.request.StatusShopRequestDTO;
import com.miller.pos.shop.statusshop.response.StatusShopResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_店铺查询
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 20:37:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("pos--shop--查询店铺状态")
public class StatusShopTests {

    @MethodSource("com.miller.pos.shop.statusshop.provider.StatusShopDataProvider#statusshop")
    @ParameterizedTest
    @DisplayName("正常流程_查询店铺")
    void shouldProductListSuccessfully(StatusShopRequestDTO statusShopRequestDTO) {
        StatusShopResponseDTO statusShopResponseDTO = StatusShopFlow.queryShop(statusShopRequestDTO);
        assertThat(statusShopResponseDTO.getCode()).isEqualTo(0);
        System.out.println("statusShopResponseDTO.getData()");
        System.out.println(statusShopResponseDTO.getData());



    }
}
