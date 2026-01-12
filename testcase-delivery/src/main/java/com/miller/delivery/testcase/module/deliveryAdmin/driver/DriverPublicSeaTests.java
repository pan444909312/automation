package com.miller.delivery.testcase.module.deliveryAdmin.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 各个环节进入司机公海
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K08JVC9TB9EY01XQ8J7G56Q0",
        scenarioName = "招新代表-各个环节公海操作",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 90)
@DisplayName("各个环节进入司机公海")
public class DriverPublicSeaTests {

    @DisplayName("完整端到端流程-各个环节进入司机公海")
    @Test
    void shouldCompletePublicSeaFlow() {
        // ========== 第一部分：司管登录 ==========
        // 步骤1: 司管登录获取token
        String siGuanToken = erpLogin();
        
        // ========== 第二部分：待提交环节公海操作 ==========
        // 步骤2: 获取待提交tab列表
        Long userId = getPendingSubmitList(siGuanToken);
        
        // 步骤3: 待提交环节放入公海
        putToPublicSea(siGuanToken, userId, 4, "待提交放入公海");
        
        // 步骤4: 公海召回到待提交环节
        recallFromPublicSea(siGuanToken, userId, 6);
        
        // ========== 第三部分：待审批环节公海操作 ==========
        // 步骤5: 获取待审批列表
        Long userId1 = getPendingApprovalList(siGuanToken);
        
        // 步骤6: 待审批环节进入公海
        putToPublicSea(siGuanToken, userId1, 4, "待信息审核放入公海");
        
        // 步骤7: 公海召回到待审批环节
        recallFromPublicSea(siGuanToken, userId1, 6);
        
        // ========== 第四部分：失败待提交环节公海操作 ==========
        // 步骤8: 获取失败待提交列表
        Long userId7 = getFailedPendingSubmitList(siGuanToken);
        
        // 步骤9: 失败待提交进入公海
        putToPublicSea(siGuanToken, userId7, 4, "审批失败进入公海");
        
        // 步骤10: 公海召回到失败待提交环节
        recallFromPublicSea(siGuanToken, userId7, 6);
        
        // ========== 第五部分：背调环节公海操作 ==========
        // 步骤11: 获取背景调查列表
        Long userId2 = getBackgroundCheckList(siGuanToken);
        
        // 步骤12: 背调环节不合格
        backgroundCheckFail(siGuanToken, userId2);
        
        // 步骤13: 公海召回到背调环节
        recallFromPublicSea(siGuanToken, userId2, 6);
        
        // ========== 第六部分：模拟培训环节公海操作 ==========
        // 步骤14: 获取模拟培训列表
        Long userId4 = getSimulationTrainingList(siGuanToken);
        
        // 步骤15: 模拟培训环节进入公海
        putToPublicSea(siGuanToken, userId4, 1, "模拟培训进入公海");
        
        // 步骤16: 公海进入模拟培训环节
        recallFromPublicSea(siGuanToken, userId4, 6);
        
        // ========== 第七部分：培训考试环节公海操作 ==========
        // 步骤17: 获取培训考试列表
        Long userId5 = getTrainingExamList(siGuanToken);
        
        // 步骤18: 培训考试环节放入公海
        putToPublicSea(siGuanToken, userId5, 4, "培训考试中放入公海");
        
        // 步骤19: 公海召回到考试环节
        recallFromPublicSea(siGuanToken, userId5, 6);
        
        // ========== 第八部分：待确认环节公海操作 ==========
        // 步骤20: 获取待确定列表
        Long userId6 = getPendingConfirmList(siGuanToken);
        
        // 步骤21: 待确认环节进入公海
        putToPublicSea(siGuanToken, userId6, 4, "放入公海");
        
        // 步骤22: 公海召回到待确认环节
        recallFromPublicSea(siGuanToken, userId6, 6);
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
     * 获取待提交tab列表
     */
    private Long getPendingSubmitList(String siGuanToken) {
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
     * 获取失败待提交列表
     */
    private Long getFailedPendingSubmitList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":7,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null,\"pageNo\":1,\"pageSize\":15}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.data.records[0].userId").toString());
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
     * 获取模拟培训列表
     */
    private Long getSimulationTrainingList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":10,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null,\"pageNo\":1,\"pageSize\":15}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.data.records[0].userId").toString());
    }

    /**
     * 获取培训考试列表
     */
    private Long getTrainingExamList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":8,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null,\"pageNo\":1,\"pageSize\":15}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.data.records[0].userId").toString());
    }

    /**
     * 获取待确定列表
     */
    private Long getPendingConfirmList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverTodoList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = "{\"city\":null,\"driverCode\":null,\"runType\":null,\"source\":null,\"contactStatus\":null,\"driverLabel\":[],\"userId\":null,\"userName\":null,\"telephone\":null,\"type\":9,\"secondType\":null,\"driverBusinessTypeList\":[],\"siteIdList\":[],\"areaList\":[],\"regChannel\":null,\"dateRange_lastFollow\":[],\"updateDateRange\":[],\"email\":null,\"whatsApp\":null,\"followStatus\":null,\"failType\":null,\"workDate\":null,\"workTime\":[],\"workHour\":null,\"languageCode\":\"\",\"dateStart\":null,\"dateEnd\":null,\"lastFollowTimeStart\":null,\"lastFollowTimeEnd\":null,\"updateTimeStart\":null,\"updateTimeEnd\":null,\"pageNo\":1,\"pageSize\":15}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.data.records[0].userId").toString());
    }

    /**
     * 放入公海
     */
    private void putToPublicSea(String siGuanToken, Long driverId, int failType, String failTypeReason) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/putPublicSea";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = String.format("{\"failType\":%d,\"failTypeReason\":\"%s\",\"driverId\":%d}", failType, failTypeReason, driverId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 公海召回
     */
    private void recallFromPublicSea(String siGuanToken, Long userId, int operation) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = String.format("{\"userId\":%d,\"operation\":%d,\"status\":null}", userId, operation);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 背调环节不合格
     */
    private void backgroundCheckFail(String siGuanToken, Long userId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        var requestBody = String.format("{\"operation\":3,\"status\":2,\"userId\":%d}", userId);
        
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

