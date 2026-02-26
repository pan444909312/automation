package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.punish;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-新增骑手禁止上线配置
 */
@Scenario(
        scenarioID = "01KDQN7QFSYBVRMA82VZ12H1WM",
        scenarioName = "新增骑手禁止上线配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新增骑手禁止上线配置")
public class AppealConfigAddNewTests {

    @DisplayName("新增骑手禁止上线配置")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        String add = add(token);
        AppealConfigDeleteTests punishAppealConfigDeleteTests = new AppealConfigDeleteTests();
        punishAppealConfigDeleteTests.delete(token,add);
    }

    public String add(String token) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/punishAppealContentConfig/save";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"cityList\": [\n" +
                "        \"九江市\"\n" +
                "    ],\n" +
                "    \"canAppeal\": 0,\n" +
                "    \"controlReason\": 0,\n" +
                "    \"controlAudit\": 0,\n" +
                "    \"personRemind\": 0,\n" +
                "    \"controlContent\": \"自动化配置\",\n" +
                "    \"controlContentEn\": \"自动化配置\"\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        // 5) 从数据库查询新增的配置
        Map<String, Object> configRecords = PandaTestDBHelpful.executeSelectOneSql(
                "select * from panda_test.hp_delivery_punish_appeal_content_config_city where city='九江市'   order by id desc limit 1\n");
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到新增的配置";


        String configNo = (configRecords.get("config_no")).toString();
        return configNo;

    }

}

