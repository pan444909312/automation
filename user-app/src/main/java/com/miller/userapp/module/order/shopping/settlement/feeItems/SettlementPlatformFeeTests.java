package com.miller.userapp.module.order.shopping.settlement.feeItems;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.product.ProductEntity;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.hungrypanda.app.server.entity.shop.ShopExtraInfoEntity;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.dto.XXLConfigEnvEnum;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.util.XXLConfUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.mapper.product.ProductSkuEntity;
import com.miller.userapp.mapper.product.ProductSkuMapper;
import com.miller.userapp.mapper.shop.ProductMapper;
import com.miller.userapp.mapper.shop.ShopExtraInfoMapper;
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

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Scenario(scenarioID = "01JCMXFHFZ6VEKMGDGSJZ6W2TC",
        scenarioName = "结算-平台服务费",
        developmentTime = 200, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("结算-平台服务费")
public class SettlementPlatformFeeTests {

    static ShopMapper shopMapper;
    static ProductMapper productMapper;
    static ProductSkuMapper productSkuMapper;
    static ShopExtraInfoMapper shopExtraInfoMapper;
    private static SqlSession sqlSession;

    @BeforeAll
    static void beforeAll() {
        sqlSession = DBUtils.getDBOfPandaTest();
        shopMapper = sqlSession.getMapper(ShopMapper.class);
        productMapper = sqlSession.getMapper(ProductMapper.class);
        productSkuMapper = sqlSession.getMapper(ProductSkuMapper.class);
        shopExtraInfoMapper = sqlSession.getMapper(ShopExtraInfoMapper.class);
        UserLoginFlow.loginByDefaultUser();

    }
    @AfterAll
    static void AfterAll(){
        sqlSession.close();
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopDefaultTableware")
    @Order(1)
    @DisplayName("结算-配送平台服务费-固定金额")
    void settlementFixednessPlatformFee(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopEntity::getPlatformFeeType,TestCaseDataForMerchantConstant.shopFixednessPlatformType );
        lambda.set(ShopEntity::getPlatformFee, TestCaseDataForMerchantConstant.shopFixednessPlatformAmount);
        shopMapper.update( updateWrapper);

        //商品起购量设为1
        UpdateWrapper<ProductEntity> updateProduct = new UpdateWrapper<>();
        LambdaUpdateWrapper<ProductEntity> lambdaProduct = updateProduct.lambda();
        lambdaProduct.eq(ProductEntity::getProductId, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductId);
        lambdaProduct.set(ProductEntity::getBuyLimitMin,1 );
        productMapper.update(lambdaProduct);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("platformFee")).findFirst().get().getItemAmount()).isEqualTo(TestCaseDataForMerchantConstant.shopFixednessPlatformAmount);
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopDefaultTableware")
    @Order(1)
    @DisplayName("结算-配送平台服务费-加拿大/美国平台服务费-无上限")
    void settlementPercentPlatformFeeCa(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopEntity::getPlatformFeeType,TestCaseDataForMerchantConstant.shopPercentPlatformType );
        lambda.set(ShopEntity::getPlatformFeeRate, TestCaseDataForMerchantConstant.shopPercentPlatformAmount);
        shopMapper.update(new ShopEntity(), updateWrapper);

        UpdateWrapper<ShopExtraInfoEntity> updateShopExtraInfo = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambdaShopExtraInfo = updateShopExtraInfo.lambda();
        lambdaShopExtraInfo.eq(ShopExtraInfoEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambdaShopExtraInfo.set(ShopExtraInfoEntity::getPlatformFeeUpLimit, 0);
        lambdaShopExtraInfo.set(ShopExtraInfoEntity::getPlatformFeeLowLimit,0);
        shopExtraInfoMapper.update(lambdaShopExtraInfo);

        //商品起购量设为1
        UpdateWrapper<ProductEntity> updateProduct = new UpdateWrapper<>();
        LambdaUpdateWrapper<ProductEntity> lambdaProduct = updateProduct.lambda();
        lambdaProduct.eq(ProductEntity::getProductId, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductId);
        lambdaProduct.set(ProductEntity::getBuyLimitMin,1 );
        productMapper.update( lambdaProduct);

        //查询商品sku
        QueryWrapper<ProductSkuEntity> queryProductSku = new QueryWrapper<>();
        LambdaQueryWrapper<ProductSkuEntity> lambdaQueryProduct = queryProductSku.lambda();
        lambdaQueryProduct.eq(ProductSkuEntity::getProductSkuId, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductSkuId);
        ProductSkuEntity sku = productSkuMapper.selectOne(queryProductSku);

        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(),"user-app-server.canada.tax.rule","加拿大税费规则",true);

        Integer skuPlatformPrice = sku.getProductSkuPrice()*TestCaseDataForMerchantConstant.shopPercentPlatformAmount/100;
        int skuPlatformPrice2 = new BigDecimal(skuPlatformPrice).setScale(0, RoundingMode.HALF_UP).intValue();

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("platformFee")).findFirst().get().getItemAmount()).isEqualTo(skuPlatformPrice2);
    }
    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#shopDefaultTableware")
    @Order(3)
    @DisplayName("结算-配送平台服务费-消费税新规则(apply-v280-tax-rule=true)-有上限")
    void settlementPercentPlatformFeeV280(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopEntity::getPlatformFeeType,TestCaseDataForMerchantConstant.shopPercentPlatformType );
        lambda.set(ShopEntity::getPlatformFeeRate, TestCaseDataForMerchantConstant.shopPercentPlatformAmount);
        shopMapper.update(updateWrapper);

        UpdateWrapper<ShopExtraInfoEntity> updateShopExtraInfo = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambdaShopExtraInfo = updateShopExtraInfo.lambda();
        lambdaShopExtraInfo.eq(ShopExtraInfoEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambdaShopExtraInfo.set(ShopExtraInfoEntity::getPlatformFeeUpLimit, TestCaseDataForMerchantConstant.shopPercentPlatformFeeUpLimit);
        lambdaShopExtraInfo.set(ShopExtraInfoEntity::getPlatformFeeLowLimit,0);
        shopExtraInfoMapper.update(lambdaShopExtraInfo);

        //商品起购量设为1
        UpdateWrapper<ProductEntity> updateProduct = new UpdateWrapper<>();
        LambdaUpdateWrapper<ProductEntity> lambdaProduct = updateProduct.lambda();
        lambdaProduct.eq(ProductEntity::getProductId, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductId);
        lambdaProduct.set(ProductEntity::getBuyLimitMin,1 );
        productMapper.update( lambdaProduct);

        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(),"user-app-server.canada.tax.rule","加拿大税费规则",false);
        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(),"user-app-server.america.rule","美国规则",false);

        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(),"user-app-server.apply.version.280.tax.rule","消费税 =（商家端总价-商家承担的满减-商家承担的首单-商家承担的红包) * 税率",true);
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("platformFee")).findFirst().get().getItemAmount()).isEqualTo(TestCaseDataForMerchantConstant.shopPercentPlatformFeeUpLimit);
    }

}
