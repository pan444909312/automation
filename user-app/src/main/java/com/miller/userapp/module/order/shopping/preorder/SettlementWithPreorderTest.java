package com.miller.userapp.module.order.shopping.preorder;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.OrderOptVO;
import com.hungrypanda.app.server.api.res.order.PreorderDeliveryTimeVO;
import com.hungrypanda.app.server.api.res.order.PreorderTimeDTO;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.app.server.dto.delivery.DeliveryTimeDTO;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.hungrypanda.app.server.entity.shop.ShopExtraInfoEntity;
import com.hungrypanda.app.server.entity.shop.ShopTimeConfigEntity;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.constants.FormatterCons;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.PreorderParamsEntity;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.mapper.shop.ShopExtraInfoMapper;
import com.miller.userapp.util.DBUtils;
import com.panda.common.enums.DeliveryTypeEnum;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementWithPreorderTest {
    PreorderParamsEntity preorderParamsEntity;
    ShopExtraInfoEntity shopExtraInfoEntity;
    ShopExtraInfoMapper shopExtraInfoMapper;
    ShopMapper shopMapper;
    PreorderServiceImpl preorderService;
    private static SqlSession sqlSession;

    @BeforeAll
    void beforeAll(){
        sqlSession = DBUtils.getDBOfPandaTest();
        shopMapper = sqlSession.getMapper(ShopMapper.class);
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

    }
    @ParameterizedTest
    @MethodSource("settlementPreorder")
    @Order(1)
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
    @Order(2)
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
    @Order(3)
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
            List<PreorderDeliveryTimeVO> preorderDeliveryTimeVOList = settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryTime();
            assertThat(preorderDeliveryTimeVOList.size()).isEqualTo(0); //是个空数组
        }
    }
    @ParameterizedTest
    @MethodSource("settlementPreorder")
    @Order(4)
    @DisplayName("用户-结算-预约单-获取第一个时间")
    void settlementWithFirstTime(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderOpenType,0);
        shopExtraInfoMapper.update(null,updateWrapper);
        LocalTime localTime = LocalTime.now();
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        OrderOptVO orderOptVO = settlementResponseDTO.getResult().getOrderOpt();
        Double distance = orderOptVO.getAddress().getOptAddress().getToShopDistance();
        System.out.println("distance: "+distance);
        DeliveryTimeDTO deliveryTimeDTO = preorderService.getDeliveryTime(distance);
        int makeTime = deliveryTimeDTO.getMakeTime();
        int aveDeliveryTime = deliveryTimeDTO.getAvgDeliveryTime();
//        System.out.println("makeTime: "+makeTime);
//        System.out.println("aveDeliveryTime: "+aveDeliveryTime);
        List<PreorderDeliveryTimeVO> preorderDeliveryTimeVOList = orderOptVO.getDeliveryWay().getDeliveryTime();
        assertThat(preorderDeliveryTimeVOList.size()).isGreaterThan(0);
        PreorderDeliveryTimeVO preorderDeliveryTimeVO = preorderDeliveryTimeVOList.get(0);
        assertThat(preorderDeliveryTimeVO.getTimeList().size()).isGreaterThan(0);
        //比较是不是当天
        String nowDate = preorderDeliveryTimeVO.getDate();
//        System.out.println("nowDate: "+nowDate +" <> "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FormatterCons.YEARDATEFormatter))).isEqualTo(nowDate);
        String firstTimeStr = preorderDeliveryTimeVO.getTimeList().get(0).getStartTime();

        LocalTime firstTime = LocalTime.parse(firstTimeStr,DateTimeFormatter.ofPattern(FormatterCons.TIMEFormatter));
        LocalTime actualFirstTime  = localTime.plusMinutes(makeTime+aveDeliveryTime);
