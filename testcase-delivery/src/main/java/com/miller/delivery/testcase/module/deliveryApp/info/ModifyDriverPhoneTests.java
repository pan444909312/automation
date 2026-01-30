package com.miller.delivery.testcase.module.deliveryApp.info;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改手机号
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JZ50XZX1CWVMCK2Y4889MEGG",
        scenarioName = "骑手app-修改手机号",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("修改手机号")
public class ModifyDriverPhoneTests {

    // 注意：需要在实际使用时替换为真实的新手机号
    private static final String NEW_PHONE = "13200000001"; // 请从质量平台或实际业务中获取
    private static final String OLD_PHONE = "13300010682"; // 请从质量平台或实际业务中获取

    @DisplayName("修改手机号完整流程")
    @Test
    void shouldModifyDriverPhone() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010682", "Test1234");

        // 2) 插入验证码到数据库（用于修改手机号）
        insertVerificationCode(NEW_PHONE);

        // 3) 修改手机号
        modifyPhone(driverAccessToken, NEW_PHONE);

        // 4) 骑手登录-新手机号登录
        String newDriverAccessToken = loginWithNewPhone(NEW_PHONE);

        // 5) 插入验证码到数据库（用于改回老手机号）
        insertVerificationCode(OLD_PHONE);

        // 6) 修改手机号-改回老手机号
        modifyPhone(newDriverAccessToken, OLD_PHONE);
    }

    /**
     * 修改手机号
     */
    private void modifyPhone(String driverAccessToken, String telephoneNew) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/modifyDriverPhone";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"areaCode\":\"86\",\"telephoneNew\":\"%s\",\"verificationCode\":\"123456\"}", telephoneNew);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 骑手登录-新手机号登录
     */
    private String loginWithNewPhone(String phone) {
        // 注意：这里假设新手机号的密码MD5是 a9e18b3c663c627bd030c06fae4fe288
        // 实际使用时需要根据实际情况调整
        return TestCaseHelpful.deliveryLogin(phone, "Test1234");
    }

    /**
     * 插入验证码到数据库
     */
    private void insertVerificationCode(String telephone) {
        long nowTime = System.currentTimeMillis();
        long futureTime = nowTime + 1200000; // 20分钟后
        
        String sql = String.format(
                "INSERT INTO `panda_test`.`hp_delivery_user_log` " +
                "(`login_ip`, `create_time`, `user_id`, `user_type`, `telephone`, `macId`, `verify_code`, " +
                "`user_name`, `access_token`, `expires_in`, `refresh_token`, `over_time`, `area_code`, " +
                "`device_id`, `user_name_mask`, `telephone_mask`, `content_mask`) " +
                "VALUES ('157.15.28.49, 172.71.210.13', %d, 0, 3, '%s', '', 123456, '', '', 0, '', %d, '86', '', '', '', '')",
                nowTime, telephone, futureTime);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.21683");
        headers.put("latitude", "30.203575");
        headers.put("version", "5.56.1");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
         
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

