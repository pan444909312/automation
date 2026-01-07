package com.miller.delivery.testcase.module.deliveryAdmin.urgeMeal;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Scenario(
        scenarioID = "01JPSS2M3G3V3CJGDZWM8BQV58",
        scenarioName = "司管后台-订单管理-催骑手取餐-新增",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("新增催骑手取餐配置")
public class UrgeMealConfigAddTests {

    @DisplayName("新增催骑手取餐配置")
    @Test
    void shouldAddUrgeMealConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/urgeMeal/add";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\r\n" +
                "    \"nonOnShopTriggerTime\": 0,\r\n" +
                "    \"nonOnShopLocationCheckDistance\": 100,\r\n" +
                "    \"triggerNumOneLevel\": 3,\r\n" +
                "    \"earlyOnShopTime\": 5,\r\n" +
                "    \"afterOnShopNonOutTime\": 6,\r\n" +
                "    \"thirdUrge\": 0,\r\n" +
                "    \"oneLevelPushType\": 0,\r\n" +
                "    \"twoLevelPushType\": 1,\r\n" +
                "    \"triggerNumTwoLevel\": 10,\r\n" +
                "    \"toManual\": 1,\r\n" +
                "    \"thirdUrgeInfo\": {\r\n" +
                "        \"onShopTime\": 0,\r\n" +
                "        \"onCustomerTime\": 0\r\n" +
                "    },\r\n" +
                "    \"city\": \"杭州市\"\r\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 4) 从数据库查询新增的配置
        Map<String, Object> configRecord = PandaTestDBHelpful.executeSelectOneSql(
                "select * from panda_test.hp_delivery_urge_meal_config where city='杭州市' and is_del=0 order by id desc limit 1");
        assert configRecord != null : "数据库中没有找到新增的配置";
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
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

