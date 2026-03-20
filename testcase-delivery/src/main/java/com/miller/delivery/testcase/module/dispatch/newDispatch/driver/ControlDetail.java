package com.miller.delivery.testcase.module.dispatch.newDispatch.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 新调度- 骑手管控详情
 */
@Scenario(
        scenarioID = "01KKD7SJ70E0DP982KW003Y7KX",
        scenarioName = "新调度-骑手管控详情",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新调度- 骑手管控详情")
public class ControlDetail {

    @DisplayName("新调度- 骑手管控详情")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        System.out.println("获取到的 token: " + token);
        System.out.println("token 长度: " + (token != null ? token.length() : "null"));
        unDispatchList(token);
    }
    //
    public  void  unDispatchList(String token) {

        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/control/detail";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", token);
        String body = "{\n" +
                "    \"driverId\": 1398723719\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }

}
