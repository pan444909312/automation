package com.miller.userapp.module.home.search;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.suggest.SuggestSearchRep;
import com.hungrypanda.app.server.api.res.suggest.SuggestResp;
import com.hungrypanda.app.server.common.result.Result;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCK",
        scenarioName = "搜索关联词成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/suggest/search")
public class UserSuggestSearchSuccess {

    /**
     * 接口_搜索关联词
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/suggest/search";

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("搜索关联词成功")
    void testCase(SuggestSearchRep searchRep) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(searchRep), null, Result.class);
        SuggestResp suggestResp = JSON.parseObject(result.getResult().toString(), SuggestResp.class);
        SuggestResp.SuggestVO suggestVO = suggestResp.getSuggests().stream().filter(item -> item.getWord().equals("炒饭")).findFirst().orElse(null);
        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(suggestVO).isNotNull();
        assertThat(suggestVO.getWord()).isEqualTo("炒饭");

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        SuggestSearchRep searchRep = new SuggestSearchRep();

        searchRep.setCity("九江市");
        searchRep.setKeywords("炒");
        return Stream.of(Arguments.of(searchRep));
    }

}
