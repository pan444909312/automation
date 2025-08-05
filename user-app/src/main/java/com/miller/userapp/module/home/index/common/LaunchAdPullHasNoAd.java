package com.miller.userapp.module.home.index.common;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.api.req.launchAd.PullLaunchAdReq;
import com.hungrypanda.app.server.api.res.launchAd.LaunchAdDetail;
import com.hungrypanda.app.server.api.res.launchAd.PullLaunchAdRes;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.entity.ad.LaunchAdEntity;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.mapper.home.LaunchAdMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.util.RequestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01K1WBNXXETNF8499810BSBW97",
        scenarioName = "拉取启动页广告-未配置广告数据不返回",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/user/v1/launchAd/pull")
public class LaunchAdPullHasNoAd {

    /**
     * 接口_启动页广告
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v1/launchAd/pull";

    private LaunchAdMapper launchAdMapper;

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        launchAdMapper = sqlSession.getMapper(LaunchAdMapper.class);
        launchAdMapper.update(new UpdateWrapper<LaunchAdEntity>().eq("la_id",232).set("state",0));
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("拉取启动页广告-未配置广告数据不返回")
    void testCase(PullLaunchAdReq pullLaunchAdReq ) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(pullLaunchAdReq), null, Result.class);
        PullLaunchAdRes pullLaunchAdRes = JSON.parseObject(result.getResult().toString(),PullLaunchAdRes.class);

        int size = pullLaunchAdRes.getLaunchAdList().size();

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(size).isEqualTo(0);
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        PullLaunchAdReq pullLaunchAdReq = new PullLaunchAdReq();
        pullLaunchAdReq.setCityName("九江市");
        pullLaunchAdReq.setLastUpdateTime(0L);


        return Stream.of(Arguments.of(pullLaunchAdReq));
    }

}
