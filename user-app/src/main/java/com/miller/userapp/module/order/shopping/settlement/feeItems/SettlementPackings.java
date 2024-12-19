package com.miller.userapp.module.order.shopping.settlement.feeItems;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.shop.ShopExtraInfoEntity;
import com.hungrypanda.app.server.vo.shop.product.Product;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.mapper.product.ProductMapper;
import com.miller.userapp.mapper.shop.ShopExtraInfoMapper;
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

@Scenario(scenarioID = "01JCMXFHFZ6VEKMGDGSJZ6W2TB",
        scenarioName = "结算-打包费",
        developmentTime = 150, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("结算-打包费")
public class SettlementPackings {
    static ShopExtraInfoMapper shopExtraInfoMapper;
    static ProductMapper productMapper;
    private static SqlSession sqlSession;

    @BeforeAll
    static void beforeAll() {
        sqlSession = DBUtils.getDBOfPandaTest();
        shopExtraInfoMapper = sqlSession.getMapper(ShopExtraInfoMapper.class);
        productMapper = sqlSession.getMapper(ProductMapper.class);
        UserLoginFlow.loginByDefaultUser();

    }

    @AfterAll
    static void AfterAll() {
        sqlSession.close();
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#sameSkuAndTag")
    @Order(1)
    @DisplayName("结算-只有商品打包费，没有商家塑料打包费-同规格-同配料")
    void settlementOnlyProductPacking(SettlementRequestDTO settlementRequestDTO) {
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopExtraInfoEntity::getPlasticAmount, 0);
        lambda.set(ShopExtraInfoEntity::getUsePlastic, 1);
        shopExtraInfoMapper.update(new ShopExtraInfoEntity(), updateWrapper);

        UpdateWrapper<Product> updateProduct = new UpdateWrapper<>();
        LambdaUpdateWrapper<Product> lambdaProduct = updateProduct.lambda();
        lambdaProduct.eq(Product::getProductId, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductIdForPacking);
        lambdaProduct.set(Product::getPackingCharges, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductPacking);
        productMapper.update(null, updateProduct);

        SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("packaging")).findFirst().get().getItemAmount()).isEqualTo(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductPacking);
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#sameSkuDifferentdTag")
    @Order(2)
    @DisplayName("结算-只有商品打包费，没有商家塑料打包费-同规格-不同配料")
    void settlementOnlyProductPacking2(SettlementRequestDTO settlementRequestDTO) {
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopExtraInfoEntity::getPlasticAmount, 0);
        lambda.set(ShopExtraInfoEntity::getUsePlastic, 1);
        shopExtraInfoMapper.update(new ShopExtraInfoEntity(), updateWrapper);

        UpdateWrapper<Product> updateProduct = new UpdateWrapper<>();
        LambdaUpdateWrapper<Product> lambdaProduct = updateProduct.lambda();
        lambdaProduct.eq(Product::getProductId, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductIdForPacking);
        lambdaProduct.set(Product::getPackingCharges, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductPacking);
        productMapper.update(null, updateProduct);

        SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("packaging")).findFirst().get().getItemAmount()).isEqualTo(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductPacking * 2);
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#sameSkuAndTag")
    @Order(3)
    @DisplayName("结算-有商品打包费，有商家塑料打包费-同规格-同配料")
    void settlementProductPackingAndPlastic(SettlementRequestDTO settlementRequestDTO) {
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopExtraInfoEntity::getPlasticAmount, TestCaseDataForMerchantConstant.shopTestDeliveryWayPlasticAmount);
        lambda.set(ShopExtraInfoEntity::getUsePlastic, 1);
        shopExtraInfoMapper.update(new ShopExtraInfoEntity(), updateWrapper);

        UpdateWrapper<Product> updateProduct = new UpdateWrapper<>();
        LambdaUpdateWrapper<Product> lambdaProduct = updateProduct.lambda();
        lambdaProduct.eq(Product::getProductId, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductIdForPacking);
        lambdaProduct.set(Product::getPackingCharges, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductPacking);
        productMapper.update(null, updateProduct);

        SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("packaging")).findFirst().get().getItemAmount()).isEqualTo(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductPacking + TestCaseDataForMerchantConstant.shopTestDeliveryWayPlasticAmount);
    }

    @ParameterizedTest
    @MethodSource("com.miller.userapp.module.order.shopping.settlement.feeItems.SettlementFeeDataProvider#sameSkuAndTag")
    @Order(3)
    @DisplayName("结算-没有商品打包费，有商家塑料打包费-同规格-同配料")
    void settlementOnlyPlastic(SettlementRequestDTO settlementRequestDTO) {
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId, TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        lambda.set(ShopExtraInfoEntity::getPlasticAmount, TestCaseDataForMerchantConstant.shopTestDeliveryWayPlasticAmount);
        lambda.set(ShopExtraInfoEntity::getUsePlastic, 1);
        shopExtraInfoMapper.update(new ShopExtraInfoEntity(), updateWrapper);

        UpdateWrapper<Product> updateProduct = new UpdateWrapper<>();
        LambdaUpdateWrapper<Product> lambdaProduct = updateProduct.lambda();
        lambdaProduct.eq(Product::getProductId, TestCaseDataForMerchantConstant.shopTestDeliveryWayProductIdForPacking);
        lambdaProduct.set(Product::getPackingCharges, 0);
        productMapper.update(null, updateProduct);

        SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("packaging")).findFirst().get().getItemAmount()).isEqualTo(TestCaseDataForMerchantConstant.shopTestDeliveryWayPlasticAmount);
    }
}
