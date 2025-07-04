package com.miller.userapp.module.home.popup.member;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.api.req.popup.RedPacketPopupReq;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.entity.member.MemberCityCrowdPricePopupRecordEntity;
import com.miller.common.util.MD5Util;
import com.miller.common.util.TimestampUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.mapper.member.MemberCityCrowdPricePopupRecordMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCZ",
        scenarioName = "首页会员浮窗今日已弹出则今日不再下发",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("/api/app/user/popup/redpacket")
@Slf4j
public class MemberIndexPopupTodayHasAlreadyPop {

    /**
     * 接口_首页弹窗接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/popup/redpacket";

    private UserLoginRequestDTO userLoginRequestDTO;

    private SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
    private MemberCityCrowdPricePopupRecordMapper memberPopupMapper;

    private String userId = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user04.account.id");

    @BeforeAll
    void beforeAll() {
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user04.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user04.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
        RequestUtils.getHeaders().remove("cityName");

        memberPopupMapper = sqlSession.getMapper(MemberCityCrowdPricePopupRecordMapper.class);
        resetLastPopupTime(userId);


    }

    @AfterAll
    void afterAll() {

        resetLastPopupTime(userId);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("首页会员浮窗今日已弹出则今日不再下发")
    void testCase(RedPacketPopupReq redPacketPopupReq) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(redPacketPopupReq), null, Result.class);


        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(result.getResult()).isNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        RedPacketPopupReq redPacketPopupReq = new RedPacketPopupReq();

        redPacketPopupReq.setType(0);
        redPacketPopupReq.setSceneType(0);
        redPacketPopupReq.setRedPacketGroupPopupTimes(0);

        return Stream.of(Arguments.of(redPacketPopupReq));
    }

    private void resetLastPopupTime(String userId) {

        // 查最近的 弹窗时间
        MemberCityCrowdPricePopupRecordEntity memberCityCrowdPricePopupRecordEntity = memberPopupMapper.selectOne(new QueryWrapper<MemberCityCrowdPricePopupRecordEntity>()
                .eq("user_id", userId)
                .orderByDesc("id")
                .last("limit 1"));
        if (memberCityCrowdPricePopupRecordEntity == null) {
            return;
        }
        Long lastPopupTime = memberCityCrowdPricePopupRecordEntity.getLastPopupTime();
        // 如果最后弹出时间大于等于当前时间，则将最后弹窗时间设置成历史的一天
        if (lastPopupTime <= TimestampUtils.getMidnightTimestamp(System.currentTimeMillis()) ) {
            log.info("userId={},重置弹出时间", userId);
            memberCityCrowdPricePopupRecordEntity.setLastPopupTime(System.currentTimeMillis());
            memberPopupMapper.updateById(memberCityCrowdPricePopupRecordEntity);
        }

    }

}
