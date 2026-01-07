package com.miller.delivery.testcase.module.deliveryAdmin.driverGroup;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01JPSS3T86N2SS249YZ4YDFPPJ",
        scenarioName = "司管后台-订单管理-催骑手取餐-修改骑手群组",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("修改骑手群组")
public class DriverGroupUpdateTests {

    @DisplayName("修改骑手群组")
    @Test
    void shouldUpdateDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取已选择的群组
        String uri1 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/urgeMeal/driverGroupInfo";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = "{\"configNo\":\"472586939537845376\"}";
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri1, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("收到请求");

        // 3) 获取所有群组列表
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverGroup/getDriverGroupAllList";
        String body2 = "{\"groupName\":\"\",\"groupId\":\"\",\"cityList\":[\"杭州市\"]}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Not(A:Brand\";v=\"99\", \"Google Chrome\";v=\"133\", \"Chromium\";v=\"133\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

