package com.miller.userapp.order.shopping.car.provider;

import com.hungrypanda.app.server.dto.order.cart.ShoppingCartDO;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.userapp.order.shopping.car.request.ShoppingCarRequestDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 数据提供者_购物车
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/10 13:44:33
 */
@SuppressWarnings("unused")
public class ShoppingCarDataProvider {

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
