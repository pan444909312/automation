package com.miller.delivery.testcase.module.deliveryAdmin.DeliveryAreaSettings.eta;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 删除eta配置
 *
 * @author 彭路路
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K628BV8TBYZ7Q8FFC33T7Z38",
        scenarioName = "司管-删除eta新",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("删除eta配置")
public class EtaConfigDeleteTests {

    // 注意：需要在实际使用时替换为真实的 eta_time_config_id
    private static final Long ETA_TIME_CONFIG_ID = 1L; // 请从质量平台或数据库中获取实际的 eta_time_config_id

    @DisplayName("删除eta配置")
    @Test
    void shouldDeleteEtaConfig() {
        EtaConfigAddTests eta= new EtaConfigAddTests();
        int configId = eta.configId();
        deleteETA(configId);
    }

    public void deleteETA( int configId) {
        // 1) 司管登录获取 token
        String token = erpLogin();


        // 2) 删除eta配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/del";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"id\":%d}", configId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 4) 从数据库验证配置已删除（is_del=1）
        List<Map<String, Object>> configRecords = PandaTestDBHelpful.executeSelectListSql(
                "select * from panda_test.hp_delivery_eta_time_config where id=?",
                configId);

        if (configRecords != null && !configRecords.isEmpty()) {
            Map<String, Object> configRecord = configRecords.get(0);
            Integer isDel = ((Number) configRecord.get("is_del")).intValue();
            assert isDel == 1 : "数据库中的配置未正确标记为已删除";
        }
    }

    @DisplayName("删除eta配置-已删除的数据不可再删除")
    @Test
    void shouldNotDeleteAlreadyDeletedEtaConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 尝试删除已删除的配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/del";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"id\":%d}", ETA_TIME_CONFIG_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(10022);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("数据不存在");
    }

    @DisplayName("删除eta配置-删除不存在的配置")
    @Test
    void shouldNotDeleteNonExistentEtaConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 尝试删除不存在的配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/del";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"id\":111111111111}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(10022);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("数据不存在");
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

