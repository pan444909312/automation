package com.miller.market.pf.redPacket.exchangeRedPacket;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.miller.market.constants.PFBusinessConstant;
import com.miller.market.mapper.redPacket.RedPacketUserMapper;
import com.miller.market.pf.redPacket.exchangeRedPacket.flow.ExchangeRedPacketFlow;
import com.miller.market.pf.redPacket.exchangeRedPacket.request.ExchangeRedPacketRequestDTO;
import com.miller.market.pf.redPacket.exchangeRedPacket.response.ExchangeRedPacketResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.dal.entity.RedPacketUser;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;


/**
 * 兑换红包
 */
@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQTKCFR1R6XZDPF13D0JYY5N", scenarioName = "APP-进入用户首页-pf融合兑换pf红包"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合兑换pf红包")
public class ExchangeRedPacketScenarioTests {

    private static RedPacketUserMapper redPacketUserMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        redPacketUserMapper = sqlSession.getMapper(RedPacketUserMapper.class);

        //预先清理用户兑换码领取记录
        QueryWrapper<RedPacketUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", PFBusinessConstant.userId);
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
    @DisplayName("PF融合_兑换pf红包：兑换成功")
    void exchangeRedPacketSuccessfully(ExchangeRedPacketRequestDTO requestDTO) {
        ExchangeRedPacketResponseDTO responseDTO = ExchangeRedPacketFlow.exchangeRedPacket(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(1);
        Assertions.assertThat(responseDTO.getData().getResult()).isTrue();

        //验证用户是否领取到账
        QueryWrapper<RedPacketUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", PFBusinessConstant.userId);
        queryWrapper.eq("cd_key", "PFZCzdhty001");
        List<RedPacketUser> redPacketUsers = redPacketUserMapper.selectList(queryWrapper);
        Assertions.assertThat(redPacketUsers.get(0)).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticRedPacketDataProvider() {
        ExchangeRedPacketRequestDTO requestDTO = new ExchangeRedPacketRequestDTO();
        //以下均为必填字段
        requestDTO.setCdKey("PFZCzdhty001");
        requestDTO.setRedPacketCustomerType(2);
        requestDTO.setUcUserId(249222L);
        requestDTO.setPortalId(3L);
        requestDTO.setRegionId(3L);
        requestDTO.setLatitude("30.20111");
        requestDTO.setLongitude("120.22136");
        return Stream.of(Arguments.of(requestDTO));
    }

}
