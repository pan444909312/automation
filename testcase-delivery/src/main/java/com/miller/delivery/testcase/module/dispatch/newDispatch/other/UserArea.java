package com.miller.delivery.testcase.module.dispatch.newDispatch.other;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 新调度- 运力数据
 */
@Scenario(
        scenarioID = "01KK146XXBKQPSHPYNW82S0DFE",
        scenarioName = "新调度-运力数据",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新调度-用户区域")
public class UserArea {

    @DisplayName("新调度-用户区域")
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

        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/userArea/popList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", token);
        String body = "{\n" +
                "    \"cityName\": \"杭州市\",\n" +
                "    \"orderBusinessType\": \"0\"\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }

}
