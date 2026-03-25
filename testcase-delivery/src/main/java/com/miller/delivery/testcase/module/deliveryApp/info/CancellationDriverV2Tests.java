package com.miller.delivery.testcase.module.deliveryApp.info;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.miller.delivery.testcase.utils.DriverOffline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 注销账号
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JZ863BBQ7M70FYF51BBGZJ6W",
        scenarioName = "骑手app-注销账号",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("注销账号")
public class CancellationDriverV2Tests {

    private static final String DRIVER_ACCOUNT = "13300010016";

    @DisplayName("注销账号完整流程")
    @Test
    void shouldCancelDriverAccount() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin(DRIVER_ACCOUNT, "Test1234");

        // 2）清空最近30天订单
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline(DRIVER_ACCOUNT,driverAccessToken);

        // 3) 插入验证码到数据库（用于注销账号）
        insertVerificationCode(DRIVER_ACCOUNT);

        // 4) 注销账号
        cancelDriverAccount(driverAccessToken);

        // 5) 司管后台登录
        String siGuanToken = erpLogin();

        // 6) 配送回收站列表
        Long userId = getRecycleDriverList(siGuanToken);

        // 7) 恢复骑手账号
        if (userId != null) {
            restoreDriverAccount(siGuanToken, userId);
        }
    }

    /**
     * 注销账号
     */
    private void cancelDriverAccount(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/cancellationDriverV2";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"captcha\":\"123456\"}";
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
     * 配送回收站列表
     */
    private Long getRecycleDriverList(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/recycleDriverList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        String body = "{\"city\":null,\"realName\":null,\"userName\":null,\"userId\":null,\"pageNo\":1,\"pageSize\":10}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 提取userId（从第一条记录）
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> records = (List<Map<String, Object>>) TestCaseHelpful.extractValue(responseBody, "$.data.records");
            if (records != null && !records.isEmpty()) {
                Map<String, Object> firstRecord = records.get(0);
                Object userIdObj = firstRecord.get("userId");
                if (userIdObj != null) {
                    return ((Number) userIdObj).longValue();
                }
            }
        } catch (Exception e) {
            // 如果提取失败，返回null
        }
        return null;
    }

    /**
     * 恢复骑手账号
     */
    private void restoreDriverAccount(String siGuanToken, Long userId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/unDelDriver";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        String body = "{\n" +
                "    \"driverId\": "+userId+",\n" +
                "    \"op\": \"undelete\"\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
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

    /**
     * 创建ERP请求头
     */
    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("priority", "u=1, i");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

