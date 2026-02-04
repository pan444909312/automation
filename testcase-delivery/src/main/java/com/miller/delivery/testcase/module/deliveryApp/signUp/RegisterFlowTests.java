package com.miller.delivery.testcase.module.deliveryApp.signUp;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手注册多Case
 *
 * Apifox: docs/d-apifox/checked/注册流程多case.apifox-cli.json
 */
//@Scenario(
//        scenarioID = "01K3N6W55KM5N4F37SSPNB2VYX",
//        scenarioName = "骑手注册多Case",
//        author = "TestingConsultant@hungrypandagroup.com",
//        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
//@DisplayName("注册流程多case")
public class RegisterFlowTests {

    @DisplayName("异常case: 手机号为空")
    @Test
    void shouldFailRegisterWithEmptyPhone() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        Map<String, Object> headers = createDriverAppHeaders();

        String body = "{\"country\":\"CN\",\"areaCode\":\"86\",\"password\":\"a9e18b3c663c627bd030c06fae4fe288\",\"captcha\":\"123456\",\"cityId\":1,\"account\":\"\",\"invitationCode\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(102061);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("请填写账号");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("异常case: 密码为空")
    @Test
    void shouldFailRegisterWithEmptyPassword() {
        String newTel = preparePhoneNumber();
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        Map<String, Object> headers = createDriverAppHeaders();

        String body = String.format("{\"country\":\"CN\",\"areaCode\":\"86\",\"password\":\"\",\"captcha\":\"123456\",\"cityId\":1,\"account\":\"%s\",\"invitationCode\":\"\"}", newTel);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(102062);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("请填写密码");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("异常case: 验证码为空")
    @Test
    void shouldFailRegisterWithEmptyCaptcha() {
        String newTel = preparePhoneNumber();
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        Map<String, Object> headers = createDriverAppHeaders();

        String body = String.format("{\"country\":\"CN\",\"areaCode\":\"86\",\"password\":\"a9e18b3c663c627bd030c06fae4fe288\",\"captcha\":\"\",\"cityId\":1,\"account\":\"%s\",\"invitationCode\":\"\"}", newTel);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(100018);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("验证码不能为空！");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("异常case: 国家为空")
    @Test
    void shouldFailRegisterWithEmptyCountry() {
        String newTel = preparePhoneNumber();
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        Map<String, Object> headers = createDriverAppHeaders();

        String body = String.format("{\"country\":\"\",\"areaCode\":\"86\",\"password\":\"a9e18b3c663c627bd030c06fae4fe288\",\"captcha\":\"123456\",\"cityId\":1,\"account\":\"%s\",\"invitationCode\":\"\"}", newTel);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(102067);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("请选择注册国家");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("注册全流程（按Apifox步骤）")
    @Test
    void shouldCompleteRegisterMultiCaseFlow() {
        // 0) 生成新手机号 & 插入验证码（captcha=123456）
        String newTel = preparePhoneNumber();
        insertVerificationCode(newTel);

        // 1) 注册页 -随机手机号验证码提交
        String accessToken = registerNewDriver(newTel);

        // 2) 注册-欢迎页
        driverBaseInfo(accessToken);

        // 3) 异常case：再需3步就可以跑单 -数据为空
        essentialInfoDriver(accessToken, "{}", 100015, "请选择怎么知道熊猫外卖的", false);

        // 4) 再需3步就可以跑单弹窗
        essentialInfoDriver(accessToken, "{\"workDate\":2,\"source\":2,\"workHour\":2,\"experience\":1,\"workTime\":[1],\"vehicleType\":0}",
                1000, "成功", true);

        // 5) 异常Case: 获取全部配置接口 -未登录
        loadGlobalConfig(null, 2015, "未登录,请登录后操作", false);

        // 6) 获取全部配置接口
        loadGlobalConfig(accessToken, 1000, "成功", true);

        // 7) bindAlias -registerid为空
        bindAlias(accessToken, "{\"registrationId\":\"\",\"alias\":\"472be5fcdcc4bb0002a8f539122c5f7d\",\"type\":2}",
                101011, "参数错误", false);

        // 8) bindAlias接口
        bindAlias(accessToken, "{\"registrationId\":\"170976fa8bbc96d1988\",\"alias\":\"472be5fcdcc4bb0002a8f539122c5f7d\",\"type\":2}",
                1000, "成功", true);

        // 9) 异常case：未登录 -新骑手banner列表
        newDriverBannerList(null, 2015, "未登录,请登录后操作", false);

        // 10) 新骑手banner列表
        newDriverBannerList(accessToken, 1000, "成功", true);

        // 11) 获取当前审核进度
        checkRegisterDriverStatus(accessToken);

        // 12) 根据token获取全部区域信息
        loadAreaByToken(accessToken);

        // 13) 点击去认证-进入骑手认证页面
        certificationInfo(accessToken);

        // 14) 异常Case: 认证提交时 -- lastname为空
        saveCertificationInfo(accessToken, CERTIFICATION_LASTNAME_EMPTY_BODY, 102068, "请填写姓名", false);

        // 15) 提交认证-所有必填：骑手认证信息填写完成提交审核
        saveCertificationInfo(accessToken, CERTIFICATION_ALL_REQUIRED_BODY, 1000, "成功", true);
    }

    private String preparePhoneNumber() {
        Map<String, Object> result = PandaTestDBHelpful.executeSelectOneSql(
                "select * from `panda_test`.`hp_delivery_user` where user_telphone like '133000%' order by user_id desc limit 1");
        if (result != null && result.get("user_telphone") != null) {
            String oldTel = result.get("user_telphone").toString();
            long oldTelNum = Long.parseLong(oldTel);
            return String.valueOf(oldTelNum + 1);
        }
        return "13300099010";
    }

    private void insertVerificationCode(String newTel) {
        long nowTime = System.currentTimeMillis();
        long futureTime = nowTime + 1200000;
        String sql = String.format(
                "insert into `panda_test`.`hp_delivery_user_log` " +
                        "(`login_ip`, `create_time`, `user_id`, `user_type`, `telephone`, `macId`, `verify_code`, " +
                        "`user_name`, `access_token`, `expires_in`, `refresh_token`, `over_time`, `area_code`, " +
                        "`device_id`, `user_name_mask`, `telephone_mask`, `content_mask`) " +
                        "values ('157.15.28.49, 162.158.114.173', %d, '0', '3', '%s', '', '123456', '', '', '0', '', %d, '86', '', '', '', '')",
                nowTime, newTel, futureTime);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
    }

    private String registerNewDriver(String newTel) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        Map<String, Object> headers = createDriverAppHeaders();

        String body = String.format("{\"country\":\"CN\",\"areaCode\":\"86\",\"password\":\"a9e18b3c663c627bd030c06fae4fe288\",\"captcha\":\"123456\",\"cityId\":1,\"account\":\"%s\",\"invitationCode\":\"\"}", newTel);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);

        return TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }

    private void driverBaseInfo(String accessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/driverBaseInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void essentialInfoDriver(String accessToken, String body, int expectedResultCode, String expectedReason, boolean expectedSuccess) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/essentialInfoDriver";
        Map<String, Object> headers = createDriverAppHeaders();
        if (accessToken != null) {
            headers.put("authorization", accessToken);
        }

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(expectedResultCode);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo(expectedReason);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(expectedSuccess);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void loadGlobalConfig(String accessToken, int expectedResultCode, String expectedReason, boolean expectedSuccess) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/config/loadGlobalConfig";
        Map<String, Object> headers = createDriverAppHeaders();
        if (accessToken != null) {
            headers.put("authorization", accessToken);
        }

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(expectedResultCode);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo(expectedReason);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(expectedSuccess);
    }

    private void bindAlias(String accessToken, String body, int expectedResultCode, String expectedReason, boolean expectedSuccess) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/jPush/bindAlias";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(expectedResultCode);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo(expectedReason);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(expectedSuccess);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void newDriverBannerList(String accessToken, int expectedResultCode, String expectedReason, boolean expectedSuccess) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driverCenter/newList";
        Map<String, Object> headers = createDriverAppHeaders();
        if (accessToken != null) {
            headers.put("authorization", accessToken);
        }

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(expectedResultCode);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo(expectedReason);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(expectedSuccess);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void checkRegisterDriverStatus(String accessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/checkRegisterDriverStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void loadAreaByToken(String accessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/common/loadAreaByToken";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void certificationInfo(String accessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/certificationInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void saveCertificationInfo(String accessToken, String body, int expectedResultCode, String expectedReason, boolean expectedSuccess) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/saveCertificationInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(expectedResultCode);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo(expectedReason);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(expectedSuccess);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 为避免超大 patch，这里复用项目中可用的 raw body 模板（关键字段与 apifox 一致：lastName 空/非空）
    private static final String CERTIFICATION_LASTNAME_EMPTY_BODY =
            "{\"regoExpirationDate\":\"2025-11-29\",\"lastName\":\"\",\"certificatesExpirationDate\":\"2025-07-26\",\"certificatesHandlePhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482552322309c47f463ee84408eb9f3193c969111c8.jpg\",\"needCertificate\":\"1\",\"driverBirthday\":\"2025-08-23\",\"drivingLicensePhotoFrontUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255281119d91197320a484732bbd9b647a8b4a8a7.jpg\",\"personRace\":\"0\",\"caWorkPermit\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748263128130686882d341a64bfd9417568424f4cf43.jpg\",\"certificatesPhotoBackUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260638330cf19d68d83564832aff667275486b25c.jpg\",\"caWorkPermitDate\":\"2025-11-29\",\"address\":\"滨江\",\"jpTrafficInsurance\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260713869f64a5d94c3704457ad9eab6b48218e91.jpg\",\"studyIdCartUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482606475826662d70843454c30ad9942a5c34797ee.jpg\",\"weekHoursPer\":\"2\",\"areaIds\":\"919\",\"timeType\":\"1\",\"jpHealthInsuranceDate\":\"2025-11-29\",\"jpTrafficInsuranceDate\":\"2025-01-31\",\"cbtExpirationDate\":\"2025-07-26\",\"landingPermitNumber\":\"1234567890123\",\"landingPermitType\":\"4\",\"residentialArea\":\"919\",\"residenceCardType\":\"3\",\"lightVestUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825527135254e6d5625075498cb72303b74f7e9a54.jpg\",\"lightVest\":\"1\",\"certificatesPhotoFrontUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255187773cf0fb0567ad04b0f9972c0f1e0613fb8.jpg\",\"email\":\"1351792983@qq.com\",\"vehicleType\":\"0\",\"vehicleLicense\":\"112233\",\"visaUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255211931e96ec00d192e40c0a1f5cf7fb2024d26.jpg\",\"visaDate\":\"2025-02-28\",\"visaNumber\":\"123456789\",\"certificationInfoStatus\":2,\"passportType\":\"1\",\"regoCtpPhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825524690886fd6dee90bf4f1888d2d0c523fba16d.jpg\",\"jpHealthInsurance\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174826068932072d33f1171454409a495312ddfe546c7.jpg\",\"certificatesPhotoType\":\"0\",\"visaType\":\"2\",\"firstName\":\"auto\",\"drivingLicenseExpirationDate\":\"2025-10-31\",\"residentAddress\":\"滨江地址\"}";

    private static final String CERTIFICATION_ALL_REQUIRED_BODY =
            "{\"regoExpirationDate\":\"2030-11-29\",\"lastName\":\"Griffin\",\"certificatesExpirationDate\":\"2030-07-26\",\"certificatesHandlePhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482552322309c47f463ee84408eb9f3193c969111c8.jpg\",\"needCertificate\":\"1\",\"driverBirthday\":\"1983-02-10\",\"drivingLicensePhotoFrontUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255281119d91197320a484732bbd9b647a8b4a8a7.jpg\",\"personRace\":\"0\",\"caWorkPermit\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748263128130686882d341a64bfd9417568424f4cf43.jpg\",\"certificatesPhotoBackUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260638330cf19d68d83564832aff667275486b25c.jpg\",\"caWorkPermitDate\":\"2035-11-29\",\"address\":\"滨江\",\"jpTrafficInsurance\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260713869f64a5d94c3704457ad9eab6b48218e91.jpg\",\"studyIdCartUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482606475826662d70843454c30ad9942a5c34797ee.jpg\",\"weekHoursPer\":\"2\",\"areaIds\":\"919\",\"timeType\":\"1\",\"jpHealthInsuranceDate\":\"2030-11-29\",\"jpTrafficInsuranceDate\":\"2030-01-31\",\"cbtExpirationDate\":\"2030-07-26\",\"landingPermitNumber\":\"1234567890123\",\"landingPermitType\":\"4\",\"residentialArea\":\"919\",\"residenceCardType\":\"3\",\"lightVestUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825527135254e6d5625075498cb72303b74f7e9a54.jpg\",\"lightVest\":\"1\",\"certificatesPhotoFrontUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255187773cf0fb0567ad04b0f9972c0f1e0613fb8.jpg\",\"email\":\"1351792983@qq.com\",\"vehicleType\":\"0\",\"vehicleLicense\":\"112233\",\"visaUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255211931e96ec00d192e40c0a1f5cf7fb2024d26.jpg\",\"visaDate\":\"2030-02-28\",\"visaNumber\":\"123456789\",\"certificationInfoStatus\":2,\"passportType\":\"1\",\"regoCtpPhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825524690886fd6dee90bf4f1888d2d0c523fba16d.jpg\",\"jpHealthInsurance\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174826068932072d33f1171454409a495312ddfe546c7.jpg\",\"certificatesPhotoType\":\"0\",\"visaType\":\"2\",\"firstName\":\"auto\",\"drivingLicenseExpirationDate\":\"2030-10-31\",\"residentAddress\":\"滨江地址\"}";

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.21674955924935");
        headers.put("latitude", "30.20344076263413");
        headers.put("version", "5.80.1");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}
