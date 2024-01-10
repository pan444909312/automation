package com.miller.userapp.order.shopping.car;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.shopping.car.flow.ShoppingCarFlow;
import com.miller.userapp.order.shopping.car.request.ShoppingCarRequestDTO;
import com.miller.userapp.order.shopping.car.response.ShoppingCarResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_购物车
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/10 14:27:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-购物车")
public class ShoppingCarTests {

    @MethodSource("com.miller.userapp.order.shopping.car.provider.ShoppingCarDataProvider#addProductToShoppingCar")
    @ParameterizedTest
    @DisplayName("正常流程_添加单个商品到购物车")
    void shouldAddProductToShoppingCarSuccessfully(ShoppingCarRequestDTO shoppingCarRequestDTO) {
        ShoppingCarResponseDTO shoppingCarResponseDTO = ShoppingCarFlow.addProductToShoppingCar(shoppingCarRequestDTO);
        assertThat(shoppingCarResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(shoppingCarResponseDTO.getSuccess()).isTrue();
        // 校验接口返回的商品ID是添加时的商品ID
        assertThat(shoppingCarResponseDTO.getResult().getCart().getShopId()).isEqualTo(shoppingCarRequestDTO.getShopId());
        assertThat(shoppingCarResponseDTO.getResult().getCart().getItems().size()).isGreaterThanOrEqualTo(1);
    }
}
