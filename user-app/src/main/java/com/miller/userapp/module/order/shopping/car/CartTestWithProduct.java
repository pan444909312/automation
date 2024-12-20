package com.miller.userapp.module.order.shopping.car;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.dto.order.cart.ShoppingCartDO;
import com.hungrypanda.app.server.entity.product.ProductEntity;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.mapper.shop.ProductMapper;
import com.miller.userapp.module.order.shopping.car.flow.ShoppingCarFlow;
import com.miller.userapp.module.order.shopping.car.request.ShoppingCarRequestDTO;
import com.miller.userapp.module.order.shopping.car.response.ShoppingCarResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@EnvTag.Test
@Scenario(scenarioID = "01J9QNPACXKVRS7D0NGX917QY0", scenarioName = "购物车-商品限购", developmentTime = 50, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("购物车-商品限购")
public class CartTestWithProduct {
    private Integer buyLimitMin = 2;
    private Long buyLimitMinProductTest = 81669212L;

    @BeforeEach
    void beforeEach() {
        // 初始化，链接数据库
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(buyLimitMinProductTest);
        productEntity.setBuyLimitMin(buyLimitMin);
        var lambdaUpdateWrapper = new LambdaUpdateWrapper<ProductEntity>();
        lambdaUpdateWrapper
                .eq(ProductEntity::getProductId, buyLimitMinProductTest)
                .set(ProductEntity::getBuyLimitMin, buyLimitMin);
        productMapper.update(productEntity, lambdaUpdateWrapper);
    }

    @AfterEach
    void afterEach() {
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        var lambdaUpdateWrapper = new LambdaUpdateWrapper<ProductEntity>();
        lambdaUpdateWrapper
                .eq(ProductEntity::getProductId, buyLimitMinProductTest)
                .set(ProductEntity::getBuyLimitMin, 1);
        productMapper.update(new ProductEntity(), lambdaUpdateWrapper);
    }

    @MethodSource("buyLimitMinProduct")
    @ParameterizedTest
    @DisplayName("购物车-加购商品数量<商品起购数量")
    void cartPriceAfterFull(ShoppingCarRequestDTO shoppingCarRequestDTO) {

        ShoppingCarResponseDTO shoppingCarResponseDTO = ShoppingCarFlow.addProductToShoppingCar(shoppingCarRequestDTO);
        assertThat(shoppingCarResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(shoppingCarResponseDTO.getResult().getCart().getItems().size()).isGreaterThanOrEqualTo(1);
        assertThat(shoppingCarResponseDTO.getResult().getCart().isIsBuyLimitMinChange()).isTrue();
        assertThat(shoppingCarResponseDTO.getResult().getCart().getItems().stream().filter(value->value.getProductId().equals(buyLimitMinProductTest)).findFirst().get().getItemCount()).isEqualTo(buyLimitMin);
    }

    private Stream<Arguments> buyLimitMinProduct() {
        ShoppingCarRequestDTO shoppingCartRequestDTO = new ShoppingCarRequestDTO();
        List<ShoppingCartDO.Item> items = new ArrayList<>();
        ShoppingCartDO.Item item = new ShoppingCartDO.Item();
        item.setProductId(buyLimitMinProductTest);
        item.setSkuId(0L);
        item.setPurchaseTime(1709707433126L);
        items.add(item);

        shoppingCartRequestDTO.setShopId(59750820L);
        shoppingCartRequestDTO.setDeliveryType(1);
        shoppingCartRequestDTO.setUserId(1398708422L);
        shoppingCartRequestDTO.setItems(items);
        return Stream.of(
                arguments(shoppingCartRequestDTO)
        );
    }

}
