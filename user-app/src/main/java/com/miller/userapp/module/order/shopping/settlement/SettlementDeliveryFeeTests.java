package com.miller.userapp.module.order.shopping.settlement;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.mapper.virtual.DeliveryDiscountShopEntity;
import com.miller.userapp.mapper.virtual.DeliveryDiscountShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.panda.promotion.server.common.enums.StatusEnum;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Scenario(scenarioID = "01JCMXFHFZ6VEKMGDGSJZ6W2T9",
       scenarioName = "结算-店铺基础运费和加价",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("结算-店铺基础运费和加价")
public class SettlementDeliveryFeeTests {
    DeliveryDiscountShopEntity deliveryDiscountShopEntity;
    static DeliveryDiscountShopMapper deliveryDiscountShopMapper;
    private static SqlSession sqlSession;

    @BeforeAll
    static void beforeAll() {
        sqlSession = DBUtils.getDBOfPandaTest();
        deliveryDiscountShopMapper = sqlSession.getMapper(DeliveryDiscountShopMapper.class);
        UserLoginFlow.loginByDefaultUser();
        UpdateWrapper<DeliveryDiscountShopEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<DeliveryDiscountShopEntity> lamda = updateWrapper.lambda();
        lamda.eq(DeliveryDiscountShopEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lamda.set(DeliveryDiscountShopEntity::getIsDel, StatusEnum.YES.getCode());
        deliveryDiscountShopMapper.update(updateWrapper);

    }
    @AfterAll
    static void AfterAll(){
        sqlSession.close();
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.SettlementDataProvider#shopSupportPandaDelAndPinckUp")
    @Order(1)
    @DisplayName("结算-基础配送费")
    void settlementBaseDeliveryFee(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("delivery")).findFirst().get().getItemAmount()).isGreaterThan(0);
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.SettlementDataProvider#shopSupportPandaDelAndPinckUp")
    @Order(2)
    @DisplayName("结算-加价-新增收费项加价")
    void settlementAddDeliveryFeeNew(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().contains("DELIVERY_ADD_FEE")).findFirst().get().getItemAmount()).isGreaterThan(0);

    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.SettlementDataProvider#shopSupportPandaDelAndPinckUp")
    @Order(3)
    @DisplayName("结算-加价-配送费原价加价")
    void settlementAddDeliveryFeeOrg(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("delivery")).findFirst().get().getItemAmount()).isGreaterThan(0);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("delivery")).findFirst().get().getItemName()).contains("原价加价");

    }
}
