package com.miller.userapp.module.order.create;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
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
import com.panda.common.enums.VoucherStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_创建订单-平台配送，也叫Panda配送
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/9 18:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-创建订单-平台配送")
public class CreateOrderByPlatformDeliveryTests {

    @MethodSource("createOrderByPlatformDelivery")
    @ParameterizedTest
    @DisplayName("正常流程_创建订单-平台配送")
    void shouldCreateOrderSuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);
        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
    }
    /**
     * 创建订单数据提供者_平台配送
     */
    static Stream<Arguments> createOrderByPlatformDelivery() {
        // 选择配送方式数据
        CreateOrderRequestDTO createOrderByPlatformDelivery = new CreateOrderRequestDTO();
        createOrderByPlatformDelivery.setAddressId(TestCaseDataForUserConstant.addressId);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByPlatformDelivery.setDeliveryType(String.valueOf(DeliveryTypeEnum.third_party.getCode()));
        createOrderByPlatformDelivery.setDeliveryTime("尽快送达");
        // 商品价格。无需动态查询，初始化数据时就应当指定好的值
        createOrderByPlatformDelivery.setFixedPrice(12000);
        createOrderByPlatformDelivery.setIsOnlinePay(Boolean.TRUE);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByPlatformDelivery.setNeedNumberMasking(Boolean.TRUE);
        createOrderByPlatformDelivery.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByPlatformDelivery.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByPlatformDelivery.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByPlatformDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByPlatformDelivery.setUseVoucherTemplate(VoucherStatusEnum.WAIT_USE.getCode());
        createOrderByPlatformDelivery.setRemark("【自动化测试】创建订单");
        // 选择自取时需要传联系电话。但是我发现配送传这个字段也没关系
        createOrderByPlatformDelivery.setUserPhone("86 18711110002");

        return Stream.of(Arguments.of(createOrderByPlatformDelivery));
    }
}
