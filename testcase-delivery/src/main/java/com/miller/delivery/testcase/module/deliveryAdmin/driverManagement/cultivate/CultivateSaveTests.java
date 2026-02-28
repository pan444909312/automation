package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.cultivate;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-新增骑手培训内容
 */
@Scenario(
        scenarioID = "01KDSPYMRS6BVME0PH42Z1EWSF",
        scenarioName = "新增骑手培训内容",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("新增骑手培训内容")
public class CultivateSaveTests {

    @DisplayName("新增培训内容")
    @Test
    void shouldSaveCultivate() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增培训内容
         saveCultivate(token);
        // 3）获取新增的 Cultivatecode
    }
    // 新增培训内容
    public   String saveCultivate(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/cultivate/saveCultivate";
        String method = "POST";
        long timestamp = System.currentTimeMillis();
        String cultivateName ="自动化"+timestamp;
        Map<String, Object> headers = createHeaders(token);
        String body = "{"
                + "\"detailPageList\":[{"
                + "\"title\":\"第1页\","
                + "\"name\":\"1\","
                + "\"idx\":0,"
                + "\"browseTime\":10,"
                + "\"pageNum\":1,"
                + "\"detailPageInfoList\":[{"
                + "\"contentModality\":2,"
                + "\"sort\":1,"
                + "\"fileContent\":\""+cultivateName+"\","
                + "\"fileContentList\":[]"
                + "}]"
                + "}],"
                + "\"cultivateName\":\""+cultivateName+"\","
                + "\"applyLanguageType\":0,"
                + "\"cityNameList\":[\"杭州市\"],"
                + "\"driverExam\":1,"
                + "\"cultivateTime\":\"10\","
                + "\"vehicleTypeList\":[0],"
                + "\"cultivateExamList\":[{"
                + "\"examTitle\":\"自动化\","
                + "\"examAnswerAnalysis\":\"自动化-答案解析\","
                + "\"questionsNum\":1,"
                + "\"examType\":1,"
                + "\"cultivateExamDetailList\":[{"
                + "\"answerContent\":\"1\","
                + "\"answerType\":1"
                + "}]"
                + "}]"
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");

        // 5) 从数据库查询新增的配置 获取cultivate_code
        Map<String, Object> configRecords = PandaTestDBHelpful.executeSelectOneSql(
                "select * from panda_test.hp_delivery_cultivate where   is_del=0   and cultivate_name like '自动化%'   order by rec_id desc limit 1\n\n");
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到新增的配置";
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String cultivate_code = (configRecords.get("cultivate_code")).toString();
        System.out.println("打印cultivate_code"+cultivate_code);
        return cultivate_code;

    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

