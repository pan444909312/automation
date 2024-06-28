package com.miller.userapp.module.order.shopping.preorder;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.PreorderDeliveryTimeVO;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.app.server.entity.shop.ShopExtraInfoEntity;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.PreorderParamsEntity;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.mapper.shop.ShopExtraInfoMapper;
import com.panda.common.enums.DeliveryTypeEnum;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.BeanUtils;
import com.miller.userapp.module.person.member.PandaDB;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 这个是虚单预约单测试，请求使用虚单接口，只是单独建了个包名
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-结算-预约单数据校验")
public class SettlementWithPreorderTest {
    PreorderParamsEntity preorderParamsEntity;
    ShopExtraInfoEntity shopExtraInfoEntity;
    ShopExtraInfoMapper shopExtraInfoMapper;
    PreorderServiceImpl preorderService;
//    private static final String mySqlUrl = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.url");
//    private static final String userName = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.username");
//    private static final String passWord = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.password");
    private static SqlSession sqlSession;

    @BeforeAll
    void beforeAll(){
//        MyBatisPlusConfig myBatisPlusConfig = new MyBatisPlusConfig();
//        sqlSession = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource());
        sqlSession = PandaDB.getSqlSession();
        shopExtraInfoMapper = sqlSession.getMapper(ShopExtraInfoMapper.class);
        QueryWrapper<ShopExtraInfoEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ShopExtraInfoEntity> lambda = queryWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        shopExtraInfoEntity = shopExtraInfoMapper.selectOne(queryWrapper);
        preorderParamsEntity = new PreorderParamsEntity();
        preorderService = new PreorderServiceImpl(sqlSession);
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
    @MethodSource("settlementPreorder")
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
    @MethodSource("settlementPreorder")
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
    @MethodSource("settlementPreorder")
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
    @ParameterizedTest
    @MethodSource("settlementPreorder")
    @DisplayName("用户-结算-预约单-获取第一个时间")
    void settlementWithFirstTime(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderOpenType,0);
        shopExtraInfoMapper.update(null,updateWrapper);
        //获取makeTime
        int makeTime = preorderService.getMakeTimeByShop();
//        System.out.println("makeTime: "+makeTime);
        LocalTime localTime = LocalTime.now();
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        List<PreorderDeliveryTimeVO> preorderDeliveryTimeVOList = settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryTime();
        assertThat(preorderDeliveryTimeVOList.size()).isGreaterThan(0);
        PreorderDeliveryTimeVO preorderDeliveryTimeVO = preorderDeliveryTimeVOList.get(0);
        assertThat(preorderDeliveryTimeVO.getTimeList().size()).isGreaterThan(0);
        String firstTime = preorderDeliveryTimeVO.getTimeList().get(0).getStartTime();
//        System.out.println("firstTime: "+firstTime);
//        System.out.println("actualTime: "+localTime.plusMinutes(makeTime).format(DateTimeFormatter.ofPattern("HH:mm")));
        assertThat(firstTime).isGreaterThan( localTime.plusMinutes(makeTime).format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    static Stream<Arguments> settlementPreorder() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
//        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        //createOrderByMyselfDelivery.setProductCartList("[{\"productId\":81669204,\"skuId\":0,\"tagId\":[]}]");
        List<ProductCart> productCartList = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
}
