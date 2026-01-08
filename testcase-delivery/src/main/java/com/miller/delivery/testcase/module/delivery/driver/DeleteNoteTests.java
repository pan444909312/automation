package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-地图笔记-删除笔记
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JPP88W58JPV5X0TA4F8HKN3Q",
        scenarioName = "骑手app-地图笔记-删除笔记",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("骑手app-地图笔记-删除笔记")
public class DeleteNoteTests {

    @DisplayName("删除笔记")
    @Test
    void shouldDeleteNote() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 删除笔记
        // 注意：noteId需要从实际场景中获取，这里使用占位符
        Long noteId = 123456L; // 占位符，实际使用时需要从创建笔记的响应中获取
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/note/delMapNote";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"noteId\":%d}", noteId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.2168189");
        headers.put("latitude", "30.2033227");
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
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

