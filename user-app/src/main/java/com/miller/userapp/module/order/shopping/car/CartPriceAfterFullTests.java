package com.miller.userapp.module.order.shopping.car;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.dto.order.cart.ShoppingCartDO;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.mapper.redpacket.UserCdKeyMapper;
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
@TestFramework
@DisplayName("仅有满减优惠的购物车优惠后金额")
public class CartPriceAfterFullTests {

    @BeforeEach
    void beforeEach() {
        // 初始化，链接数据库
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        UserCdKeyMapper userCdKeyMapper = sqlSession.getMapper(UserCdKeyMapper.class);
        var lambdaUpdateWrapper = new LambdaUpdateWrapper<UserCdKeyEntity>();
        lambdaUpdateWrapper
                .eq(UserCdKeyEntity::getUserId, 1398708422L)
                .gt(UserCdKeyEntity::getEndTime, 1710223505718L)
                .set(UserCdKeyEntity::getIsUsed, 1);
        userCdKeyMapper.update(lambdaUpdateWrapper);
    }

    @AfterEach
    void afterEach() {
    }

    @MethodSource("shoppingCartDataProvider")
    @ParameterizedTest
    @DisplayName("配送-无折扣商品-仅满减_购物车优惠后金额")
    void cartPriceAfterFull(ShoppingCarRequestDTO shoppingCarRequestDTO) {
        ShoppingCarResponseDTO shoppingCarResponseDTO = ShoppingCarFlow.addProductToShoppingCar(shoppingCarRequestDTO);
        assertThat(shoppingCarResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        // 校验接口返回的商品ID是添加时的商品ID
        assertThat(shoppingCarResponseDTO.getResult().getCart().getShopId()).isEqualTo(shoppingCarRequestDTO.getShopId());
        assertThat(shoppingCarResponseDTO.getResult().getCart().getItems().size()).isGreaterThanOrEqualTo(1);
        Integer totalGoodsAmount = shoppingCarResponseDTO.getResult().getCart().getCountInfo().getTotalGoodsAmount();
        Integer subDiscount = shoppingCarResponseDTO.getResult().getCart().getCountInfo().getSubDiscount();
        Integer totalpackingCharges = shoppingCarResponseDTO.getResult().getCart().getCountInfo().getTotalPackingCharges();
        assertThat(totalGoodsAmount + totalpackingCharges - subDiscount).isEqualTo(10400);//10000+1000-600
    }

    private Stream<Arguments> shoppingCartDataProvider() {
        ShoppingCarRequestDTO shoppingCartRequestDTO = new ShoppingCarRequestDTO();
        List<ShoppingCartDO.Item> items = new ArrayList<>();
        ShoppingCartDO.Item item = new ShoppingCartDO.Item();
        item.setProductId(81669212L);
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
