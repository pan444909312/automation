package com.miller.userapp.module.order.create;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.common.enums.common.PlatformEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.create.request.CreateOrderRequestDTO;
import com.miller.userapp.module.order.create.flow.CreateOrderFlow;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_创建订单-用户自取
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 15:47:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-创建订单-用户自取")
public class CreateOrderByMyselfDeliveryTests {
    // 商品价格，使用结算页的计算金额,动态获取。这个用例用于验证订单主流程，所以金额计算的内部计算因子逻辑，暂不放在这里处理
    private static Integer orderPrice;

    @BeforeAll
    static void beforeAll() {
        // 查询订单金额
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);

        orderPrice = SettlementFlow.queryOrderPriceFormSettlementPage(
                OrderReqTypeEnum.COMMON_ORDER.getType(),
                DeliveryTypeEnum.user.getCode(),
                TestCaseDataForMerchantConstant.shopId,
                List.of(productCart));
    }

    @MethodSource("createOrderByMyselfDelivery")
    @ParameterizedTest
    @DisplayName("正常流程_创建订单-用户自取")
    void shouldCreateOrderSuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);

        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
    }

    /**
     * 创建订单数据提供者_用户自取
     */
    Stream<Arguments> createOrderByMyselfDelivery() {
        CreateOrderRequestDTO createOrderByMyselfDelivery = new CreateOrderRequestDTO();
        createOrderByMyselfDelivery.setDeliveryTime("尽快取餐");
        // 0=商家配送；1=平台配送；2=自取
        createOrderByMyselfDelivery.setDeliveryType(String.valueOf(DeliveryTypeEnum.user.getCode()));
        // 商品价格。用户自取的价格是不一样的。无需动态查询，初始化数据时就应当指定好的值
        createOrderByMyselfDelivery.setFixedPrice(orderPrice);    // 价格为什么跟之前不一样了？这里要改为动态获取了
        // 选择自取/商家配送时需要传联系电话
        createOrderByMyselfDelivery.setUserPhone("86 18711110002");
        createOrderByMyselfDelivery.setTablewareCount(1);
        createOrderByMyselfDelivery.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByMyselfDelivery.setRemark("【自动化测试】创建订单,用户自取");
        createOrderByMyselfDelivery.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        //createOrderByMyselfDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByMyselfDelivery.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByMyselfDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByMyselfDelivery.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByMyselfDelivery.setVerify(String.valueOf(StatusEnum.NO.getType()));
        createOrderByMyselfDelivery.setNeedNumberMasking(Boolean.FALSE);
        createOrderByMyselfDelivery.setIsOnlinePay(Boolean.TRUE);
        return Stream.of(Arguments.of(createOrderByMyselfDelivery));
    }

}
