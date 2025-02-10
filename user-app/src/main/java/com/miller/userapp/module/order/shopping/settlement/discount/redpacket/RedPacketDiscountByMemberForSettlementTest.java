package com.miller.userapp.module.order.shopping.settlement.discount.redpacket;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.OrderAmountVO;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.app.server.entity.member.MembershipPlusCycleConfigurationEntity;
import com.miller.common.util.MD5Util;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.data.member.db.MemberShipPlusCycleConfigSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.home.login.response.UserLoginResponseDTO;
import com.miller.userapp.module.order.shopping.settlement.discount.ShopsEnum;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.base.flow.FetchShopRedPacketFlow;
import com.miller.userapp.module.shop.card.version2.redPacket.base.request.FetchShopRedPacketRequestDTO;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JKJ8KJHV1DZ44QX4K7N839A4",
        scenarioName = "正常流程_结算_优惠项-红包减免-用户会员",
        developmentTime = 300, maintenanceTime = 30, manualTestTime = 60)
@EnvTag.Test
@DisplayName("红包减免-用户会员")
public class RedPacketDiscountByMemberForSettlementTest {
    private static SqlSession sqlSession;
    @BeforeAll
    static void beforeAll() {
        // 构造请求数据，从数据库查询结果作为请求数据
        UserLoginRequestDTO user = new UserLoginRequestDTO();
        user.setAreaCode("86");
        user.setAccount("17141976639"); //九江会员
        user.setPassword(MD5Util.string2MD5("12345678"));
        user.setType(2);
        user.setDistinctId("ed99f8b03a64c6c1");

        UserLoginResponseDTO userLoginResponseDTO = UserLoginFlow.loginReturnBodyObject(user);

        // 获取token
        var token = userLoginResponseDTO.getResult().getAccessToken();
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        sqlSession = DBUtils.getDBOfPandaTest();

    }
    @ParameterizedTest
    @MethodSource("settlementRedPacketFullReduceAddingForMember")
    @DisplayName("结算-优惠-满减红包-减免8,店铺日常加码2，会员日加码3:会员")
    void settlementWithRedPacketFullReduceAddingForMember(SettlementRequestDTO settlementRequestDTO){
        //先领取红包
        fetchShopRedPacketTest(888893950L);
        //结算
        OrderAmountVO orderAmountItemDiscount = settlementWithRedPacketFullReduceAddingIsMemberDate(settlementRequestDTO);
        if(isMemberDate()){
            assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo((1100));//2+3
            setMemberShipPlusCycleConfig(false); //设置成false，再判断
            OrderAmountVO orderAmountItemDiscount1 = settlementWithRedPacketFullReduceAddingIsMemberDate(settlementRequestDTO);
            assertThat(orderAmountItemDiscount1.getItemAmount()).isEqualTo((800));//+2
        }else {
            assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo((800));
            setMemberShipPlusCycleConfig(true); //设置成true，再判断
            OrderAmountVO orderAmountItemDiscount2 = settlementWithRedPacketFullReduceAddingIsMemberDate(settlementRequestDTO);
            assertThat(orderAmountItemDiscount2.getItemAmount()).isEqualTo((1100));
        }
    }
    @ParameterizedTest
    @MethodSource("settlementRedPacketFullDiscountAddingForMember")
    @DisplayName("结算-优惠-满折红包-1.5折,店铺日常加码3（0.5折），会员日加码4（1折）:会员")
    void settlementWithRedPacketFullDiscountAddingForMember(SettlementRequestDTO settlementRequestDTO){
        //先领取红包
        fetchShopRedPacketTest(888893952L);
        //结算
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();

        int baseAmount = 0;
//        int redPacketDiscountAmount = 0;
        for (OrderAmountVO orderAmountVO : orderAmountItemList){
            if (orderAmountVO.getItemKey().equals("tableware")){
                baseAmount += orderAmountVO.getItemAmount();
            }
            if (orderAmountVO.getItemKey().equals("packaging")){
                baseAmount += orderAmountVO.getItemAmount();
            }
            if (orderAmountVO.getItemKey().equals("product")){
                baseAmount += orderAmountVO.getItemAmount();
            }
//            if (orderAmountVO.getItemKey().equals("redPacketDiscount")){
//                redPacketDiscountAmount += orderAmountVO.getItemAmount();
//            }
        }
        int discount = new BigDecimal(baseAmount).multiply(new BigDecimal(0.15)).intValue();
        int discountDaily = new BigDecimal(baseAmount).multiply(new BigDecimal(0.15+0.05)).intValue();//0.5
        int discountMember = new BigDecimal(baseAmount).multiply(new BigDecimal(0.15+0.15)).intValue(); //0.05+0.1
        assertThat(4000).isGreaterThan(discount);
        assertThat(4000+300).isGreaterThan(discountDaily);
        assertThat(4000+700).isGreaterThan(discountMember);
        OrderAmountVO orderAmountItemDiscount = settlementWithRedPacketFullReduceAddingIsMemberDate(settlementRequestDTO);
        if(isMemberDate()){
            assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo(discountMember);
            setMemberShipPlusCycleConfig(false); //设置成false，再判断
            OrderAmountVO orderAmountItemDiscount1 = settlementWithRedPacketFullReduceAddingIsMemberDate(settlementRequestDTO);
            assertThat(orderAmountItemDiscount1.getItemAmount()).isEqualTo(discountDaily);
        }else {
            assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo(discountDaily);
            setMemberShipPlusCycleConfig(true); //设置成true，再判断
            OrderAmountVO orderAmountItemDiscount2 = settlementWithRedPacketFullReduceAddingIsMemberDate(settlementRequestDTO);
            assertThat(orderAmountItemDiscount2.getItemAmount()).isEqualTo(discountMember);
        }
    }


