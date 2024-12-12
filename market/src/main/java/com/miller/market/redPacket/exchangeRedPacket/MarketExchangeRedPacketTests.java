package com.miller.market.redPacket.exchangeRedPacket;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.mapper.order.OrderMapper;
import com.miller.market.mapper.redPacket.RedPacketUserMapper;
import com.miller.market.order.cancelOrder.flow.MarketCancelOrderFlow;
import com.miller.market.order.cancelOrder.request.MarketCancelOrderRequestDTO;
import com.miller.market.order.cancelOrder.response.MarketCancelOrderResponseDTO;
import com.miller.market.redPacket.exchangeRedPacket.flow.MarketExchangeRedPacketFlow;
import com.miller.market.redPacket.exchangeRedPacket.request.MarketExchangeRedPacketRequestDTO;
import com.miller.market.redPacket.exchangeRedPacket.response.MarketExchangeRedPacketResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.common.enums.OrderStatusEnum;
import com.panda.market.dal.entity.Order;
import com.panda.market.dal.entity.RedPacketUser;
import com.panda.market.dal.entity.TransfersOrder;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * 兑换红包
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_兑换红包")
public class MarketExchangeRedPacketTests {

    private static RedPacketUserMapper redPacketUserMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        redPacketUserMapper = sqlSession.getMapper(RedPacketUserMapper.class);

        //预先清理用户兑换码领取记录
        QueryWrapper<RedPacketUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", BusinessConstant.userId);
        queryWrapper.eq("cd_key", "PFZCzdhty001");
        List<RedPacketUser> redPacketUsers = redPacketUserMapper.selectList(queryWrapper);

        if (!redPacketUsers.isEmpty()){
            for (RedPacketUser redPacketUser : redPacketUsers){
                new LambdaUpdateChainWrapper<>(redPacketUserMapper)
                        .set(RedPacketUser::getCdKey, "")
                        .eq(RedPacketUser::getRedPacketUserId, redPacketUser.getRedPacketUserId())
                        .update();
            }
        }
    }

    @MethodSource("staticRedPacketDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_兑换红包成功")
    void exchangeRedPacketSuccessfully(MarketExchangeRedPacketRequestDTO marketExchangeRedPacketRequestDTO) {
        MarketExchangeRedPacketResponseDTO marketExchangeRedPacketResponseDTO = MarketExchangeRedPacketFlow.exchangeRedPacket(marketExchangeRedPacketRequestDTO);

        Assertions.assertThat(marketExchangeRedPacketResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketExchangeRedPacketResponseDTO.getData().getResult()).isTrue();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticRedPacketDataProvider() {
        // 查询待支付订单
        MarketExchangeRedPacketRequestDTO requestDTO = new MarketExchangeRedPacketRequestDTO();
        requestDTO.setCdKey("PFZCzdhty001");
        requestDTO.setRedPacketCustomerType(2);
        return Stream.of(Arguments.of(requestDTO));
    }

}
