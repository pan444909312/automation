package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.control.forbiddenOnline;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-新增骑手禁止上线配置
 */
@Scenario(
        scenarioID = "01KN1Y4Y3A6R7TJ2TMVE6V1AC6",
        scenarioName = "手动添加禁止上线-审核失败",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("手动添加禁止上线-审核失败")
public class AuditFailOnlinePunishTests {

    @DisplayName("手动添加禁止上线-审核失败")
    @Test
    void auditOnlinePunishTests() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Long driverID=1398723978L;
        CreateOnlinePunishTests createOnlinePunishTests = new CreateOnlinePunishTests();
        String punish_no=createOnlinePunishTests.add(token,driverID);
        auditFail(punish_no,token);

    }

    public void auditFail(String punish_no,String token) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish/audit";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\"punishNo\":\""+punish_no+"\",\"auditStatus\":2,\"rejectReason\":\"自动化测试-拒绝\"}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }




}

