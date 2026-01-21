package com.miller.delivery.testcase.module.deliveryAdmin.eta;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 编辑eta配置
 *
 * @author 彭路路
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K628956TQAJSTN5BEHZJ4EX5",
        scenarioName = "司管-编辑EAT",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("编辑eta配置")
public class EtaConfigEditTests {

    // 注意：需要在实际使用时替换为真实的 eta_time_config_id
    private static final Long ETA_CONFIG_ID = 1L; // 请从质量平台或数据库中获取实际的 eta_time_config_id
    private static final Long AREA_ID = 902L;
    private static final String CITY_ID = "729";

    @DisplayName("编辑ETA配置")
    @Test
    void shouldEditEtaConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当前时间戳用于配置名称
        long nowTime = System.currentTimeMillis();
        String configName = "自动化新增ETA配送方案" + nowTime;

        // 3) 编辑ETA配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/update";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format(
                "{\"name\":\"%s\",\"scope\":1,\"minDiff\":1,\"maxDiff\":3,\"specialTimeList\":[],\"shopList\":null,\"areaList\":[{\"configId\":%d,\"areaId\":%d,\"areaName\":\"青岛-商圈3\"}],\"areaIdList\":[%d],\"shopIdList\":null,\"id\":%d,\"openStatus\":0,\"cityId\":\"%s\",\"isDefault\":0,\"createTime\":1758856303014,\"updateTime\":0,\"lastUpdateUserId\":2182,\"lastUpdateUserName\":\"ding17058431144045523\"}",
                configName, ETA_CONFIG_ID, AREA_ID, AREA_ID, ETA_CONFIG_ID, CITY_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("编辑配置-未登记不可编辑")
    @Test
    void shouldNotEditEtaConfigWithoutAuth() {
        // 1) 使用无效token测试未授权场景
        String invalidToken = "33333";

        // 2) 尝试编辑ETA配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/update";
        String method = "POST";
        Map<String, Object> headers = createHeaders(invalidToken);
        long nowTime = System.currentTimeMillis();
        String configName = "自动化新增ETA配送方案" + nowTime;
        String body = String.format(
                "{\"name\":\"%s\",\"scope\":1,\"minDiff\":1,\"maxDiff\":3,\"specialTimeList\":[],\"shopList\":null,\"areaList\":[{\"configId\":%d,\"areaId\":%d,\"areaName\":\"青岛-商圈3\"}],\"areaIdList\":[%d],\"shopIdList\":null,\"id\":%d,\"openStatus\":0,\"cityId\":\"%s\",\"isDefault\":0,\"createTime\":1758856303014,\"updateTime\":0,\"lastUpdateUserId\":2182,\"lastUpdateUserName\":\"ding17058431144045523\"}",
                configName, ETA_CONFIG_ID, AREA_ID, AREA_ID, ETA_CONFIG_ID, CITY_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言未授权错误
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(10015);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("没有该系统登陆权限");
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

