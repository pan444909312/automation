package com.miller.userapp.module.order.create;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.member.MemberCombinedTypeEnum;
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
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import com.panda.common.enums.VoucherStatusEnum;
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
 * 测试用例_创建订单-平台配送-会员合单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/22 18:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-创建订单-平台配送-会员合单")
public class CreateOrderByPlatformDeliveryWithMemberTests {
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
                DeliveryTypeEnum.third_party.getCode(),
                TestCaseDataForMerchantConstant.shopId,
                List.of(productCart));
    }
    @MethodSource("createOrderByPlatformDeliveryWithMember")
    @ParameterizedTest
    @DisplayName("正常流程_创建订单-平台配送-会员合单")
    void shouldCreateOrderSuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);
        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
        // 会员合单计算逻辑在结算测试用例中进行校验
    }

    /**
     * 创建订单数据提供者_平台配送-会员合单。用户在下单时购买会员.
     */
    static Stream<Arguments> createOrderByPlatformDeliveryWithMember() {
        CreateOrderRequestDTO createOrderByPlatformDeliveryWithMember = new CreateOrderRequestDTO();
        createOrderByPlatformDeliveryWithMember.setAddressId(TestCaseDataForUserConstant.addressId);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByPlatformDeliveryWithMember.setDeliveryType(String.valueOf(DeliveryTypeEnum.third_party.getCode()));
        createOrderByPlatformDeliveryWithMember.setDeliveryTime("尽快送达");
        createOrderByPlatformDeliveryWithMember.setIsOnlinePay(Boolean.TRUE);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByPlatformDeliveryWithMember.setNeedNumberMasking(Boolean.TRUE);
        createOrderByPlatformDeliveryWithMember.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByPlatformDeliveryWithMember.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));
        createOrderByPlatformDeliveryWithMember.setUseVoucherTemplate(VoucherStatusEnum.WAIT_USE.getCode());
        createOrderByPlatformDeliveryWithMember.setRemark("【自动化测试】创建订单");
        // 选择自取时需要传联系电话。但是我发现配送传这个字段也没关系
        createOrderByPlatformDeliveryWithMember.setUserPhone("86 18711110002");
        createOrderByPlatformDeliveryWithMember.setTablewareCount(1);
        createOrderByPlatformDeliveryWithMember.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());


        createOrderByPlatformDeliveryWithMember.setFixedPrice(orderPrice);
        // 会员合单相关参数
        createOrderByPlatformDeliveryWithMember.setMemberCityId(TestCaseDataForUserConstant.memberCityId);
        createOrderByPlatformDeliveryWithMember.setMemberBuyType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByPlatformDeliveryWithMember.setMemberCombinedType(MemberCombinedTypeEnum.MEMBER_NO.getOpenBizType());

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByPlatformDeliveryWithMember.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByPlatformDeliveryWithMember.setShopId(TestCaseDataForMerchantConstant.shopId);
        return Stream.of(Arguments.of(createOrderByPlatformDeliveryWithMember));
    }
}
