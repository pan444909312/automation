package com.miller.delivery.testcase.module.deliveryAdmin.driverLayer;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-编辑分层
 */
@Scenario(
        scenarioID = "01K602KND68S0MM79DN9N9S9T5",
        scenarioName = "司管-编辑分层",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("编辑分层")
public class DriverLayerUpdateTests {

    @DisplayName("编辑分层")
    @Test
    void shouldUpdateDriverLayer() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取分层列表，提取第一个配置ID
        Integer layerConfigId = getFirstLayerConfigId(token);

        // 3) 编辑分层
        if (layerConfigId != null) {
            updateDriverLayer(token, layerConfigId);
        }
    }

    @DisplayName("编辑分层-工作类型为空（错误场景）")
    @Test
    void shouldFailWhenWorkTypeListIsEmpty() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取分层列表，提取第一个配置ID
        Integer layerConfigId = getFirstLayerConfigId(token);

        // 3) 编辑分层-工作类型为空（应该返回错误）
        if (layerConfigId != null) {
            updateDriverLayerWithEmptyWorkType(token, layerConfigId);
        }
    }

    private Integer getFirstLayerConfigId(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityList\":[],\"workTypeList\":[],\"pageNo\":1,\"pageSize\":10}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        Object idObj = TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].id");
        return idObj != null ? Integer.valueOf(idObj.toString()) : null;
    }

    private void updateDriverLayer(String token, Integer layerConfigId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/update";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{"
                + "\"cityList\":[\"日照市\"],"
                + "\"workTypeList\":[0,1],"
                + "\"levelCount\":1,"
                + "\"levelRules\":[{"
                + "\"layerLevel\":\"D\","
                + "\"efficiencyMin\":1,"
                + "\"efficiencyMax\":2,"
                + "\"attendanceDaysMin\":2,"
                + "\"attendanceDaysMax\":3,"
                + "\"peakDaysMin\":4,"
                + "\"peakDaysMax\":5,"
                + "\"weekendHoursMin\":6,"
                + "\"weekendHoursMax\":9"
                + "}],"
                + "\"id\":%d"
                + "}", layerConfigId);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void updateDriverLayerWithEmptyWorkType(String token, Integer layerConfigId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/add";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{"
                + "\"cityList\":[\"日照市\"],"
                + "\"workTypeList\":[],"
                + "\"levelCount\":1,"
                + "\"levelRules\":[{"
                + "\"layerLevel\":\"D\","
                + "\"efficiencyMin\":1,"
                + "\"efficiencyMax\":2,"
                + "\"attendanceDaysMin\":2,"
                + "\"attendanceDaysMax\":3,"
                + "\"peakDaysMin\":4,"
                + "\"peakDaysMax\":5,"
                + "\"weekendHoursMin\":6,"
                + "\"weekendHoursMax\":9"
                + "}],"
                + "\"id\":%d"
                + "}", layerConfigId);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 错误场景：应该返回code=9999，且响应包含"请选择工作类型"
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Authorization", token);
        headers.put("Connection", "keep-alive");
        headers.put("Origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("Referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Site", "same-site");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");

        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

