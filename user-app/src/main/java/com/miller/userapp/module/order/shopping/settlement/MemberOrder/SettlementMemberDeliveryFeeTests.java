package com.miller.userapp.module.order.shopping.settlement.MemberOrder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.OrderAmountVO;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.app.server.entity.member.MemberEntityDeliveryPriceEntity;
import com.miller.common.util.MD5Util;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.virtual.MemberEntityDeliveryPriceMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.module.shop.card.version2.home.request.ShopListRequestDTO;
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
 * @since 2024/11/26 11:02
 */
@Scenario(scenarioID = "01JBV23SAR8WP6EKSYZK0EEE3S",
        scenarioName = "用户是会员并且会员权益有运费减免，会员运费减免剩余次数>0，配送费-运费减免<=会员运费减免金额",
        developmentTime = 160, maintenanceTime = 0, manualTestTime = 20)
@EnvTag.Test
@DisplayName("结算-会员运费减免")
public class SettlementMemberDeliveryFeeTests {
    private static SqlSession sqlSession;
    static MemberEntityDeliveryPriceMapper memberEntityDeliveryPriceMapper;
    Long id = 1492300933476106384L;

    UserLoginRequestDTO userLoginRequestDTO;
    static Integer sumItemAmount = 0;
    static Integer itemAmount = 0;
    @BeforeAll
    void beforeAll() {
        sqlSession = DBUtils.getDBOfPandaTest();
        memberEntityDeliveryPriceMapper =sqlSession.getMapper(MemberEntityDeliveryPriceMapper.class);
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
    @DisplayName("用户是会员并且会员权益有运费减免，会员运费减免剩余次数>0，配送费-运费减免>会员运费减免金额")
    void settlementMemberDeliveryFee(SettlementRequestDTO settlementRequestDTO){

        UpdateWrapper<MemberEntityDeliveryPriceEntity> updateWrapper1 = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberEntityDeliveryPriceEntity> lamda1 = updateWrapper1.lambda();
        lamda1.eq(MemberEntityDeliveryPriceEntity::getMemberEntityId, id);
        lamda1.set(MemberEntityDeliveryPriceEntity::getDeliveryType, 0);
        memberEntityDeliveryPriceMapper.update(updateWrapper1);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        OrderAmountVO discountDelivery = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(item -> item.getItemKey().equals("discountDelivery")).findFirst().get();
        List<OrderAmountVO> mergeList = discountDelivery.getMergeList();
        JSONArray itemKeyList = new JSONArray();
        if (mergeList !=null){

            for (OrderAmountVO orderAmountVO : mergeList) {
                sumItemAmount = orderAmountVO.getItemAmount() + sumItemAmount;
                itemKeyList.add(orderAmountVO.getItemKey());

            }
             itemAmount = mergeList.get(0).getItemAmount() - (sumItemAmount - mergeList.get(0).getItemAmount());
        }
        assertThat(discountDelivery.getItemAmount()).isEqualTo(itemAmount);
        assertThat(itemKeyList.toString().contains("memberDeliveryDiscount"));

    }
    @ParameterizedTest
    @MethodSource("memberDeliveryFeeData")
    @Order(2)
    @DisplayName("用户是会员并且会员权益有运费减免，会员运费减免剩余次数>0，配送费-运费减免<=会员运费减免金额")
    void settlementMemberDeliveryFreeFee(SettlementRequestDTO settlementRequestDTO){

        UpdateWrapper<MemberEntityDeliveryPriceEntity> updateWrapper1 = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberEntityDeliveryPriceEntity> lamda1 = updateWrapper1.lambda();
        lamda1.eq(MemberEntityDeliveryPriceEntity::getMemberEntityId, id);
        lamda1.set(MemberEntityDeliveryPriceEntity::getDeliveryType, 1);
        memberEntityDeliveryPriceMapper.update(updateWrapper1);

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        OrderAmountVO discountDelivery = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(item -> item.getItemKey().equals("discountDelivery")).findFirst().get();
        List<OrderAmountVO> mergeList = discountDelivery.getMergeList();
        JSONArray itemKeyList = new JSONArray();
        if (mergeList !=null){

            for (OrderAmountVO orderAmountVO : mergeList) {
                itemKeyList.add(orderAmountVO.getItemKey());

            }
        }
        Integer memberDeliveryFee = mergeList.get(0).getItemAmount()-mergeList.get(2).getItemAmount();
        assertThat(discountDelivery.getItemAmount()).isEqualTo(0);
        assertThat(itemKeyList.toString().contains("memberDeliveryDiscount"));
        assertThat(memberDeliveryFee).isEqualTo(mergeList.get(1).getItemAmount());

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
  