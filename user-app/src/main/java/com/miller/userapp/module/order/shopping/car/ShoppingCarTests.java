package com.miller.userapp.module.order.shopping.car;

import com.hungrypanda.app.server.dto.order.cart.ShoppingCartDO;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.shopping.car.flow.ShoppingCarFlow;
import com.miller.userapp.module.order.shopping.car.request.ShoppingCarRequestDTO;
import com.miller.userapp.module.order.shopping.car.response.ShoppingCarResponseDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @MethodSource("addProductToShoppingCar")
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

    /**
     * 添加商品到购物车
     *
     * @return 购物车请求DTO
     */
    static Stream<Arguments> addProductToShoppingCar() {
        ShoppingCarRequestDTO shoppingCarRequestDTO = new ShoppingCarRequestDTO();
        shoppingCarRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        shoppingCarRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);

        // 构造商品列表
        List<ShoppingCartDO.Item> items = new ArrayList<>();
        // 构造商品数据
        ShoppingCartDO.Item item = new ShoppingCartDO.Item();
        item.setProductId(TestCaseDataForMerchantConstant.productId);
        item.setSkuId(TestCaseDataForMerchantConstant.skuId);
        item.setPurchaseTime(System.currentTimeMillis());

        // 将商品添加到商品列表中
        items.add(item);
        shoppingCarRequestDTO.setItems(items);

        return Stream.of(Arguments.of(shoppingCarRequestDTO));
    }
}
