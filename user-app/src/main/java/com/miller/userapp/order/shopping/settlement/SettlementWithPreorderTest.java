package com.miller.userapp.order.shopping.settlement;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.entity.shop.ShopExtraInfoEntity;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.member.db.PandaDB;
import com.miller.userapp.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.order.shopping.settlement.request.PreorderParamsEntity;
import com.miller.userapp.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.order.shopping.settlement.response.SettlementResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.BeanUtils;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("用户-结算-预约单数据校验")
public class SettlementWithPreorderTest {
    PreorderParamsEntity preorderParamsEntity;
    ShopExtraInfoEntity shopExtraInfoEntity;
    @BeforeAll
    void beforeAll(){
        preorderParamsEntity = new PreorderParamsEntity();
        String sql = "select * from shop_extra_info where shop_id = ?";
        shopExtraInfoEntity = PandaDB.getDBInstance().queryOneObjectReturnObject(sql,
                ShopExtraInfoEntity.class, TestCaseDataForMerchantConstant.shopId);
        System.out.println("=========================== " +JSON.toJSON(shopExtraInfoEntity));
        BeanUtils.copyProperties(shopExtraInfoEntity,preorderParamsEntity);
        System.out.println("=========================== " +JSON.toJSON(preorderParamsEntity));

    }
    @AfterAll
    void AfterAll(){
//        shopExtraInfoEntity.setPreorderDays(preorderParamsEntity.getPreorderDays());
//        shopExtraInfoEntity.setPreorderOpenType(preorderParamsEntity.getPreorderOpenType());
//        shopExtraInfoEntity.setPreorderClosedSupport(preorderParamsEntity.getPreorderClosedSupport());
//        shopExtraInfoEntity.setPreorderTimeMode(preorderParamsEntity.getPreorderTimeMode());
//        shopExtraInfoEntity.setPreorderCutOff(preorderParamsEntity.getPreorderCutOff());
//        shopExtraInfoEntity.setPreorderInterval(preorderParamsEntity.getPreorderInterval());
//        shopExtraInfoEntity.setPreorderPushShop(preorderParamsEntity.getPreorderPushShop());
        String sql = "update shop_extra_info set preorder_open_type = ?, preorder_closed_support = ?, preorder_time_mode = ?, preorder_days = ?, preorder_cut_off = ?, preorder_interval = ?, preorder_push_shop = ? ";
        PandaDB.getDBInstance().executeInsertOrUpdateOrDelete(sql,preorderParamsEntity.getPreorderOpenType(),preorderParamsEntity.getPreorderClosedSupport()
        ,preorderParamsEntity.getPreorderTimeMode(),preorderParamsEntity.getPreorderDays(),preorderParamsEntity.getPreorderCutOff(),
                preorderParamsEntity.getPreorderInterval(),preorderParamsEntity.getPreorderPushShop());

    }
    @ParameterizedTest
    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#settlementPreorder")
    @DisplayName("用户-结算-预约单-立即送达+仅预约")
    void settlementWithPreorderOpenType1(SettlementRequestDTO settlementRequestDTO){
        String sql = "update shop_extra_info set preorder_open_type = ?";
        PandaDB.getDBInstance().executeInsertOrUpdateOrDelete(sql,1);
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        if(settlementResponseDTO.getResult().getOrderOther().getOrderType() == 0 ){
            assertThat(settlementResponseDTO.getResult().getOrderOther().getPreOrderOpenType()).isEqualTo(1);
        }
    }
//    @ParameterizedTest
//    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#settlementPreorder")
//    @DisplayName("用户-结算-预约单-仅预约")
//    void settlementWithPreorderOpenType2(SettlementRequestDTO settlementRequestDTO){
//        String sql = "update shop_extra_info set preorder_open_type = ?";
//        PandaDB.getDBInstance().executeInsertOrUpdateOrDelete(sql,2);
//        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
//        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
//        assertThat(settlementResponseDTO.getResult().getOrderOther().getPreOrderOpenType()).isEqualTo(2);
//    }
//    @ParameterizedTest
//    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#settlementPreorder")
//    @DisplayName("用户-结算-预约单-仅立即送达")
//    void settlementWithPreorderOpenType3(SettlementRequestDTO settlementRequestDTO){
//        String sql = "update shop_extra_info set preorder_open_type = ?";
//        PandaDB.getDBInstance().executeInsertOrUpdateOrDelete(sql,3);
//        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
//        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
//        if(settlementResponseDTO.getResult().getOrderOther().getOrderType() == 0 ){
//            assertThat(settlementResponseDTO.getResult().getOrderOther().getPreOrderOpenType()).isEqualTo(3);
//        }
//    }
}
