package com.miller.delivery.testcase.module.deliveryAdmin.salary;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KDQN8K9PTJCW6WWZXWM4SV2M",
        scenarioName = "薪资管理-全部导出",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取骑手个人薪资详情页")
public class DriverSalaryDetailTests {

    @DisplayName("获取骑手个人薪资详情页")
    @Test
    void shouldGetDriverSalaryDetail() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手个人薪资详情页
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/salary/manager/getDriverSalaryListInfo";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"driverId\":\"1398721882\",\"beginDate\":\"2025-12-23\",\"endDate\":\"2025-12-29\",\"city\":\"杭州市\",\"receiptTimeStart\":null,\"receiptTimeEnd\":null,\"deliveryTimeStart\":null,\"deliveryTimeEnd\":null}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        // 验证返回包含"薪资总额"
        TestCaseHelpful.assertThatJson(responseBody)
                .node("data").isNotNull();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

