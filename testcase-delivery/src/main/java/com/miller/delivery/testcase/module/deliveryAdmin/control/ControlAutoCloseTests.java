package com.miller.delivery.testcase.module.deliveryAdmin.control;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手处罚列表-新增解除并查看处罚
 */
@Scenario(
        scenarioID = "01KDQB71WJD6YVV0QCP6GR8X8Y",
        scenarioName = "骑手处罚列表-新增解除并查看处罚",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("生成管控-关闭自动接单")
public class ControlAutoCloseTests {

    @DisplayName("生成处罚-关闭自动接单")
    @Test
    void shouldCreateAutoClosePunish() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成处罚-关闭自动接单
        createAutoClosePunish(token);

        // 3) 获取处罚措施punishNo
        String actualPunishNo = getPunishNo(token);

        // 4) 解除当前处罚
        if (actualPunishNo != null && !actualPunishNo.isEmpty()) {
            relievePunish(token, actualPunishNo);
        }

        // 5) 查看解除原因
        if (actualPunishNo != null && !actualPunishNo.isEmpty()) {
            viewRelieveDetail(token, actualPunishNo);
        }

        // 6) 查看管控规则
        if (actualPunishNo != null && !actualPunishNo.isEmpty()) {
            viewControlRule(token, actualPunishNo);
        }
    }

    private String createAutoClosePunish(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish/save";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{"
                + "\"controlReason\":6,"
                + "\"driverIdList\":[1398714150],"
                + "\"punishRemark\":\"测试\","
                + "\"punishTime\":0.1,"
                + "\"punishType\":1,"
                + "\"rulePunishList\":[{"
                + "\"punish\":{"
                + "\"code\":6,"
                + "\"type\":3,"
                + "\"desc\":\"关闭自动接单\","
                + "\"realControlType\":0,"
                + "\"thanType\":0"
                + "},"
                + "\"punishAction\":6,"
                + "\"punishValue\":null,"
                + "\"warningCnTip\":\"\","
                + "\"warningRegionTip\":\"\""
                + "}]"
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return null; // 这个接口可能不返回punishNo
    }

    private String getPunishNo(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{"
                + "\"dateRange\":[],"
                + "\"operatorTypeList\":[],"
                + "\"cityNameList\":[],"
                + "\"controlReason\":[],"
                + "\"driverId\":null,"
                + "\"driverName\":\"\","
                + "\"driverTel\":\"19539027924\","
                + "\"gradeTypeList\":[],"
                + "\"punishActionList\":[6],"
                + "\"punishStatusList\":[],"
                + "\"driverBusinessType\":null,"
                + "\"punishNo\":null,"
                + "\"pageNo\":1,"
                + "\"pageSize\":15"
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        Object punishNoObj = TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].punishNo");
        return punishNoObj != null ? punishNoObj.toString() : null;
    }

    private void relievePunish(String token, String punishNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish/relieve";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"relieveRemark\":\"解除\",\"relieveType\":1,\"punishNo\":\"%s\"}", punishNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void viewRelieveDetail(String token, String punishNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish/relieve/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"punishNo\":\"%s\"}", punishNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void viewControlRule(String token, String punishNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish/rule/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"punishNo\":\"%s\"}", punishNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("h5xxx", "1754297095626_request_new");
        headers.put("priority", "u=1, i");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

