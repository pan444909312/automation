package com.miller.userapp.module.order.shopping.settlement.MemberCombined;

/**
 * @author heyuan
 * @version 1.0
 * @since 2025/2/25 18:01
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.member.MemberBuyDetailOrderShowRes;
import com.hungrypanda.app.server.api.res.order.OrderAmountVO;
import com.hungrypanda.app.server.api.res.order.RedPacketVO;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.app.server.entity.address.CityFunctionConfigEntity;
import com.hungrypanda.app.server.entity.member.*;
import com.hungrypanda.app.server.entity.redpacket.RedPacketEntity;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.miller.common.util.MD5Util;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.member.MemberCityMapper;
import com.miller.userapp.mapper.member.MemberDeliveryPriceMapper;
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
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author heyuan
 * @version 1.0
 * @since 2025/2/10 16:17
 */
@Scenario(scenarioID = "01JKQEJ6YQ5N9XD5G3DBG34DQ9", scenarioName = "会员合单", developmentTime = 160, maintenanceTime = 0, manualTestTime = 20, author = "heyuan@hungrypandagroup.com")
@EnvTag.Test
@DisplayName("配置会员&代金券合单优先级：共同展示&会员权益配置仅运费立减")
public class SettlementMemberCombinedAndDeliveryFeeTests {
    private static SqlSession sqlSession;
    static MemberCityMapper memberCityMapper;
    static MemberCityFeeReduceMapper memberCityFeeReduceMapper;
    static CityFunctionConfigMapper cityFunctionConfigMapper;
    static MemberPacketMapper memberPacketMapper;
    static RedPacketMapper redPacketMapper;
    static MemberShopRedPacketConfigurationMapper memberShopRedPacketConfigurationMapper;
    static MemberDeliveryPriceMapper memberDeliveryPriceMapper;


    UserLoginRequestDTO userLoginRequestDTO;
    static Long memberCityId = 1111378L;
    static Integer cityFunctionConfigId = 4058;
    static Integer itemAmount = 0;

    //   static Integer cityId = 508;
    @BeforeAll
    void beforeAll() {
        sqlSession = DBUtils.getDBOfPandaTest();
        memberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
        memberDeliveryPriceMapper=sqlSession.getMapper(MemberDeliveryPriceMapper.class);
        memberCityFeeReduceMapper = sqlSession.getMapper(MemberCityFeeReduceMapper.class);
        redPacketMapper = sqlSession.getMapper(RedPacketMapper.class);
        cityFunctionConfigMapper = sqlSession.getMapper(CityFunctionConfigMapper.class);
        memberPacketMapper = sqlSession.getMapper(MemberPacketMapper.class);
        memberShopRedPacketConfigurationMapper = sqlSession.getMapper(MemberShopRedPacketConfigurationMapper.class);

        //开启会员运费减免
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
        //新增现金红包
        UpdateWrapper<MemberPacketEntity> updateWrapper4 = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberPacketEntity> lamda4 = updateWrapper4.lambda();
        lamda4.eq(MemberPacketEntity::getMemberCityId, memberCityId);
        lamda4.set(MemberPacketEntity::getIsDel, 1);
        memberPacketMapper.update(new MemberPacketEntity(), updateWrapper4);
        //关闭所有会员店铺红包
        UpdateWrapper<MemberShopRedPacketConfigurationEntity> updateWrapper6 = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberShopRedPacketConfigurationEntity> lamda6 = updateWrapper6.lambda();
        lamda6.eq(MemberShopRedPacketConfigurationEntity::getMemberCityId, memberCityId);
        lamda6.set(MemberShopRedPacketConfigurationEntity::getIsDel, 1);
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
    @DisplayName("结算不适用会员权益-运费立减")
    void settlementNoCashRedPacket(SettlementRequestDTO settlementRequestDTO) {
        UpdateWrapper<MemberCityEntity> updateWrapper1 = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberCityEntity> lamda1 = updateWrapper1.lambda();
        lamda1.eq(MemberCityEntity::getMemberCityId, memberCityId);
        lamda1.set(MemberCityEntity::getDeratePrice, 1000000);
        lamda1.set(MemberCityEntity::getIsOpenDeliveryDiscount, 1);
        lamda1.set(MemberCityEntity::getOnlineStatus, 1);
        memberCityMapper.update(new MemberCityEntity(), updateWrapper1);


        SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
        MemberBuyDetailOrderShowRes memberBuyDetailOrderShowRes = settlementResponseDTO.getResult().getOrderOpt().getOrderPaymentCombined().getMemberBuyDetailOrderShowRes();
        assert memberBuyDetailOrderShowRes == null;

    }

    @ParameterizedTest
    @MethodSource("memberDeliveryFeeData")
    @Order(2)
    @DisplayName("结算适用会员权益-运费立减")
    void settlementWithCashRedPacket(SettlementRequestDTO settlementRequestDTO) {
        UpdateWrapper<MemberCityEntity> updateWrapper1 = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberCityEntity> lamda1 = updateWrapper1.lambda();
        lamda1.eq(MemberCityEntity::getMemberCityId, memberCityId);
        lamda1.set(MemberCityEntity::getDeratePrice, 100);
        lamda1.set(MemberCityEntity::getIsOpenDeliveryDiscount, 1);
        lamda1.set(MemberCityEntity::getOnlineStatus, 1);
        memberCityMapper.update(new MemberCityEntity(), updateWrapper1);


        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);

        OrderAmountVO discountDelivery = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(item -> item.getItemKey().equals("discountDelivery")).findFirst().get();
        List<OrderAmountVO> mergeList = discountDelivery.getMergeList();
        JSONArray itemKeyList = new JSONArray();
        if (mergeList != null) {

            for (OrderAmountVO orderAmountVO : mergeList) {
                itemKeyList.add(orderAmountVO.getItemKey());
                if (orderAmountVO.getItemKey().equals("memberDeliveryDiscount")) {
                    itemAmount = orderAmountVO.getItemAmount();
                }
            }
        }
        MemberDeliveryPriceEntity memberDeliveryPriceEntity = memberDeliveryPriceMapper.selectOne(new QueryWrapper<MemberDeliveryPriceEntity>().eq("member_delivery_price_id",472));

        assertThat(itemAmount.equals(memberDeliveryPriceEntity.getMemberDeliveryPrice()));
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
  