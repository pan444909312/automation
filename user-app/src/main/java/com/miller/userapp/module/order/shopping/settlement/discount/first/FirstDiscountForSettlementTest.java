package com.miller.userapp.module.order.shopping.settlement.discount.first;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.OrderAmountVO;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.miller.common.util.MD5Util;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.home.login.response.UserLoginResponseDTO;
import com.miller.userapp.module.order.shopping.settlement.discount.ShopsEnum;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.util.RequestUtils;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JFA2RKBP5TB9D6JE8GMXQ5FS",
        scenarioName = "正常流程_结算_优惠项-首单/门店新客",
        author = "luwei@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
public class FirstDiscountForSettlementTest {

    @ParameterizedTest
    @MethodSource("settlementWithFirstDiscount")
    @DisplayName("结算-优惠-门店新客-新客减2")
    void settlementWithShopFirstDiscount(SettlementRequestDTO settlementRequestDTO){
        UserLoginFlow.loginByDefaultUser();
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO OrderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("shopFirstDiscount")).findFirst().get();
        assertThat(OrderAmountItemDiscount.getItemAmount()).isEqualTo(200);

    }
    @ParameterizedTest
    @MethodSource("settlementWithFirstDiscount")
    @DisplayName("结算-优惠-平台首单单-减3")
    void settlementWithPlatformFirstDiscount(SettlementRequestDTO settlementRequestDTO){
        // 构造请求数据，从数据库查询结果作为请求数据
        UserLoginRequestDTO user = new UserLoginRequestDTO();
        user.setAreaCode("86");
        user.setAccount("17121193644");
        user.setPassword(MD5Util.string2MD5("12345678"));
        user.setType(2);
        user.setDistinctId("ed99f8b03a64c6c1");

        UserLoginResponseDTO userLoginResponseDTO = UserLoginFlow.loginReturnBodyObject(user);

        // 获取token
        var token = userLoginResponseDTO.getResult().getAccessToken();
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);


        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO OrderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("firstDiscount")).findFirst().get();
        assertThat(OrderAmountItemDiscount.getItemAmount()).isEqualTo(300);

    }


    //门店折扣
    static Stream<Arguments> settlementWithFirstDiscount() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351760L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
}
