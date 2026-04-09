package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.control.forbiddenOnline;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
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
        scenarioID = "01KN1Y5P318YGRQNZWGN1ZAZQ3",
        scenarioName = "手动添加禁止上线-新增惩罚",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("手动添加禁止上线-新增惩罚")
public class CreateOnlinePunishTests {

    @DisplayName("手动添加禁止上线-新增惩罚")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Long driverID=1398723978L;
        AuditFailOnlinePunishTests auditFailOnlinePunishTests = new AuditFailOnlinePunishTests();
        String p=add(token,driverID);

        auditFailOnlinePunishTests.auditFail(p,token);

    }

    public String add(String token,Long driverID) {
        RelieveOnlinePunishTests relieveOnlinePunishTests = new RelieveOnlinePunishTests();
        relieveOnlinePunishTests.piliang(driverID,token);

        // 5) 从数据库查询新增的配置
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(
                "update hp_delivery_punish_appeal_content_config_city set is_del=1 where city='九江市' and is_del=0;" +
                        "delete from hp_delivery_punish_appeal_content_config where id=372;" +
                        "delete from hp_delivery_punish_appeal_content_config_city where id=551;");
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(
                "INSERT INTO `panda_test`.`hp_delivery_punish_appeal_content_config_city`(`id`, `config_no`, `config_id`, `city`, `create_time`, `is_del`) VALUES (551, 'PACC509732371043475296', 372, '九江市', 1774953645157, 0);\n" +
                        "INSERT INTO `panda_test`.`hp_delivery_punish_appeal_content_config`(`id`, `config_no`, `can_appeal`, `control_reason`, `control_audit`, `person_remind`, `control_content`, `appeal_wait_audit_title`, `appeal_wait_audit_title_en`, `appeal_wait_audit_content`, `appeal_wait_audit_content_en`, `appeal_audit_success_title`, `appeal_audit_success_title_en`, `appeal_audit_success_content`, `appeal_audit_success_content_en`, `appeal_audit_fail_title`, `appeal_audit_fail_title_en`, `appeal_audit_fail_content`, `appeal_audit_fail_content_en`, `create_time`, `operator`, `update_time`, `is_del`, `is_default`, `control_content_en`) VALUES (372, 'PACC509732371043475296', 1, 1, 1, 1, '1', '2', '9', '3', '10', '4', '11', '5', '12', '6', '13', '7', '14', 1774953645157, '陈春霞', 1774953645157, 0, 0, '8');");


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish/save";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"controlReason\": 6,\n" +
                "    \"driverIdList\": [\n" +
                "        "+driverID+"\n" +
                "    ],\n" +
                "    \"punishRemark\": \"自动化禁止上线\",\n" +
                "    \"punishType\": 0,\n" +
                "    \"rulePunishList\": [\n" +
                "        {\n" +
                "            \"punish\": {\n" +
                "                \"code\": 9,\n" +
                "                \"type\": 3,\n" +
                "                \"desc\": \"禁止上线\",\n" +
                "                \"realControlType\": 0,\n" +
                "                \"thanType\": 0\n" +
                "            },\n" +
                "            \"punishAction\": 9,\n" +
                "            \"punishValue\": null,\n" +
                "            \"warningCnTip\": \"\",\n" +
                "            \"warningRegionTip\": \"\",\n" +
                "            \"punishReasonValue\": [\n" +
                "                3,\n" +
                "                15\n" +
                "            ],\n" +
                "            \"punishReason\": 3,\n" +
                "            \"punishSecondReason\": 15\n" +
                "        }\n" +
                "    ],\n" +
                "    \"punishStatus\": 4\n" +
                "}";

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

        // 5) 从数据库查询新增的配置
        Map<String, Object> configRecords = PandaTestDBHelpful.executeSelectOneSql(
                "select * from  hp_delivery_driver_control_punish where driver_id="+driverID+" and punish_action=9 and punish_remark='自动化禁止上线' order by rec_id desc limit 1 ");
        assert configRecords != null && !configRecords.isEmpty() : "数据库中没有找到新增的配置";
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int auditStatus = (int) configRecords.get("audit_status");
        if (auditStatus==0){
            System.out.println("需要先审核");
        }
        return  (String) configRecords.get("punish_no");




    }




}

