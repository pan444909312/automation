package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-地图笔记-我的笔记
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JPP89GD31CZ5Q34NRCZQDW77",
        scenarioName = "骑手app-地图笔记-我的笔记",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("骑手app-地图笔记-我的笔记")
public class MyNoteTests {

    @DisplayName("我的笔记-列表页，数据获取")
    @Test
    void shouldGetMyNoteList() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/note/myNoteKeep";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("Authorization", driverAccessToken);
        
        String body = "{\"sortType\":1,\"sortDesc\":0,\"noteKeepType\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("我的笔记-地图页，获取数据")
    @Test
    void shouldGetMyNoteMapData() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/note/mapNoteArea";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("Authorization", driverAccessToken);
        
        String body = "{\"noteKeepType\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isNotNull();
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "app-deliverytest.hungrypanda.cn");
        headers.put("longitude", "120.2167995");
        headers.put("latitude", "30.2032503");
        headers.put("version", "5.59.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("user-agent", "5.59.0");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "samsung");
        headers.put("uniquetoken", "34ea70ca94766bbc");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}
