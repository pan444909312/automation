package com.miller.delivery.testcase.module.deliveryAdmin.eta;

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
 * 开启/关闭配置
 *
 * @author 彭路路
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K6289ZS5KATCKX7KC4GN7YN7",
        scenarioName = "司管-开启ETA",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("开启/关闭配置")
public class EtaConfigToggleTests {

    // 注意：需要在实际使用时替换为真实的 eta_time_config_id
    private static final Long ETA_CONFIG_ID = 1L; // 请从质量平台或数据库中获取实际的 eta_time_config_id

    @DisplayName("开启ETA配置")
    @Test
    void shouldEnableEtaConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 开启ETA配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/changeStatus";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"id\":%d,\"openStatus\":1,\"isAutoClose\":0}", ETA_CONFIG_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 4) 从数据库验证配置已开启
        List<Map<String, Object>> configRecords = PandaTestDBHelpful.executeSelectListSql(
                "select * from panda_test.hp_delivery_eta_time_config where id=?",
                ETA_CONFIG_ID);
        
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到配置";
        Map<String, Object> configRecord = configRecords.get(0);
        Integer openStatus = ((Number) configRecord.get("open_status")).intValue();
        assert openStatus == 1 : "配置状态应为开启(1)，实际为: " + openStatus;
    }

    @DisplayName("关闭ETA配置")
    @Test
    void shouldDisableEtaConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 关闭ETA配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/changeStatus";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"id\":%d,\"openStatus\":0,\"isAutoClose\":0}", ETA_CONFIG_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 4) 从数据库验证配置已关闭
        List<Map<String, Object>> configRecords = PandaTestDBHelpful.executeSelectListSql(
                "select * from panda_test.hp_delivery_eta_time_config where id=?",
                ETA_CONFIG_ID);
        
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到配置";
        Map<String, Object> configRecord = configRecords.get(0);
        Integer openStatus = ((Number) configRecord.get("open_status")).intValue();
        assert openStatus == 0 : "配置状态应为关闭(0)，实际为: " + openStatus;
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
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

