package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.station;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 司管后台-骑手列表-删除骑手驿站
 */
@Scenario(
        scenarioID = "01KG4KA0XGPF86Z4B75BE5JAQP",
        scenarioName = "骑手列表-删除骑手驿站",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("删除骑手驿站")
public class StationDeleteTests {


    @DisplayName("删除骑手驿站")
    @Test
    void shouldDeleteStation() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        String ConfigNo = getStaionData();
        // 2) 禁用驿站
        disableStation(ConfigNo,0);

        // 2) 删除骑手驿站
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/station/del";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"configNo\":\"" + ConfigNo + "\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言删除成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }
    private void disableStation(String ConfigNo,int configStatus) {
        // 1) 司管登录获取 token
        String token = erpLogin();


        // 2) 禁用驿站
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/station/configStatus";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"configNo\":\"" + ConfigNo + "\",\"configStatus\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言禁用成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private String getStaionData() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增骑手驿站
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/station/add";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"serviceTypeList\":[10],\"city\":\"奥克兰\",\"stationNameCn\":\"测试自动化\",\"stationAddress\":\"奥克兰主城区-自动化测试地址\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        Map<String, Object> demandRecord = PandaTestDBHelpful.executeSelectOneSql(
                "select * from panda_test.hp_delivery_driver_station where station_name_cn='测试自动化' order by id desc limit 1");
        String configId = (demandRecord.get("config_no")).toString();
        return configId;
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

