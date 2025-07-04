package com.miller.userapp.module.order.shopping.settlement.MemberCombined;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.member.MemberBuyDetailOrderShowRes;
import com.hungrypanda.app.server.api.res.order.RedPacketVO;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.app.server.entity.address.CityFunctionConfigEntity;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.hungrypanda.app.server.entity.member.MemberCityFeeReduceEntity;
import com.hungrypanda.app.server.entity.member.MemberPacketEntity;
import com.hungrypanda.app.server.entity.member.MemberShopRedPacketConfigurationEntity;
import com.hungrypanda.app.server.entity.redpacket.RedPacketEntity;
import com.miller.common.util.MD5Util;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.member.MemberCityMapper;
import com.miller.userapp.mapper.member.MemberPacketMapper;
import com.miller.userapp.mapper.member.MemberShopRedPacketConfigurationMapper;
import com.miller.userapp.mapper.redpacket.RedPacketMapper;
import com.miller.userapp.mapper.shop.CityFunctionConfigMapper;
import com.miller.userapp.mapper.virtual.MemberCityFeeReduceMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import com.panda.promotion.server.dal.dao.template.CouponTemplateMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author heyuan
 * @version 1.0
 * @since 2025/3/02 16:14
 */
@Scenario(scenarioID = "01JKQEJ6YQ5N9XD5G3DBG34DQ3", scenarioName = "会员合单", developmentTime = 160, maintenanceTime = 0, manualTestTime = 20, author = "heyuan@hungrypandagroup.com")
@EnvTag.Test
@DisplayName("配置会员&代金券合单优先级：共同展示&会员权益配置仅会员店铺红包")
public class SettlementMemberCombinedAndMemberShopCouponTests {
   private static SqlSession sqlSession;
   static MemberCityMapper memberCityMapper;
   static MemberCityFeeReduceMapper memberCityFeeReduceMapper;
   static CityFunctionConfigMapper cityFunctionConfigMapper;
   static MemberPacketMapper memberPacketMapper;
   static RedPacketMapper redPacketMapper;
   static MemberShopRedPacketConfigurationMapper memberShopRedPacketConfigurationMapper;

   UserLoginRequestDTO userLoginRequestDTO;
   static Long memberCityId = 1111378L;
   static Integer cityFunctionConfigId = 4058;
   static long memberPacketId = 2778L;
   static Long redPacketId = 888894214L;

   //   static Integer cityId = 508;
   @BeforeAll
   void beforeAll() {
      sqlSession = DBUtils.getDBOfPandaTest();
      memberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
//      memberDeliveryPriceMapper=sqlSession.getMapper(MemberDeliveryPriceMapper.class);
      memberCityFeeReduceMapper = sqlSession.getMapper(MemberCityFeeReduceMapper.class);
      redPacketMapper = sqlSession.getMapper(RedPacketMapper.class);
      cityFunctionConfigMapper = sqlSession.getMapper(CityFunctionConfigMapper.class);
      memberPacketMapper = sqlSession.getMapper(MemberPacketMapper.class);
      memberShopRedPacketConfigurationMapper =sqlSession.getMapper(MemberShopRedPacketConfigurationMapper.class);
      //关闭会员运费减免
      UpdateWrapper<MemberCityEntity> updateWrapper1 = new UpdateWrapper<>();
      LambdaUpdateWrapper<MemberCityEntity> lamda1 = updateWrapper1.lambda();
      lamda1.eq(MemberCityEntity::getMemberCityId, memberCityId);
      lamda1.set(MemberCityEntity::getIsOpenDeliveryDiscount, 1);
      lamda1.set(MemberCityEntity::getOnlineStatus, 1);
      memberCityMapper.update(new MemberCityEntity(), updateWrapper1);
      //关闭服务费减免
      UpdateWrapper<MemberCityFeeReduceEntity> updateWrapper2 = new UpdateWrapper<>();
      LambdaUpdateWrapper<MemberCityFeeReduceEntity> lamda2 = updateWrapper2.lambda();
      lamda2.eq(MemberCityFeeReduceEntity::getMemberCityId, memberCityId);
      lamda2.set(MemberCityFeeReduceEntity::getStatus, 0);
      memberCityFeeReduceMapper.update(new MemberCityFeeReduceEntity(), updateWrapper2);
      //会员&代金券合单优先级-共同展示
      UpdateWrapper<CityFunctionConfigEntity> updateWrapper3 = new UpdateWrapper<>();
      LambdaUpdateWrapper<CityFunctionConfigEntity> lamda3 = updateWrapper3.lambda();
      lamda3.eq(CityFunctionConfigEntity::getId, cityFunctionConfigId);
      lamda3.set(CityFunctionConfigEntity::getStatus, 1);
      lamda3.set(CityFunctionConfigEntity::getCombinedOrderShowType, 1);
      cityFunctionConfigMapper.update(new CityFunctionConfigEntity(), updateWrapper3);
      //会员权益红包全部移除
      UpdateWrapper<MemberPacketEntity> updateWrapper4 = new UpdateWrapper<>();
      LambdaUpdateWrapper<MemberPacketEntity> lamda4 = updateWrapper4.lambda();
      lamda4.eq(MemberPacketEntity::getMemberCityId, memberCityId);
      lamda4.set(MemberPacketEntity::getIsDel, 1);
      memberPacketMapper.update(new MemberPacketEntity(), updateWrapper4);
      //关闭所有会员店铺红包
      UpdateWrapper<MemberShopRedPacketConfigurationEntity> updateWrapper5 = new UpdateWrapper<>();
      LambdaUpdateWrapper<MemberShopRedPacketConfigurationEntity> lamda5 = updateWrapper5.lambda();
      lamda5.eq(MemberShopRedPacketConfigurationEntity::getMemberCityId, memberCityId);
      lamda5.set(MemberShopRedPacketConfigurationEntity::getIsDel, 1);
      memberShopRedPacketConfigurationMapper.update(new MemberShopRedPacketConfigurationEntity(), updateWrapper5);
      //打开所需会员店铺红包计划
      UpdateWrapper<MemberShopRedPacketConfigurationEntity> updateWrapper6 = new UpdateWrapper<>();
      LambdaUpdateWrapper<MemberShopRedPacketConfigurationEntity> lamda6 = updateWrapper6.lambda();
      lamda6.eq(MemberShopRedPacketConfigurationEntity::getId, 336);
      lamda6.set(MemberShopRedPacketConfigurationEntity::getIsDel, 0);
      lamda6.set(MemberShopRedPacketConfigurationEntity::getGroupStatus, 0);
      memberShopRedPacketConfigurationMapper.update(new MemberShopRedPacketConfigurationEntity(), updateWrapper6);



      userLoginRequestDTO = new UserLoginRequestDTO();
      userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.no.coupon.user.account"));
      userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.no.coupon.user.password")));
      userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
      userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
      userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

      UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
   }


