package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.cultivate;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-删除培训内容
 */
@Scenario(
        scenarioID = "01KDSR15WSDPPRKGD523H8X58M",
        scenarioName = "骑手列表-删除培训内容",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("删除培训内容")
public class CultivateDeleteTests {

    @DisplayName("删除培训内容")
    @Test
    void shouldDeleteCultivate() {
        // 1) 前置动作：可删除数据准备；城市：宣城市
        updateCuleivateData();

        // 2) 司管登录获取 token
        String token = erpLogin();

        // 3) 获取培训内容列表，提取 cultivateCode
        String cultivateCode = getCultivateCode(token);

        // 4) 删除培训内容
        deleteCultivate(token, cultivateCode);
    }

    private void updateCuleivateData(){
        String milliTimestamp = String.valueOf(Instant.now().toEpochMilli());
        String sql = String.format("update hp_delivery_cultivate set is_del = 0, is_enable = 0, update_time = %s where cultivate_code = \"420191646312577248\";",milliTimestamp);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
    }

    private String getCultivateCode(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/cultivate/cultivatePage";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"pageNo\":1,\"pageSize\":10,\"cityNameList\":[],\"cultivateName\":\"\",\"isEnable\":\"\",\"applyLanguageType\":\"\",\"vehicleTypeList\":[],\"cityNameList\":[\"宣城市\"]}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].cultivateCode").toString();
    }

    private void deleteCultivate(String token, String cultivateCode) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/cultivate/delCultivate";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"cultivateCode\":\"%s\"}", cultivateCode);

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
        headers.put("priority", "u=1, i");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

