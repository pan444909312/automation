package com.miller.delivery.testcase.module.deliveryAdmin.DeliveryAreaSettings.deliveryTime;

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
 * 新增老的配送时长方案
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT5CA2Z1B1ZXEJCZ4FK2VSM",
        scenarioName = "老的配送方案-新增",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("新增老的配送时长方案")
public class OldDeliveryTimeConfigAddTests {
    public   Long oldconfigId;

    @DisplayName("新增老的配送时长方案")
    @Test
    void shouldAddOldDeliveryTimeConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当前时间戳用于配置名称
        long nowTimestamp = System.currentTimeMillis();
        String configName = "自动化测试配送方案老的" + nowTimestamp;

        // 3) 新增老的配送时长方案
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/time/config/add";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format(
                "{\"name\":\"%s\",\"scope\":0,\"idList\":[],\"detail\":[{\"distanceStart\":0,\"distanceEnd\":1,\"timeList\":[{\"isDefault\":1,\"max\":\"30\",\"min\":\"20\",\"timeStart\":\"00:00:00\",\"timeEnd\":\"23:59:59\"}]}],\"configName\":\"%s\",\"city\":\"杭州市\"}",
                configName, configName);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 5) 从数据库查询新增的配置
        List<Map<String, Object>> configRecords = PandaTestDBHelpful.executeSelectListSql(
                "select * from panda_test.hp_delivery_time_config where config_name=?",
                configName);
        
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到新增的配置";
        
        Map<String, Object> configRecord = configRecords.get(0);
        Long configId = ((Number) configRecord.get("id")).longValue();
        oldconfigId=configId;
        assert configId != null && configId > 0 : "配置ID无效";
        // 5) 从数据库删除
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(
                "update panda_test.hp_delivery_time_config set is_del=1 where id=" + oldconfigId + "" );

    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("Content-Type", "application/json");
        return headers;
    }
}

