package com.miller.userapp.module.home.index.common;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.dto.user.ItemKeyDTO;
import com.hungrypanda.app.server.vo.login.AppleLoginShowVO;
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

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCH",
        scenarioName = "根据key值查询是否展示成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/itemKey/show")
public class UserItemKeyShowSuccess {

    /**
     * 接口_根据key值查询是否展示
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/itemKey/show";

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("根据key值查询是否展示成功")
    void testCase(ItemKeyDTO itemKeyDTO) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(itemKeyDTO), null, Result.class);
        AppleLoginShowVO appleLoginShowVO = JSON.parseObject(result.getResult().toString(), AppleLoginShowVO.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(appleLoginShowVO.getShow()).isTrue();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ItemKeyDTO itemKeyDTO = new ItemKeyDTO();
        itemKeyDTO.setItemKey("MEMBER_COUNTRY_SHARE_CONFIG");


        return Stream.of(Arguments.of(itemKeyDTO));
    }

}
