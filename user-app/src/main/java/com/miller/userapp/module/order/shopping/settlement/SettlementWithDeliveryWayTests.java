package com.miller.userapp.module.order.shopping.settlement;

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
import com.panda.pos.server.api.enums.DeliveryTypeEnum;
import com.panda.promotion.server.common.enums.StatusEnum;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Scenario(scenarioID = "01JCMXFHFYF8C7JVWHEGJ124Y6",
       scenarioName = "结算-店铺支持的配送方式结算",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("结算-店铺支持的配送方式结算")
public class SettlementWithDeliveryWayTests {
    static ShopMapper shopMapper;
    private static SqlSession sqlSession;

    @BeforeAll
    static void beforeAll() {
        sqlSession = DBUtils.getDBOfPandaTest();
        shopMapper = sqlSession.getMapper(ShopMapper.class);
        UserLoginFlow.loginByDefaultUser();
    }
    @AfterAll
    static void AfterAll(){
        sqlSession.close();
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopSupportPandaDelAndPinckUp")
    @Order(1)
    @DisplayName("结算-店铺同时支持panda配送和自取")
    public void shopSupportPandaDelAndPinckUp(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopEntity::getDeliveryType,DeliveryTypeEnum.PANDA_DELIVERY.getCode());
        lambda.set(ShopEntity::getIsUserPack, StatusEnum.YES.getCode());
        shopMapper.update(updateWrapper);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryWayList().get(0).getDeliveryId()).isEqualTo(DeliveryTypeEnum.PANDA_DELIVERY.getCode());
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryWayList().get(1).getDeliveryId()).isEqualTo(DeliveryTypeEnum.USER_TAKE.getCode());

    }


    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopSupportPandaDelAndPinckUp")
    @Order(2)
    @DisplayName("结算-店铺同时支持店铺配送和自取")
    public void shopSupportShopDelAndPinckUp(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopEntity::getDeliveryType,DeliveryTypeEnum.SHOP_DELIVERY.getCode());
        lambda.set(ShopEntity::getIsUserPack, StatusEnum.YES.getCode());
        shopMapper.update(updateWrapper);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryWayList().get(0).getDeliveryId()).isEqualTo(DeliveryTypeEnum.SHOP_DELIVERY.getCode());
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryWayList().get(1).getDeliveryId()).isEqualTo(DeliveryTypeEnum.USER_TAKE.getCode());

    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopSupportPandaDelAndPinckUp")
    @Order(3)
    @DisplayName("结算-店铺仅支持panda配送")
    public void shopSupportPandaDel(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopEntity::getDeliveryType,DeliveryTypeEnum.PANDA_DELIVERY.getCode());
        lambda.set(ShopEntity::getIsUserPack, StatusEnum.NO.getCode());
        shopMapper.update(updateWrapper);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryWayList().get(0).getDeliveryId()).isEqualTo(DeliveryTypeEnum.PANDA_DELIVERY.getCode());
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopSupportPandaDelAndPinckUp")
    @Order(4)
    @DisplayName("结算-店铺仅支持自取")
    public void shopSupportPickUp(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopEntity::getDeliveryType,DeliveryTypeEnum.NO_DELIVERY.getCode());
        lambda.set(ShopEntity::getIsUserPack, StatusEnum.YES.getCode());
        shopMapper.update(updateWrapper);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryWayList().get(0).getDeliveryId()).isEqualTo(DeliveryTypeEnum.USER_TAKE.getCode());
    }

}
