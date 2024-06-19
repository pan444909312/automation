package com.miller.userapp.order.shopping.settlement;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.shop.ShopExtraInfoEntity;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.mapper.shop.ShopExtraInfoMapper;
import com.miller.userapp.order.shopping.settlement.request.PreorderParamsEntity;
import com.miller.userapp.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.order.shopping.settlement.response.SettlementResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.BeanUtils;
import com.miller.userapp.member.PandaDB;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("用户-结算-预约单数据校验")
public class SettlementWithPreorderTest {
    PreorderParamsEntity preorderParamsEntity;
    ShopExtraInfoEntity shopExtraInfoEntity;
    ShopExtraInfoMapper shopExtraInfoMapper;
//    private static final String mySqlUrl = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.url");
//    private static final String userName = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.username");
//    private static final String passWord = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.password");
    private static SqlSession sqlSession;

    @BeforeAll
    void beforeAll(){
//        MyBatisPlusConfig myBatisPlusConfig = new MyBatisPlusConfig();
//        sqlSession = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource());
        shopExtraInfoMapper = PandaDB.getSqlSession().getMapper(ShopExtraInfoMapper.class);
        QueryWrapper<ShopExtraInfoEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ShopExtraInfoEntity> lambda = queryWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        shopExtraInfoEntity = shopExtraInfoMapper.selectOne(queryWrapper);
        preorderParamsEntity = new PreorderParamsEntity();
//        String sql = "select * from shop_extra_info where shop_id = ?";
//        shopExtraInfoEntity = PandaDB.getDBInstance().queryOneObjectReturnObject(sql,
//                ShopExtraInfoEntity.class, TestCaseDataForMerchantConstant.shopId);
//        System.out.println("=============select============== " +JSON.toJSON(shopExtraInfoEntity));
        BeanUtils.copyProperties(shopExtraInfoEntity,preorderParamsEntity);
//        System.out.println("=========================== " +JSON.toJSON(preorderParamsEntity));

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
//        String sql = "update shop_extra_info set preorder_open_type = ?, preorder_closed_support = ?, preorder_time_mode = ?, preorder_days = ?, preorder_cut_off = ?, preorder_interval = ?, preorder_push_shop = ? ";
//        PandaDB.getDBInstance().executeInsertOrUpdateOrDelete(sql,preorderParamsEntity.getPreorderOpenType(),preorderParamsEntity.getPreorderClosedSupport()
//        ,preorderParamsEntity.getPreorderTimeMode(),preorderParamsEntity.getPreorderDays(),preorderParamsEntity.getPreorderCutOff(),
//                preorderParamsEntity.getPreorderInterval(),preorderParamsEntity.getPreorderPushShop());
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderOpenType,preorderParamsEntity.getPreorderOpenType());
        lambda.set(ShopExtraInfoEntity::getPreorderClosedSupport,preorderParamsEntity.getPreorderClosedSupport());
        lambda.set(ShopExtraInfoEntity::getPreorderTimeMode,preorderParamsEntity.getPreorderTimeMode());
        lambda.set(ShopExtraInfoEntity::getPreorderDays,preorderParamsEntity.getPreorderDays());
        lambda.set(ShopExtraInfoEntity::getPreorderCutOff,preorderParamsEntity.getPreorderCutOff());
        lambda.set(ShopExtraInfoEntity::getPreorderInterval,preorderParamsEntity.getPreorderInterval());
        lambda.set(ShopExtraInfoEntity::getPreorderPushShop,preorderParamsEntity.getPreorderPushShop());
        shopExtraInfoMapper.update(null,updateWrapper);
//        shopExtraInfoMapper.update(shopExtraInfoEntity,updateWrapper);
        PandaDB.getSqlSession().close();

    }
    @ParameterizedTest
    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#settlementPreorder")
    @DisplayName("用户-结算-预约单-立即送达+仅预约")
    void settlementWithPreorderOpenType1(SettlementRequestDTO settlementRequestDTO){
//        String sql = "update shop_extra_info set preorder_open_type = ?";
//        PandaDB.getDBInstance().executeInsertOrUpdateOrDelete(sql,1);
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderOpenType,1);
        shopExtraInfoMapper.update(null,updateWrapper);
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        if(settlementResponseDTO.getResult().getOrderOther().getOrderType() == 0 ){
            assertThat(settlementResponseDTO.getResult().getOrderOther().getPreOrderOpenType()).isEqualTo(1);
        }
    }
    @ParameterizedTest
    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#settlementPreorder")
    @DisplayName("用户-结算-预约单-仅预约")
    void settlementWithPreorderOpenType2(SettlementRequestDTO settlementRequestDTO){
//        String sql = "update shop_extra_info set preorder_open_type = ?";
//        PandaDB.getDBInstance().executeInsertOrUpdateOrDelete(sql,2);
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderOpenType,2);
        shopExtraInfoMapper.update(null,updateWrapper);
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getOrderOther().getPreOrderOpenType()).isEqualTo(2);
    }
    @ParameterizedTest
    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#settlementPreorder")
    @DisplayName("用户-结算-预约单-仅立即送达")
    void settlementWithPreorderOpenType3(SettlementRequestDTO settlementRequestDTO){
//        String sql = "update shop_extra_info set preorder_open_type = ?";
//        PandaDB.getDBInstance().executeInsertOrUpdateOrDelete(sql,3);
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderOpenType,3);
        shopExtraInfoMapper.update(null,updateWrapper);
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        if(settlementResponseDTO.getResult().getOrderOther().getOrderType() == 0 ){
            assertThat(settlementResponseDTO.getResult().getOrderOther().getPreOrderOpenType()).isEqualTo(3);
        }
    }
}
