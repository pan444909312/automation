package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传骑手经纬度
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K4C0439DK13A6S8VBHH1GP1G",
        scenarioName = "骑手上传经纬度",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("上传骑手经纬度")
public class SyncGpsTests {

    @DisplayName("上传经纬度")
    @Test
    void shouldSyncGps() {
        // 1) 骑手登录获取 token（注意：这个用例使用的账号是13251012003）
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13251012003", "Test1234");

        // 2) 上传经纬度
        syncGps(driverAccessToken, "120.216893", "30.203568");
    }

    @DisplayName("上传经纬度-15米")
    @Test
    void shouldSyncGps15Meters() throws InterruptedException {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13251012003", "Test1234");

        // 2) 上传经纬度
        syncGps(driverAccessToken, "120.216893", "30.203568");

        // 3) 延迟5秒
        Thread.sleep(5000);

        // 4) 上传经纬度-15米（latitude变化）
        syncGps(driverAccessToken, "120.216893", "30.216068");
    }

    @DisplayName("上传经纬度-超过1500km")
    @Test
    void shouldSyncGpsOver1500km() throws InterruptedException {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13251012003", "Test1234");

        // 2) 上传经纬度
        syncGps(driverAccessToken, "120.216893", "30.203568");

        // 3) 延迟5秒
        Thread.sleep(5000);

        // 4) 上传经纬度-15米
        syncGps(driverAccessToken, "120.216893", "30.216068");

        // 5) 延迟5秒
        Thread.sleep(5000);

        // 6) 上传经纬度-超过1500km（longitude和latitude都变化）
        syncGps(driverAccessToken, "119.985936", "30.154554");
    }

    /**
     * 上传经纬度
     */
    private void syncGps(String driverAccessToken, String longitude, String latitude) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/home/syncGps";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders(longitude, latitude);
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头（IOS平台）
     */
    private Map<String, Object> createDriverAppHeaders(String longitude, String latitude) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone X");
        headers.put("latitude", latitude);
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.74.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "BFEC8953-4F04-4AAC-9C06-7EB2C8CA1411");
        headers.put("longitude", longitude);
        headers.put("accept-language", "zh-Hans-GB;q=1, en-GB;q=0.9, ja-GB;q=0.8, en-AU;q=0.7");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");
        headers.put("accept", "*/*");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json");
        return headers;
    }
}

