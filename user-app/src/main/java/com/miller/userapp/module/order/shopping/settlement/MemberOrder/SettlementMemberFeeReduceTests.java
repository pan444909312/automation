package com.miller.userapp.module.order.shopping.settlement.MemberOrder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.OrderAmountVO;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.app.server.entity.data.AdsSearchDistanceMatrixEntity;
import com.hungrypanda.app.server.entity.member.MemberCityFeeReduceEntity;
import com.miller.common.util.MD5Util;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
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
 * @since 2024/12/6 18:03
 */
@Scenario(scenarioID = "01JEDQDKGXRPDKC276AS7SPK0X",
        scenarioName = "用户是会员并且会员权益有服务费费减免，会员服务费减免剩余次数>0，店铺服务费>0，店铺服务费<=服务费减免金额",
        developmentTime = 160, maintenanceTime = 0, manualTestTime = 20)
@EnvTag.Test
@DisplayName("结算-会员服务费减免")
public class SettlementMemberFeeReduceTests {
        private static SqlSession sqlSession;
        static MemberCityFeeReduceMapper memberCityFeeReduceMapper;
        Long memberCityId = 1111378L;

        UserLoginRequestDTO userLoginRequestDTO;
         static Integer itemAmount = 0;
        static Integer platformFee = 0;
        @BeforeAll
        void beforeAll() {
            sqlSession = DBUtils.getDBOfPandaTest();
            memberCityFeeReduceMapper =sqlSession.getMapper(MemberCityFeeReduceMapper.class);
            userLoginRequestDTO = new UserLoginRequestDTO();
            userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.member.order.user.account"));
            userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.member.order.user.password")));
            userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
            userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
            userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

            UserLoginFlow.loginAndPutToken(userLoginRequestDTO);

        }
    @ParameterizedTest
    @MethodSource("memberDeliveryFeeData")
    @Order(1)
    @DisplayName("用户是会员并且会员权益有服务费费减免，会员服务费减免剩余次数>0，店铺服务费>0，店铺服务费>服务费减免金额")
    void settlementMemberFeeReducePartFree(SettlementRequestDTO settlementRequestDTO){

        UpdateWrapper<MemberCityFeeReduceEntity> updateWrapper1 = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberCityFeeReduceEntity> lamda1 = updateWrapper1.lambda();
        lamda1.eq(MemberCityFeeReduceEntity::getMemberCityId, memberCityId);
        lamda1.set(MemberCityFeeReduceEntity::getReduceShopType, 0);
        lamda1.set(MemberCityFeeReduceEntity::getReduceThreshold, 0);
        lamda1.set(MemberCityFeeReduceEntity::getReduceNumLimit, 0);
        lamda1.set(MemberCityFeeReduceEntity::getReduceType, 1);
        lamda1.set(MemberCityFeeReduceEntity::getReducePrice, 50);
        lamda1.set(MemberCityFeeReduceEntity::getStatus, 1);
        memberCityFeeReduceMapper.update(null, updateWrapper1);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        OrderAmountVO discountPlatformFee = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(item -> item.getItemKey().equals("discountPlatformFee")).findFirst().get();
        List<OrderAmountVO> mergeList = discountPlatformFee.getMergeList();
        MemberCityFeeReduceEntity memberCityFeeReduceEntity = memberCityFeeReduceMapper.selectOne(new QueryWrapper<MemberCityFeeReduceEntity>().eq("member_city_id", memberCityId));
        Integer reducePrice = memberCityFeeReduceEntity.getReducePrice();
        if (mergeList !=null){

            for (OrderAmountVO orderAmountVO : mergeList) {
                if (orderAmountVO.getItemKey().equals("memberPlatformFeeDiscount")){
                    itemAmount = orderAmountVO.getItemAmount();
                }
                if (orderAmountVO.getItemKey().equals("platformFee")){
                    platformFee = orderAmountVO.getItemAmount();
                }
            }
        }
        assertThat(itemAmount).isEqualTo(platformFee-discountPlatformFee.getItemAmount()).isEqualTo(reducePrice);
        }

    @ParameterizedTest
    @MethodSource("memberDeliveryFeeData")
    @Order(2)
    @DisplayName("用户是会员并且会员权益有服务费费减免，会员服务费减免剩余次数>0，店铺服务费>0，店铺服务费<=服务费减免金额")
    void settlementMemberFeeReduceAllFree(SettlementRequestDTO settlementRequestDTO){

        UpdateWrapper<MemberCityFeeReduceEntity> updateWrapper1 = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberCityFeeReduceEntity> lamda1 = updateWrapper1.lambda();
        lamda1.eq(MemberCityFeeReduceEntity::getMemberCityId, memberCityId);
        lamda1.set(MemberCityFeeReduceEntity::getReduceShopType, 0);
        lamda1.set(MemberCityFeeReduceEntity::getReduceThreshold, 0);
        lamda1.set(MemberCityFeeReduceEntity::getReduceNumLimit, 0);
        lamda1.set(MemberCityFeeReduceEntity::getReduceType, 1);
        lamda1.set(MemberCityFeeReduceEntity::getReducePrice, 100000);
        lamda1.set(MemberCityFeeReduceEntity::getStatus, 1);
        memberCityFeeReduceMapper.update(null, updateWrapper1);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        OrderAmountVO discountPlatformFee = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(item -> item.getItemKey().equals("discountPlatformFee")).findFirst().get();
        List<OrderAmountVO> mergeList = discountPlatformFee.getMergeList();
        if (mergeList !=null){

            for (OrderAmountVO orderAmountVO : mergeList) {
               if (orderAmountVO.getItemKey().equals("memberPlatformFeeDiscount")){
                   itemAmount = orderAmountVO.getItemAmount();
                   }
                if (orderAmountVO.getItemKey().equals("platformFee")){
                    platformFee = orderAmountVO.getItemAmount();
               }
            }
        }
        assertThat(itemAmount).isEqualTo(platformFee);
        assertThat(discountPlatformFee.getItemAmount()).isEqualTo(0);
    }



    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> memberDeliveryFeeData() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForMerchantConstant.memberAddressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopIDFormemberOrder);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(TestCaseDataForMerchantConstant.productIDFormemberOrder);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }


}
  