package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 冲单奖单单奖详情（未完成，h5验签）
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KG4FJNG5JHWVN50RFH0CTYG9",
        scenarioName = "骑手app-新人专区",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("冲单奖单单奖详情")
public class ChongdanActivityDetailInfoTests {

    // 注意：需要在实际使用时替换为真实的 activityDetailId
    private static final String ACTIVITY_DETAIL_ID = "11458"; // 请从质量平台或实际业务中获取

    @DisplayName("冲单奖单单奖详情")
    @Test
    void shouldGetChongdanActivityDetailInfo() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010526", "Test1234");

        // 2) 冲单奖单单奖详情（H5验签）
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/activity/activityDetailInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        // 构建H5验签请求体
        String body = buildH5SignBody(driverAccessToken);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 构建H5验签请求体
     */
    private String buildH5SignBody(String authorization) {
        long timestamp = System.currentTimeMillis();
        String nonce = "U38pajmYVc8VEe0gWWxPaouK1";
        String deviceId = "7be03e9a3de30fd";
        
        // 构建ph对象
        String ph = String.format(
                "{\"locale\":\"zh-CN\",\"brand\":\"samsung+SM-G9600\",\"model\":\"samsung+SM-G9600\"," +
                "\"version\":\"8.10.1\",\"appVersion\":\"5.67.6\",\"longitude\":\"\",\"latitude\":\"\"," +
                "\"platform\":\"WEB_ANDROID\",\"appTypeId\":\"2\",\"uniquetoken\":\"39ff68a85a154f96a84c346e35166f3b\"," +
                "\"authorization\":\"%s\",\"language\":\"zh-CN\",\"service-route\":\"delivery\"}",
                authorization);
        
        // 构建pd对象
        String pd = String.format("{\"activityDetailId\":\"%s\"}", ACTIVITY_DETAIL_ID);
        
        // 构建完整请求体
        return String.format(
                "{\"pm\":\"POST\",\"ph\":%s,\"pd\":%s,\"nv\":\"2\",\"nt\":\"%d\",\"nn\":\"%s\",\"nd\":\"%s\"}",
                ph, pd, timestamp, nonce, deviceId);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.21674955924935");
        headers.put("latitude", "30.20344076263413");
        headers.put("version", "5.56.1");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

