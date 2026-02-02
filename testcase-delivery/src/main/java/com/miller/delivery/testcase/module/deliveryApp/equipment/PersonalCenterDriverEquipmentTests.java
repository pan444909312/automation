package com.miller.delivery.testcase.module.deliveryApp.equipment;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心-骑手装备
 *
 * Apifox: docs/d-apifox/个人中心-骑手装备.apifox-cli.json
 */
@Scenario(
        scenarioID = "01KGEK54NMRMD6GA2Z26ZAMTGT",
        scenarioName = "个人中心-骑手装备",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("个人中心-骑手装备")
public class PersonalCenterDriverEquipmentTests {

    @DisplayName("装备申请&撤销-广告装备+周边装备（按Apifox步骤）")
    @Test
    void shouldApplyAndCancelEquipments() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 1) 装备管理-申请类型页面
        equipmentList(driverAccessToken);

        // 2) 广告装备：equipmentInfo -> submit -> claimInfo -> cancel
        String adConfigNo = equipmentInfo(driverAccessToken, 1);
        submit(driverAccessToken, adConfigNo, 810);
        String adClaimNo = claimInfo(driverAccessToken, 1);
        cancel(driverAccessToken, adClaimNo);

        // 3) 周边装备：equipmentInfo -> submit -> claimInfo -> cancel
        String peripheralConfigNo = equipmentInfo(driverAccessToken, 0);
        submit(driverAccessToken, peripheralConfigNo, 808);
        String peripheralClaimNo = claimInfo(driverAccessToken, 0);
        cancel(driverAccessToken, peripheralClaimNo);
    }

    private void equipmentList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/equipmentAdmin/list";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private String equipmentInfo(String driverAccessToken, int equipmentType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/equipmentAdmin/equipmentInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"equipmentType\":%d}", equipmentType);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);

        // apifox 提取：result.configNo
        return TestCaseHelpful.extractValue(responseBody, "$.result.configNo").toString();
    }

    private void submit(String driverAccessToken, String configNo, int addressId) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/equipmentAdmin/submit";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"configNo\":\"%s\",\"addressId\":%d}", configNo, addressId);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private String claimInfo(String driverAccessToken, int equipmentType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/equipmentAdmin/claimInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"equipmentType\":%d}", equipmentType);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);

        // apifox 提取：$.result.claimNo
        return TestCaseHelpful.extractValue(responseBody, "$.result.claimNo").toString();
    }

    private void cancel(String driverAccessToken, String claimNo) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/equipmentAdmin/cancel";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"claimNo\":\"%s\"}", claimNo);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216775");
        headers.put("latitude", "30.203361");
        headers.put("version", "5.64.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

