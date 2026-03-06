package com.miller.delivery.testcase.module.dispatch.newDispatch.plan;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 新调度-订单详情-路径规划
 */
@Scenario(
        scenarioID = "01KK17X3YD164PMT92ZQ1M01DP",
        scenarioName = "新调度-订单详情-路径规划",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新调度-订单详情-路径规划")
public class RoutePlan {
    @DisplayName("新调度-订单详情-路径规划")
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

        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/map/route/plan";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", token);
        String body = "{\n" +
                "    \"point\": [\n" +
                "        {\n" +
                "            \"fromPoint\": {\n" +
                "                \"lat\": 30.20344913005829,\n" +
                "                \"lon\": 120.21696746349335\n" +
                "            },\n" +
                "            \"toPoint\": {\n" +
                "                \"lat\": 30.206596,\n" +
                "                \"lon\": 120.218072\n" +
                "            },\n" +
                "            \"profile\": \"car\",\n" +
                "            \"orderSn\": \"3646218809167717511254\",\n" +
                "            \"type\": 1\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }

}
