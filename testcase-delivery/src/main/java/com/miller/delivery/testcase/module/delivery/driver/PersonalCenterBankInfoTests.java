package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心-银行卡信息
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JPP6YKPZVYRZ9PNR4C5NPERW",
        scenarioName = "骑手app-个人中心-银行卡信息",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("个人中心-银行卡信息")
public class PersonalCenterBankInfoTests {

    private static final String DRIVER_ACCOUNT = "13300010015";

    @DisplayName("个人中心-银行卡信息")
    @Test
    void shouldGetPersonalCenterBankInfo() throws InterruptedException {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin(DRIVER_ACCOUNT, "Test1234");

        // 2) 银行卡列表
        getBankInfoList(driverAccessToken);

        // 3) 银行卡设置（type=0）
        setBankInfo(driverAccessToken, "{\"type\":0}");

        // 4) 延迟1秒
        Thread.sleep(1000);

        // 5) 银行卡设置（空body）
        setBankInfo(driverAccessToken, "{}");

        // 6) 延迟1秒
        Thread.sleep(1000);

        // 7) 银行卡设置（未登录）
        setBankInfoWithoutLogin("{\"type\":0}");

        // 8) 插入验证码到数据库（用于银行卡修改）
        insertVerificationCode(DRIVER_ACCOUNT);

        // 9) 银行卡修改
        modifyBank(driverAccessToken, "123456");

        // 10) 延迟1秒
        Thread.sleep(1000);

        // 11) 插入验证码到数据库（用于银行卡修改-验证码错误）
        insertVerificationCode(DRIVER_ACCOUNT);

        // 12) 银行卡修改-验证码错误
        modifyBankWithWrongCode(driverAccessToken, "123123");
    }

    /**
     * 银行卡列表
     */
    private void getBankInfoList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/bank/bankInfoList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
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
     * 银行卡设置
     */
    private void setBankInfo(String driverAccessToken, String body) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/bank/bankInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
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
     * 银行卡设置（未登录）
     */
    private void setBankInfoWithoutLogin(String body) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/bank/bankInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    /**
     * 银行卡修改
     */
    private void modifyBank(String driverAccessToken, String checkCode) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/bank/modifyBank";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format(
                "{\"accountName\":\"DSA\",\"accountNumber\":\"12345678\",\"type\":0,\"sortCode\":\"125555\",\"checkCode\":\"%s\"}",
                checkCode);
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
     * 银行卡修改-验证码错误
     */
    private void modifyBankWithWrongCode(String driverAccessToken, String checkCode) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/bank/modifyBank";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format(
                "{\"accountName\":\"DSA\",\"accountNumber\":\"12345678\",\"type\":0,\"sortCode\":\"125555\",\"checkCode\":\"%s\"}",
                checkCode);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(2011);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("验证码不匹配或已失效，请重新获取验证码");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    /**
     * 插入验证码到数据库
     */
    private void insertVerificationCode(String phoneNumber) {
        long nowTime = System.currentTimeMillis();
        long futureTime = nowTime + 1200000; // 20分钟后

        String sql = String.format(
                "insert into `panda_test`.`hp_delivery_user_log` " +
                        "(`login_ip`, `create_time`, `user_id`, `user_type`, `telephone`, `macId`, `verify_code`, " +
                        "`user_name`, `access_token`, `expires_in`, `refresh_token`, `over_time`, `area_code`, " +
                        "`device_id`, `user_name_mask`, `telephone_mask`, `content_mask`) " +
                        "values ('157.15.28.49, 162.158.114.173', %d, '0', '3', '%s', '', '123456', '', '', '0', '', %d, '86', '', '', '', '')",
                nowTime, phoneNumber, futureTime);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216847");
        headers.put("latitude", "30.203542");
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

