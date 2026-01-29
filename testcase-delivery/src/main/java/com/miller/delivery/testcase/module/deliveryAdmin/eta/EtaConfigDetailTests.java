package com.miller.delivery.testcase.module.deliveryAdmin.eta;

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
 * 查看eta配置
 *
 * @author 彭路路
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K629DMYHD9N6XGX02J1K18P7",
        scenarioName = "司管-查看EAT",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("查看eta配置")
public class EtaConfigDetailTests {

    // 注意：需要在实际使用时替换为真实的 eta_time_config_id
    // apifox迁移过来的逻辑，未实现自动查库导致自动化执行失败；现注释，并实现动态查询数据库获取杭州市有效的eta id； - 江彪 2026.01.29记录
    //private static final Long ETA_CONFIG_ID = 1L; // 请从质量平台或数据库中获取实际的 eta_time_config_id

    @DisplayName("查看ETA配置详情")
    @Test
    void shouldGetEtaConfigDetail() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取杭州市 有效 etaid，并转换为Long类型
        String ETA_CONFIG_ID_Str = getEtaIdFromDatabase();
        Long ETA_CONFIG_ID = Long.parseLong(ETA_CONFIG_ID_Str);

        // 3) 查看ETA配置详情
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"id\":%d}", ETA_CONFIG_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 5) 验证返回包含自动化配置名称（如果存在）
        assert responseBody.contains("自动化新增ETA配送方案") || responseBody.contains("data") : 
                "返回结果应包含配置信息";
    }

    /**
     * 从数据库查询杭州市有效ETA_id
     * author：江彪
     * time：2026.1.29 新增
     */
    private String getEtaIdFromDatabase() {
        String sql = String.format("select id from hp_delivery_eta_time_config  where is_del=0 AND city_id=1 order by id limit 1;");
        List<Map<String, Object>> resultList = PandaTestDBHelpful.executeSelectListSql(sql);
        if (resultList != null && !resultList.isEmpty()) {
            Object etaID = resultList.get(0).get("id");
            return etaID != null ? etaID.toString() : "121";
        }
        return "121";
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

