package com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.riderOnboardingConfig;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 骑手入驻配置-新增配置-删除配置
 *
 * @author 江彪
 * @version 2.0
 * @since 2026/02/28
 */
@Scenario(
        scenarioID = "01KJHR29AZDSJERHHQMX8GFCGW",
        scenarioName = "骑手入驻配置-新增配置-删除配置",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("骑手入驻配置-新增配置-删除配置")
public class riderOnboardingConfigCreateTests {

    @DisplayName("骑手入驻配置-新增配置-删除配置")
    @Test
    void riderOnboardingConfigCreate() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增宣城市推送
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driver/defaultConfig/addAndUpdateConfig";
        String body = "{\"automaticOrderReceivingShow\":1,\"automaticOrderReceiving\":1,\"city\":\"宣城市\",\"isCanTakeOrder\":1,\"isTipShow\":1,\"maxReceiving\":1,\"outAreaOnline\":0,\"workType\":1,\"canInsteadOrder\":0,\"id\":null}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 4) 删除宣城市推送
        String delUrl = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driver/defaultConfig/delConfig";
        String delBody = "{\"city\":\"宣城市\",\"isDel\":1}";
        var delResponseBody = TestCaseHelpful.sendRequest(method, delUrl, null, headers, delBody);

        // 5) 断言
        TestCaseHelpful.assertThatJson(delResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }


    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

