package com.miller.userapp.module.order.create;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.common.enums.common.PlatformEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.create.request.CreateOrderRequestDTO;
import com.miller.userapp.module.order.create.flow.CreateOrderFlow;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("用户-创建订单-平台配送-优速达合单")
public class CreateOrderByPlatformDeliveryFastDeliveryTests {

    @MethodSource("createOrderByPlatformDeliveryFastDelivery")
    @ParameterizedTest
    @DisplayName("正常流程_创建订单-平台配送-优速达合单")
    void shouldCreateOrderWithFastDeliverySuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);
        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
        // 代金券合单计算逻辑在结算测试用例中进行校验
    }


    /**
     * 优速达合单下单
     */
    static Stream<Arguments> createOrderByPlatformDeliveryFastDelivery() {
        CreateOrderRequestDTO createOrderByPlatformDeliveryWithFastDelivery = new CreateOrderRequestDTO();
        SettlementRequestDTO settlementRequestDTO=new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);
        settlementRequestDTO.setChoseFastDelivery(1);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));
        SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
        // 优速达相关参数
        Integer fastDeliveryAmount = settlementResponseDTO.getResult().getOrderOpt().getOrderPaymentCombined().getAdditionalBusinessOrderVO().getFastDeliveryAdditionalVO().getFastDeliveryAmount();
        createOrderByPlatformDeliveryWithFastDelivery.setFastMinute(settlementResponseDTO.getResult().getOrderOpt().getOrderPaymentCombined().getAdditionalBusinessOrderVO().getFastDeliveryAdditionalVO().getFastMinute());
        createOrderByPlatformDeliveryWithFastDelivery.setFastDeliveryAmount(fastDeliveryAmount);
        createOrderByPlatformDeliveryWithFastDelivery.setChoseFastDelivery(1);

        createOrderByPlatformDeliveryWithFastDelivery.setAddressId(TestCaseDataForUserConstant.addressId);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByPlatformDeliveryWithFastDelivery.setDeliveryType(String.valueOf(DeliveryTypeEnum.third_party.getCode()));
        createOrderByPlatformDeliveryWithFastDelivery.setDeliveryTime("尽快送达");
        createOrderByPlatformDeliveryWithFastDelivery.setIsOnlinePay(Boolean.TRUE);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByPlatformDeliveryWithFastDelivery.setNeedNumberMasking(Boolean.TRUE);
        createOrderByPlatformDeliveryWithFastDelivery.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByPlatformDeliveryWithFastDelivery.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));
        createOrderByPlatformDeliveryWithFastDelivery.setRemark("【自动化测试】创建订单");
        // 选择自取时需要传联系电话。但是我发现配送传这个字段也没关系
        createOrderByPlatformDeliveryWithFastDelivery.setUserPhone("86 18711110002");
        createOrderByPlatformDeliveryWithFastDelivery.setTablewareCount(1);
        createOrderByPlatformDeliveryWithFastDelivery.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByPlatformDeliveryWithFastDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByPlatformDeliveryWithFastDelivery.setFixedPrice(settlementResponseDTO.getResult().getPriceInfo().getTotalAmount());

        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCartList.add(productCart);
        createOrderByPlatformDeliveryWithFastDelivery.setProductCartList(JSON.toJSONString(productCartList));
        createOrderByPlatformDeliveryWithFastDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);
        return Stream.of(Arguments.of(createOrderByPlatformDeliveryWithFastDelivery));
    }
}
