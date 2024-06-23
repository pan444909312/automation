package com.miller.userapp.module.order.create;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.common.enums.common.PlatformEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.create.flow.CreateOrderFlow;
import com.miller.userapp.module.order.create.request.CreateOrderRequestDTO;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_创建订单-美食城订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/15 15:27:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-创建订单-美食城订单")
public class CreateOrderByFoodCityTests {

    @MethodSource("createOrderByFoodCity")
    @ParameterizedTest
    @DisplayName("正常流程_创建订单-美食城订单")
    void shouldCreateOrderSuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);
        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
    }

    /**
     * 创建订单数据提供者_美食城多档口订单
     */
    static Stream<Arguments> createOrderByFoodCity() {
        CreateOrderRequestDTO createOrderByFoodCity = new CreateOrderRequestDTO();
        createOrderByFoodCity.setRemark("【自动化测试】创建美食城订单");
        createOrderByFoodCity.setDeliveryTime("尽快送达");
        createOrderByFoodCity.setTablewareCount(1);
        createOrderByFoodCity.setUserPhone("86 18711110002");
        createOrderByFoodCity.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        // 0=商家配送；1=平台配送；2=自取。美食城仅支持平台配送
        createOrderByFoodCity.setDeliveryType(String.valueOf(DeliveryTypeEnum.third_party.getCode()));
        // 商品价格。无需动态查询，初始化数据时就应当指定好的值。每件商品价格100元，共2件商品，覆盖2个档口。
        createOrderByFoodCity.setFixedPrice(23000);
        createOrderByFoodCity.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));
        createOrderByFoodCity.setAddressId(TestCaseDataForUserConstant.addressId);
        createOrderByFoodCity.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByFoodCity.setShopId(TestCaseDataForMerchantConstant.shopIdOfFoodCity);
        createOrderByFoodCity.setIsOnlinePay(Boolean.TRUE);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByFoodCity.setNeedNumberMasking(Boolean.TRUE);


        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByFoodCity.setProductCartList("[{"productId":81742258,"skuId":0,"tagId":[]},{"productId":81744208,"skuId":0,"tagId":[]}]");
        List<ProductCart> productCarts = new ArrayList<>();
        // 商品1
        ProductCart productCart1 = new ProductCart();
        productCart1.setProductId(TestCaseDataForMerchantConstant.productIdOfFoodCity1);
        productCart1.setSkuId(TestCaseDataForMerchantConstant.skuIdOfFoodCity1);
        // 商品2
        ProductCart productCart2 = new ProductCart();
        productCart2.setProductId(TestCaseDataForMerchantConstant.productIdOfFoodCity2);
        productCart2.setSkuId(TestCaseDataForMerchantConstant.skuIdOfFoodCity2);
        // 添加商品1、商品2
        productCarts.add(productCart1);
        productCarts.add(productCart2);
        createOrderByFoodCity.setProductCartList(JSON.toJSONString(productCarts));

        return Stream.of(Arguments.of(createOrderByFoodCity));
    }
}
