package com.miller.market.pf.redPacket.queryPFRedPacketList;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.market.mapper.redPacket.RedPacketMapper;
import com.miller.market.pf.redPacket.queryPFRedPacketList.flow.QueryPFRedPacketListFlow;
import com.miller.market.pf.redPacket.queryPFRedPacketList.request.QueryPFRedPacketListRequestDTO;
import com.miller.market.pf.redPacket.queryPFRedPacketList.response.QueryPFRedPacketListResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.dal.entity.RedPacket;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * App端根据红包id过滤出生效红包
 */
@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQTWKPA6MCVT2DGTVDW36419", scenarioName = "APP-进入用户首页-融合App端根据红包id过滤出生效的PF红包：红包无效"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-根据红包id过滤出生效的PF红包：红包失效")
public class QueryPFRedPacketListInvalidScenarioTests {


    private static RedPacketMapper redPacketMapper;
    private static List<Long> redPacketIds = new ArrayList<>();
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        redPacketMapper = sqlSession.getMapper(RedPacketMapper.class);

        //查询全国通用的无效的通用红包
        QueryWrapper<RedPacket> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("residue_quantity", "1");
        queryWrapper.eq("valid_type", "2");
        queryWrapper.eq("red_packet_type", "1");
        queryWrapper.eq("status", "2");
        queryWrapper.eq("is_del", "0");
        //0全国通用红包
        queryWrapper.eq("portal_id","0");
        List<RedPacket> redPacketList = redPacketMapper.selectList(queryWrapper);
        Long redPacketId = redPacketList.get(0).getRedPacketId();
        redPacketIds.add(redPacketId);

    }

    @MethodSource("staticRedPacketDataProvider")
    @ParameterizedTest
    @DisplayName("PF融合_根据红包id过滤出生效的PF红包:红包失效")
    void exchangeRedPacketSuccessfully(QueryPFRedPacketListRequestDTO requestDTO) {
        QueryPFRedPacketListResponseDTO responseDTO = QueryPFRedPacketListFlow.getRedPacketCount(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(1);
        Assertions.assertThat(responseDTO.getData().getRedPacketList()).isEmpty();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticRedPacketDataProvider() {
        QueryPFRedPacketListRequestDTO requestDTO = new QueryPFRedPacketListRequestDTO();
        //以下均为必填字段
        requestDTO.setRedPacketIds(redPacketIds);
        requestDTO.setPortalId(3L);
        requestDTO.setLatitude("30.20111");
        requestDTO.setLongitude("120.22136");
        requestDTO.setLanguage("CN");
        return Stream.of(Arguments.of(requestDTO));
    }

}
