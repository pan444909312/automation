package com.miller.delivery.testcase.module.deliveryAdmin.deliveryTime;

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
 * 编辑-老的配送时长
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT5HQ1174HSQZNCFERFRA9R",
        scenarioName = "老的配送方案-编辑",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("编辑-老的配送时长")
public class OldDeliveryTimeConfigEditTests {







    @DisplayName("编辑老的配送时长方案")
    @Test
    void shouldEditOldDeliveryTimeConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当前时间戳用于配置名称
        long nowTime = System.currentTimeMillis();
        String configName = "自动化测试配送方案老的" + nowTime;

        OldDeliveryTimeConfigAddTests oldDeliveryTimeConfigAddTests = new OldDeliveryTimeConfigAddTests();
        oldDeliveryTimeConfigAddTests.shouldAddOldDeliveryTimeConfig();
        Long oldconfigId = oldDeliveryTimeConfigAddTests.oldconfigId;


        // 3) 编辑配送时长方案
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/time/config/add";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format(
                "{\"configName\":\"%s\",\"configId\":%d,\"scope\":0,\"scopeDesc\":\"全城\",\"isDefault\":0,\"detail\":[{\"distanceStart\":0,\"distanceEnd\":1,\"timeList\":[{\"isDefault\":1,\"max\":\"30\",\"min\":\"20\",\"timeStart\":\"00:00:00\",\"timeEnd\":\"23:59:59\"}]}],\"areaList\":null,\"name\":\"%s\",\"city\":\"杭州市\"}",
                configName, oldconfigId, configName);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
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

