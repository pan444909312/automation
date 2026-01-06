package com.miller.delivery.testcase.module.deliveryAdmin.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-获取列表数据
 */
@Scenario(
        scenarioID = "01JNNE6CKKGMB6N5ZDAPCY5YXH",
        scenarioName = "【主干场景】司管后台-骑手列表-获取列表数据",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("获取列表数据")
public class DriverListDataTests {

    @DisplayName("获取骑手列表")
    @Test
    void shouldGetDriverList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手列表
        getDriverList(token);
    }

    private void getDriverList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{"
                + "\"whatsApp\":null,"
                + "\"userStatus\":null,"
                + "\"city\":null,"
                + "\"receivingType\":null,"
                + "\"driverCode\":null,"
                + "\"driverEquipmentType\":null,"
                + "\"areaList\":[],"
                + "\"dateStart\":null,"
                + "\"dateEnd\":null,"
                + "\"runType\":null,"
                + "\"source\":null,"
                + "\"online\":null,"
                + "\"certificateExpireType\":null,"
                + "\"driverBusinessTypeList\":[],"
                + "\"tag\":null,"
                + "\"userId\":null,"
                + "\"userName\":null,"
                + "\"telephone\":null,"
                + "\"pageNo\":1,"
                + "\"pageSize\":10,"
                + "\"canInsteadOrder\":null,"
                + "\"languageCode\":[],"
                + "\"workType\":null,"
                + "\"workDate\":null,"
                + "\"workTime\":[],"
                + "\"workHour\":null,"
                + "\"siteIdList\":[]"
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Not(A:Brand\";v=\"99\", \"Google Chrome\";v=\"133\", \"Chromium\";v=\"133\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

