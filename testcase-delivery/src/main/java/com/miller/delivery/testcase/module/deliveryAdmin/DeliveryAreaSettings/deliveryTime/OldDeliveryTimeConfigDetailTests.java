package com.miller.delivery.testcase.module.deliveryAdmin.DeliveryAreaSettings.deliveryTime;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 查看详情页-老的配送时长
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KEDTSSW8DGFTFFXGNADGVS2P",
        scenarioName = "老的配送方案-列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("查看详情页-老的配送时长")
public class OldDeliveryTimeConfigDetailTests {

    // 注意：需要在实际使用时替换为真实的 configId

    @DisplayName("查看老的配送时长方案详情")
    @Test
    void shouldGetOldDeliveryTimeConfigDetail() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 查看配送时长方案详情
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/time/config/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        OldDeliveryTimeConfigAddTests oldDeliveryTimeConfigAddTests = new OldDeliveryTimeConfigAddTests();
        oldDeliveryTimeConfigAddTests.shouldAddOldDeliveryTimeConfig();
        Long oldconfigId = oldDeliveryTimeConfigAddTests.oldconfigId;

        String body = String.format("{\"configId\":\"%s\"}", oldconfigId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 5) 从数据库删除

        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(
                "update panda_test.hp_delivery_time_config set is_del=1 where id=" + oldconfigId + "" );

    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");

        headers.put("Content-Type", "application/json");
        return headers;
    }
}