    private static OrderAmountVO settlementWithRedPacketFullReduceAddingIsMemberDate(SettlementRequestDTO settlementRequestDTO){
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO orderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("redPacketDiscount")).findFirst().get();
        return orderAmountItemDiscount;
    }
    static Stream<Arguments> settlementRedPacketFullReduceAddingForMember() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(1398672664L);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        // 是否自动使用红包，使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.YES.getType());
        settlementRequestDTO.setRedUseSn("102efd0c-c6a8-40f6-9f24-49db4ae21892");
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
    static Stream<Arguments> settlementRedPacketFullDiscountAddingForMember() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(1398672664L);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        // 是否自动使用红包，使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.YES.getType());
        settlementRequestDTO.setRedUseSn("a28bc586-2db4-4bd1-b9be-4ef6e0bc3f2a");
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
    private static void fetchShopRedPacketTest(Long redPacketId){
        FetchShopRedPacketRequestDTO requestDTO = new FetchShopRedPacketRequestDTO();
        requestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        requestDTO.setPacketBenefitType(1);
        requestDTO.setRedPacketId(redPacketId);
        FetchShopRedPacketFlow.fetchShopRedPacketFlow(requestDTO);
    }
/**
 *
 * @param isMemberDate 是否需要会员日;true为需要
 */
private static void setMemberShipPlusCycleConfig(boolean isMemberDate){
    String week = String.valueOf(LocalDateTime.now().getDayOfWeek().getValue());
    MemberShipPlusCycleConfigSql sql = new MemberShipPlusCycleConfigSql(sqlSession);
    MembershipPlusCycleConfigurationEntity entity = sql.getMembershipPlusCycleConfigurationEntity(1111378L);
    if(Objects.nonNull(entity)){
        if (entity.getDayLimit().contains(week)){
            if (isMemberDate) return;
            //如果要的是不存在则移除
            sql.updateMembershipPlusCycleConfigurationEntity(1111378L,entity.getDayLimit().replace(week,""));
        }else {
            if (!isMemberDate) return;
            //如果要的是存在则添加
            sql.updateMembershipPlusCycleConfigurationEntity(1111378L,"[" +week+"]");

        }
    }else {
        if (isMemberDate){
            //如果需要就插入
            long timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            MembershipPlusCycleConfigurationEntity membershipPlusCycleConfigurationEntity = new MembershipPlusCycleConfigurationEntity();
            membershipPlusCycleConfigurationEntity.setMemberCityId(1111378L);
            membershipPlusCycleConfigurationEntity.setStartUsing(1);
            membershipPlusCycleConfigurationEntity.setDayLimit("[" +week+"]");
            membershipPlusCycleConfigurationEntity.setCreateTime(timestamp);
            membershipPlusCycleConfigurationEntity.setUpdateTime(timestamp);
            membershipPlusCycleConfigurationEntity.setCreateUserId(189L);
            membershipPlusCycleConfigurationEntity.setUpdateUserId(189L);
            membershipPlusCycleConfigurationEntity.setIsDel(0);
            sql.insert(membershipPlusCycleConfigurationEntity);
        }

    }
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
}
    private static boolean isMemberDate(){
        String week = String.valueOf(LocalDateTime.now().getDayOfWeek().getValue());
        MemberShipPlusCycleConfigSql sql = new MemberShipPlusCycleConfigSql(sqlSession);
        MembershipPlusCycleConfigurationEntity entity = sql.getMembershipPlusCycleConfigurationEntity(1111378L);
        return Objects.nonNull(entity) && entity.getDayLimit().contains(week);
    }
}
