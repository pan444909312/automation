package com.miller.delivery.testcase.module.deliveryAdmin.control;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手管控-新增管控并启用/禁用/删除
 */
@Scenario(
        scenarioID = "01KDQCRK7SBEN91WZ0QSCAQ8PD",
        scenarioName = "【主干场景】司管后台-骑手管控-新增管控并启用禁用删除",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("管控-新增启用禁用删除")
public class ControlRuleLifecycleTests {

    @DisplayName("新增管控并启用/禁用/删除")
    @Test
    void shouldCreateEnableDisableDeleteControlRule() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增管控规则，获取 controlNo
        String controlNo = createControlRule(token);

        // 3) 获取管控列表校验
        fetchControlList(token);

        // 4) 查看管控详情
        viewControlDetail(token, controlNo);

        // 5) 启用管控
        publishControl(token, controlNo);

        // 6) 禁用管控
        disableControl(token, controlNo);

        // 7) 删除管控规则（使用示例 controlNo）
        deleteControl(token, "501483950317137056");
    }

    private String createControlRule(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/saveControl";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        String body = "{"
                + "\"cityNameList\": [\"奥克兰\"],"
                + "\"controlType\": 0,"
                + "\"areaScopeType\": 0,"
                + "\"overType\": 0,"
                + "\"gradeRuleList\": [{"
                + "  \"gradeType\": \"1\","
                + "  \"indicatorsRuleList\": [{"
                + "    \"checkedGroupIdList\": [1858],"
                + "    \"driverType\": 1,"
                + "    \"excludeGroupIdList\": [],"
                + "    \"priorityLevel\": 1,"
                + "    \"punishTime\": 1,"
                + "    \"punishType\": 1,"
                + "    \"relieveType\": 0,"
                + "    \"rulePunishList\": [{"
                + "      \"punish\": {\"code\": 10, \"type\": 2, \"desc\": \"增加背单量\", \"realControlType\": 0, \"thanType\": 0},"
                + "      \"punishAction\": 10,"
                + "      \"punishValue\": 1,"
                + "      \"warningCnTip\": \"\","
                + "      \"warningRegionTip\": \"\","
                + "      \"maxNum\": null,"
                + "      \"minNum\": 1,"
                + "      \"limitType\": 0,"
                + "      \"limitValue\": 2,"
                + "      \"limitList\": [{\"driverLayerLevel\":\"D\",\"limitValue\":null},{\"driverLayerLevel\":\"C\",\"limitValue\":null},{\"driverLayerLevel\":\"B\",\"limitValue\":null},{\"driverLayerLevel\":\"A\",\"limitValue\":null},{\"driverLayerLevel\":\"S\",\"limitValue\":null}]"
                + "    }],"
                + "    \"indicatorsList\": [{"
                + "      \"code\": 6,"
                + "      \"type\": 1,"
                + "      \"desc\": \"拒单数\","
                + "      \"realControlType\": 1,"
                + "      \"thanType\": 0,"
                + "      \"startValue\": 1,"
                + "      \"endValue\": 2,"
                + "      \"indicatorType\": 6"
                + "    }]"
                + "  }]"
                + "}],"
                + "\"indicatorsTypeList\": [6]"
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.controlNo").toString();
    }

    private void fetchControlList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/page";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{"
                + "\"controlStatus\": \"\","
                + "\"indicatorsType\": \"\","
                + "\"controlNo\": \"\","
                + "\"cityNameList\": [],"
                + "\"orderAreaIdList\": [],"
                + "\"pageNo\": 1,"
                + "\"pageSize\": 15"
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void viewControlDetail(String token, String controlNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"controlNo\":\"%s\"}", controlNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void publishControl(String token, String controlNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/publish";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"controlNo\":\"%s\"}", controlNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void disableControl(String token, String controlNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/disable";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"controlNo\":\"%s\"}", controlNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void deleteControl(String token, String controlNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/del";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"controlNo\":\"%s\"}", controlNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 司管后台登录并返回 token
     */
    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");

        String body = "{"
                + "\"password\": \"d9501f93554734ba83d19c9dc83ef4fb\","
                + "\"userName\": \"ding023660390221528503\""
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

