package com.miller.userapp.module.order.shopping.settlement.feeItems;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Scenario(scenarioID = "01JCMXFHFZ6VEKMGDGSJZ6W2TA",
        scenarioName = "结算-店铺餐具费",
        developmentTime = 40, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("结算-店铺餐具费")
public class SettlementTablewareFeeTests {
    static ShopMapper shopMapper;
    private static SqlSession sqlSession;

    @BeforeAll
    static void beforeAll() {
        sqlSession = DBUtils.getDBOfPandaTest();
        shopMapper = sqlSession.getMapper(ShopMapper.class);
        UserLoginFlow.loginByDefaultUser();
        UpdateWrapper<ShopEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopEntity::getTablewarePrice,TestCaseDataForMerchantConstant.tablewarePrice );
        lambda.set(ShopEntity::getDefaultTablewareQuantity, TestCaseDataForMerchantConstant.defaultTablewareQuantity);
        shopMapper.update(updateWrapper);
    }
    @AfterAll
    static void AfterAll(){
        sqlSession.close();
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopDefaultTableware")
    @Order(1)
    @DisplayName("结算-店铺默认餐具费")
    void settlementDefaultTableware(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("tableware")).findFirst().get().getItemAmount()).isEqualTo(TestCaseDataForMerchantConstant.tablewarePrice*TestCaseDataForMerchantConstant.defaultTablewareQuantity);
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopSupportPandaDelAndPinckUp")
    @Order(2)
    @DisplayName("结算-店铺指定餐具数")
    void settlementAppointTableware(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("tableware")).findFirst().get().getItemAmount()).isEqualTo(TestCaseDataForMerchantConstant.tablewarePrice*1);
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopNoTableware")
    @Order(3)
    @DisplayName("结算-店铺不需要餐具数")
    void settlementNoTableware(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().noneMatch((value -> value.getItemKey().equalsIgnoreCase("tableware"))));
    }

}
