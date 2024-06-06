package com.miller.userapp.order.shopping.car;

import com.hungrypanda.app.server.dto.order.cart.ShoppingCartDO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.db.DBUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.shopping.car.flow.ShoppingCarFlow;
import com.miller.userapp.order.shopping.car.request.ShoppingCarRequestDTO;
import com.miller.userapp.order.shopping.car.response.ShoppingCarResponseDTO;
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

    private static final String mySqlUrl = "jdbc:mysql://rm-3ns24734o9z8747d0jo.mysql.rds.aliyuncs.com:3306/panda_test";
    private static final String userName = "panda_test";
    private static final String passWord = "Pan$te19*";
    private static DBUtils dbUtils;

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach invoked...");
        // 初始化，链接数据库
        dbUtils = new DBUtils(mySqlUrl, userName, passWord);
        String sql = "update user_cdkey set is_used=1 WHERE user_id=? and end_time > 1710223505718";
        dbUtils.executeInsertOrUpdateOrDelete(sql,1398708422);
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach invoked...");
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
        Integer totalGoodsAmount =shoppingCarResponseDTO.getResult().getCart().getCountInfo().getTotalGoodsAmount();
        Integer subDiscount	 =shoppingCarResponseDTO.getResult().getCart().getCountInfo().getSubDiscount();
        Integer totalpackingCharges =shoppingCarResponseDTO.getResult().getCart().getCountInfo().getTotalPackingCharges();
        assertThat(totalGoodsAmount+totalpackingCharges-subDiscount).isEqualTo(10400);//10000+1000-600
    }

    private  Stream<Arguments> shoppingCartDataProvider() {
        ShoppingCarRequestDTO shoppingCartRequestDTO = new ShoppingCarRequestDTO();
        List<ShoppingCartDO.Item> items = new ArrayList<>();
        ShoppingCartDO.Item item= new ShoppingCartDO.Item();
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