   @ParameterizedTest
   @MethodSource("memberDeliveryFeeData")
   @Order(1)
   @DisplayName("结算不适用会员权益-会员店铺红包")
   void settlementNoCashRedPacket(SettlementRequestDTO settlementRequestDTO) {
      UpdateWrapper<RedPacketEntity> updateWrapper4 = new UpdateWrapper<>();
      LambdaUpdateWrapper<RedPacketEntity> lamda4 = updateWrapper4.lambda();
      lamda4.eq(RedPacketEntity::getRedPacketId, redPacketId);
      lamda4.set(RedPacketEntity::getCity, "杭州市");
      redPacketMapper.update(new RedPacketEntity(), updateWrapper4);


      SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
      MemberBuyDetailOrderShowRes memberBuyDetailOrderShowRes = settlementResponseDTO.getResult().getOrderOpt().getOrderPaymentCombined().getMemberBuyDetailOrderShowRes();
      assert memberBuyDetailOrderShowRes == null;

   }

   @ParameterizedTest
   @MethodSource("memberDeliveryFeeData")
   @Order(2)
   @DisplayName("结算适用会员权益-会员店铺红包")
   void settlementWithCashRedPacket(SettlementRequestDTO settlementRequestDTO) {
      UpdateWrapper<RedPacketEntity> updateWrapper4 = new UpdateWrapper<>();
      LambdaUpdateWrapper<RedPacketEntity> lamda4 = updateWrapper4.lambda();
      lamda4.eq(RedPacketEntity::getRedPacketId, redPacketId);
      lamda4.set(RedPacketEntity::getCity, "九江市");
      redPacketMapper.update(new RedPacketEntity(), updateWrapper4);

//      RedPacketEntity redPacketEntity = redPacketMapper.selectOne(new QueryWrapper<RedPacketEntity>().eq("red_packet_id", redPacketId));

      SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);

      MemberBuyDetailOrderShowRes memberBuyDetailOrderShowRes = settlementResponseDTO.getResult().getOrderOpt().getOrderPaymentCombined().getMemberBuyDetailOrderShowRes();
      RedPacketVO currentOrderRedPacket = memberBuyDetailOrderShowRes.getCurrentOrderRedPacket();

      assertThat(currentOrderRedPacket.getRedPacketId().equals(redPacketId));

   }


   /**
    * 测试用例数据提供者
    */
   static Stream<Arguments> memberDeliveryFeeData() {
      SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
      settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
      settlementRequestDTO.setTablewareCount(1);
      settlementRequestDTO.setMemberCityId(memberCityId);
      settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
      settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
      settlementRequestDTO.setAddressId(TestCaseDataForMerchantConstant.memberAddressId);
      settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
      settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopIDFormemberOrder);
      // 是否自动使用红包，不使用红包
      settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.YES.getType());
      settlementRequestDTO.setMemberBuyType(1);
      settlementRequestDTO.setMemberCombinedType(0);

      var productCartList = new ArrayList<ProductCart>();
      var productCart = new ProductCart();
      productCart.setSkuId(0L);
      productCart.setProductId(TestCaseDataForMerchantConstant.productIDFormemberCombineOrder);
      productCartList.add(productCart);
      settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

      return Stream.of(Arguments.of(settlementRequestDTO));
   }
}
  