package com.miller.delivery.testcase.module.deliveryAdmin.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 正向审批-含背调环节
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K08JKVV71F7RYPTCMTQ3JZS0",
        scenarioName = "招新代办-各个环节骑手审批通过",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 90)
@DisplayName("正向审批-含背调环节")
public class DriverApprovalWithBackgroundCheckTests {

    @DisplayName("完整端到端流程-正向审批流程（含背调环节）")
    @Test
    void shouldCompleteApprovalFlowWithBackgroundCheck() {
        // ========== 第一部分：司管登录 ==========
        // 步骤1: 司管后台登录
        String siGuanToken = erpLogin();
        
        // 步骤2: 获取当前审批流程环节
        getApprovalFlowConfig(siGuanToken);
        
        // ========== 第二部分：认证资料审批 ==========
        // 步骤3: 获取待审批列表
        Long userId = getPendingApprovalList(siGuanToken);
        
        // 步骤4: 认证资料审批并通过
        approveAuthInfo(siGuanToken, userId);
        
        // ========== 第三部分：背景调查 ==========
        // 步骤5: 获取背景调查列表
        Long userId1 = getBackgroundCheckList(siGuanToken);
        
        // 步骤6: 背调环节通过
        passBackgroundCheck(siGuanToken, userId1);
        
        // ========== 第四部分：模拟培训 ==========
        // 步骤7: 获取模拟培训列表
        getSimulationTrainingList(siGuanToken);
        
        // 步骤8: 模拟培训环节通过
        passSimulationTraining(siGuanToken, userId);
        
        // ========== 第五部分：培训考试 ==========
        // 步骤9: 培训考试列表
        getTrainingExamList(siGuanToken);
        
        // 步骤10: 考试通过
        passTrainingExam(siGuanToken, userId);
        
        // ========== 第六部分：最后确认 ==========
        // 步骤11: 待确定列表
        getPendingConfirmList(siGuanToken);
        
        // 步骤12: 最后确认环节通过
        passFinalConfirm(siGuanToken, userId);
    }

    /**
     * 司管后台登录并返回token
     */
    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        
        var requestBody = "{\"password\":\"d9501f93554734ba83d19c9dc83ef4fb\",\"userName\":\"ding023660390221528503\"}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }

    /**
     * 获取当前审批流程环节
     */
    private void getApprovalFlowConfig(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoConfig";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":1,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 获取待审批列表
     */
    private Long getPendingApprovalList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":2,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null,\"pageNo\":1,\"pageSize\":15}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.data.records[0].userId").toString());
    }

    /**
     * 认证资料审批并通过
     */
    private void approveAuthInfo(String siGuanToken, Long userId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = String.format("{\"operation\":2,\"status\":1,\"userId\":%d}", userId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 获取背景调查列表
     */
    private Long getBackgroundCheckList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":3,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null,\"pageNo\":1,\"pageSize\":15}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.data.records[0].userId").toString());
    }

    /**
     * 背调环节通过
     */
    private void passBackgroundCheck(String siGuanToken, Long userId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = String.format("{\"operation\":3,\"status\":1,\"userId\":%d}", userId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 获取模拟培训列表
     */
    private void getSimulationTrainingList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":10,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null,\"pageNo\":1,\"pageSize\":15}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 模拟培训环节通过
     */
    private void passSimulationTraining(String siGuanToken, Long driverId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/authDriver/testOrderComplete";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = String.format("{\"driverId\":%d,\"reason\":\"通过模拟培训\"}", driverId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 培训考试列表
     */
    private void getTrainingExamList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":8,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null,\"pageNo\":1,\"pageSize\":15}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 考试通过
     */
    private void passTrainingExam(String siGuanToken, Long userId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = String.format("{\"operation\":8,\"status\":1,\"userId\":%d,\"trainCompleteReason\":\"考试通过\"}", userId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 待确定列表
     */
    private void getPendingConfirmList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":9,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null,\"pageNo\":1,\"pageSize\":15}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 最后确认环节通过
     */
    private void passFinalConfirm(String siGuanToken, Long userId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = String.format("{\"userId\":%d,\"operation\":9,\"status\":1}", userId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 创建ERP请求头
     */
    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/erp/auth/request/headers.json");
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

