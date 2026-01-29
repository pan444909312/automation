package com.miller.delivery.testcase.module.deliveryAdmin.station;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 司管后台-骑手列表-删除骑手驿站
 */
//@Scenario(
//        scenarioID = "01KG4KA0XGPF86Z4B75BE5JAQP",
//        scenarioName = "骑手列表-删除骑手驿站",
//        author = "TestingConsultant@hungrypandagroup.com",
//        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
//@DisplayName("删除骑手驿站")
public class StationDeleteTests {

    //private static final String CONFIG_NO = "123456"; // 假设一个configNo

    @DisplayName("删除骑手驿站")
    @Test
    void shouldDeleteStation() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 前置数据
        String Station_CONFIG_No_Str = getStaionData("奥克兰");
        Long ETA_CONFStation_CONFIG_No = Long.parseLong(Station_CONFIG_No_Str);

        // 2) 删除骑手驿站
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/station/del";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"configNo\":\"" + ETA_CONFStation_CONFIG_No + "\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言删除成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private String getStaionData(String city) {
        Object stationConfigNo = null;
        String sql1 = String.format("select config_no from hp_delivery_driver_station where city = \"%s\" and is_del = 0 order by id desc limit 1;", city);
        List<Map<String, Object>> resultList = PandaTestDBHelpful.executeSelectListSql(sql1);
        String sql2 = String.format("update hp_delivery_driver_station set is_del = 0, config_status = 0 where config_no = %s;", stationConfigNo);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql2);
        if (resultList != null && !resultList.isEmpty()) {
            stationConfigNo = resultList.get(0).get("config_no");
            return stationConfigNo != null ? stationConfigNo.toString() : "121";
            }
        return "121";
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

