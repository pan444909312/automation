package com.miller.userapp.module.order.shopping.settlement;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.app.server.entity.product.ProductEntity;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.mapper.shop.ProductMapper;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@Scenario(scenarioID = "01J9QNPACXKVRS7D0NGX917QYW", scenarioName = "结算-商品限购", developmentTime = 1, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("结算-商品限购")
public class SettlementWithProduct {
    private Integer buyLimitMin = 2;
    private static Long buyLimitMinProductTest = 81669212L;

    @BeforeEach
    void beforeEach() {
        // 初始化，链接数据库
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        var lambdaUpdateWrapper = new LambdaUpdateWrapper<ProductEntity>();
        lambdaUpdateWrapper
                .eq(ProductEntity::getProductId, buyLimitMinProductTest)
                .set(ProductEntity::getBuyLimitMin, buyLimitMin);
        productMapper.update(new ProductEntity(), lambdaUpdateWrapper);
    }

    @AfterEach
    void afterEach() {
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        var lambdaUpdateWrapper = new LambdaUpdateWrapper<ProductEntity>();
        lambdaUpdateWrapper
                .eq(ProductEntity::getProductId, buyLimitMinProductTest)
                .set(ProductEntity::getBuyLimitMin, 1);
        productMapper.update(new ProductEntity(), lambdaUpdateWrapper);
    }
    @ParameterizedTest
    @MethodSource("buyLimitMinProduct")
    @DisplayName("结算-加购商品数量<商品起购数量")
    void shouldSettlementWithCouponSuccessfully(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.BUY_LIMIT_MIN_FAIL);
        assertThat(settlementResponseDTO.getError()).contains("最小起购量为"+buyLimitMin+"，已为您自动更新");
    }

    static Stream<Arguments> buyLimitMinProduct() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);
        settlementRequestDTO.setVoucherSn(TestCaseDataForUserConstant.voucherSn);
        settlementRequestDTO.setUseVoucherTemplate(1);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(buyLimitMinProductTest);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
}
