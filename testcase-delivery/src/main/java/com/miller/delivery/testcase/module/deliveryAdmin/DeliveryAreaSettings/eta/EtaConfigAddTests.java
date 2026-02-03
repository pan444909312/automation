package com.miller.delivery.testcase.module.deliveryAdmin.DeliveryAreaSettings.eta;

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
 * 新增eta配置
 *
 * @author 彭路路
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K60H25RN1PTD3VK6MJ163MV4",
        scenarioName = "司管-新增eta新",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("新增eta配置")
public class EtaConfigAddTests {

    private static final Long AREA_ID = 902L;
    private static final String CITY_ID = "729";

    @DisplayName("新增eta配置-未登录不可添加")
    @Test
    void shouldNotAddEtaConfigWithoutLogin() {
        // 1) 使用无效token
        String invalidToken = "324222222";

        // 2) 生成当前时间戳用于配置名称
        long nowTime = System.currentTimeMillis();
        String configName = "自动化新增ETA配送方案" + nowTime;

        // 3) 尝试新增eta配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/add";
        String method = "POST";
        Map<String, Object> headers = createHeaders(invalidToken);
        String body = String.format(
                "{\"name\":\"%s\",\"scope\":0,\"minDiff\":1,\"maxDiff\":2,\"specialTimeList\":[],\"shopList\":[],\"areaList\":[],\"areaIdList\":[],\"shopIdList\":[],\"cityId\":\"%s\"}",
                configName, CITY_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        String responseText = responseBody.toString();
        assert responseText.contains("没有该系统登陆权限") : "未正确返回未登录错误";
    }

    @DisplayName("新增eta配置-成功")
    @Test
    void shouldAddEtaConfig() {
        int configId = configId();
        EtaConfigDeleteTests etaD= new EtaConfigDeleteTests();
        etaD.deleteETA(configId);

    }

    public int configId(){  // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当前时间戳用于配置名称
        long nowTime = System.currentTimeMillis();
        String configName = "自动化新增ETA配送方案" + nowTime;

        // 3) 新增eta配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/add";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format(
                "{\"name\":\"%s\",\"scope\":1,\"minDiff\":1,\"maxDiff\":2,\"specialTimeList\":[],\"shopList\":[],\"areaList\":[],\"areaIdList\":[%d],\"shopIdList\":[],\"cityId\":\"%s\"}",
                configName, AREA_ID, CITY_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 5) 从数据库查询新增的配置
        Map<String, Object> configRecords = PandaTestDBHelpful.executeSelectOneSql(
                "select * from panda_test.hp_delivery_eta_time_config where name=? order by id desc limit 1",
                configName);
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到新增的配置";


        int configId = Integer.parseInt((configRecords.get("id")).toString());
        return configId;
    }
    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