//        System.out.println("firstTime: "+firstTime +" > " +actualFirstTime);
        //比较第一个开始时间是不是大于当前时间+出餐时间+配送时常平均值
        assertThat(actualFirstTime).isBefore(firstTime);
    }
    @ParameterizedTest
    @MethodSource("settlementPreorder")
    @Order(5)
    @DisplayName("用户-结算-预约天数")
    void settlementWithPreDays(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderDays,5);
        shopExtraInfoMapper.update(null,updateWrapper);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);

        List<PreorderDeliveryTimeVO> preorderDeliveryTimeVOList = settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryTime();
        assertThat(preorderDeliveryTimeVOList.size()).isEqualTo(5);
    }
    @ParameterizedTest
    @MethodSource("settlementPreorder")
    @Order(6)
    @DisplayName("用户-结算-打烊可预约&打烊不可预约")
    void settlementWithClosedSupport(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopEntity> updateWrapperShop = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopEntity> lambdaShop  = updateWrapperShop.lambda();
        lambdaShop.eq(ShopEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambdaShop.set(ShopEntity::getShopStatus,1); //1为打烊
        shopMapper.update(null,updateWrapperShop);

        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderClosedSupport,1);
        lambda.set(ShopExtraInfoEntity::getPreorderOpenType,1); //设置为全部，即立即+预约单
        shopExtraInfoMapper.update(null,updateWrapper);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResult().getOrderOther().getPreOrderOpenType()).isEqualTo(2); //判断为预约单模式
        //有预约时间
        List<PreorderDeliveryTimeVO> preorderDeliveryTimeVOList = settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryTime();
        assertThat(preorderDeliveryTimeVOList.size()).isGreaterThan(1);

        lambda.set(ShopExtraInfoEntity::getPreorderClosedSupport,0); //设置打烊不可预约
        shopExtraInfoMapper.update(null,updateWrapper);
        SettlementResponseDTO settlementResponseDTONew= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTONew.getResultCode()).isNotEqualTo(1000);
        assertThat(settlementResponseDTONew.getReason()).isEqualTo("店铺已打烊");


        lambdaShop.set(ShopEntity::getShopStatus,0); //数据还原，0是营业
        shopMapper.update(null,updateWrapperShop);

    }
    @ParameterizedTest
    @MethodSource("settlementPreorder")
    @Order(7)
    @DisplayName("用户-结算-预约时间间隔")
    void settlementWithInterval(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderOpenType,1);
        shopExtraInfoMapper.update(null,updateWrapper);
        int defaultInterval = shopExtraInfoEntity.getPreorderInterval() < 15 ? 15 : shopExtraInfoEntity.getPreorderInterval();
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);

        List<PreorderDeliveryTimeVO> preorderDeliveryTimeVOList = settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryTime();
        List<PreorderTimeDTO> preorderTimeDTOS = preorderDeliveryTimeVOList.get(0).getTimeList();
        LocalTime firstTime  = LocalTime.parse(preorderTimeDTOS.get(0).getStartTime(),DateTimeFormatter.ofPattern(FormatterCons.TIMEFormatter));
        LocalTime secTime  = LocalTime.parse(preorderTimeDTOS.get(1).getStartTime(),DateTimeFormatter.ofPattern(FormatterCons.TIMEFormatter));
//        System.out.println(firstTime +" <> " + secTime);
        assertThat(firstTime.plusMinutes(defaultInterval)).isEqualTo(secTime);
    }
    @ParameterizedTest
    @MethodSource("settlementPreorder")
    @Order(8)
    @DisplayName("用户-结算-预约截单时间")
    void settlementWithDeadLine(SettlementRequestDTO settlementRequestDTO){
        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<ShopExtraInfoEntity> lambda  = updateWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
        lambda.set(ShopExtraInfoEntity::getPreorderTimeMode,2);
        lambda.set(ShopExtraInfoEntity::getNMini,1); //1天后的时间，比较好判断
        shopExtraInfoMapper.update(null,updateWrapper);
        String nowTime = PreorderServiceImpl.LocalTime2String(LocalTime.now());
        ShopTimeConfigEntity shopTimeConfigEntity = preorderService.updateShopTimeConfigEntity(nowTime);
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        List<PreorderDeliveryTimeVO> preorderDeliveryTimeVOList = settlementResponseDTO.getResult().getOrderOpt().getDeliveryWay().getDeliveryTime();
        List<PreorderTimeDTO> preorderTimeDTOS = preorderDeliveryTimeVOList.get(0).getTimeList();
        assertThat(preorderTimeDTOS.get(0).getStartTime()).isEqualTo(shopTimeConfigEntity.getTimeStart());
        assertThat(preorderTimeDTOS.get(0).getEndTime()).isEqualTo(shopTimeConfigEntity.getTimeEnd());
        //还原数据
        lambda.set(ShopExtraInfoEntity::getPreorderTimeMode,1);
        shopExtraInfoMapper.update(null,updateWrapper);
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
