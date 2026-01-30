package com.miller.delivery.testcase.module.deliveryApp.backgroundCheck;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-首跑前完成背调
 *
 * Apifox: docs/d-apifox/背调场景.apifox-cli.json
 */
@Scenario(
        scenarioID = "01K633Z5SWQ5KF706BX6VW1RAD",
        scenarioName = "骑手app-首跑前完成背调",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("背调场景")
public class DriverBackgroundCheckBeforeFirstRunTests {

    private String newTel;
    private long newUserId;
    private String accessToken;

    @DisplayName("首跑前完成背调-完整流程")
    @Test
    void shouldCompleteBackgroundCheckBeforeFirstRun() {
        // 0) 生成新手机号 & 插入验证码
        preparePhoneNumber();
        insertVerificationCode();

        // 1) 注册页 -随机手机号验证码提交
        registerNewDriver();

        // 2) 注册-欢迎页
        driverBaseInfo();

        // 3) 再需3步就可以跑单弹窗
        essentialInfoDriver();

        // 4) 获取全部配置接口
        loadGlobalConfig();

        // 5) bindAlias接口
        bindAlias();

        // 6) 新骑手banner列表
        newDriverBannerList();

        // 7) 获取当前审核进度
        checkRegisterDriverStatus();

        // 8) 根据token获取全部区域信息
        loadAreaByToken();

        // 9) 点击去认证-进入骑手认证页面
        certificationInfo();

        // 10) 骑手认证信息填写完成提交审核
        saveCertificationInfo();

        // 11) 司管后台登录
        String erpToken = TestCaseHelpful.erpLogin();

        // 12) 司管第一步审核（operation=2）
        newDriverAuthOperation(erpToken, 2, "{\"operation\":2,\"status\":1,\"userId\":%d}");

        // 13) 提交背调
        submitBackgroundCheck();

        // 14) 第二步审核（operation=3）
        newDriverAuthOperation(erpToken, 3, "{\"operation\":3,\"status\":1,\"userId\":%d}");

        // 15) 第三步模拟审核
        testOrderComplete(erpToken);

        // 16) 第四步培训审核（operation=8）
        newDriverAuthOperation(erpToken, 8, "{\"operation\":8,\"status\":1,\"userId\":%d,\"trainCompleteReason\":\"test\"}");

        // 17) 最后一步入驻（operation=9）
        finalSettleIn(erpToken);

        // 18) 入驻后修改密码
        modifyDriverPassword(erpToken);
    }

    private void preparePhoneNumber() {
        Map<String, Object> result = PandaTestDBHelpful.executeSelectOneSql(
                "select * from `panda_test`.`hp_delivery_user` where user_telphone like '%9901%' order by user_id desc limit 1");
        if (result != null && result.get("user_telphone") != null) {
            String oldTel = result.get("user_telphone").toString();
            long oldTelNum = Long.parseLong(oldTel);
            newTel = String.valueOf(oldTelNum + 1);
        } else {
            newTel = "13300099010";
        }
    }

    private void insertVerificationCode() {
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

    private void registerNewDriver() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        Map<String, Object> headers = createDriverAppHeaders();

        String body = String.format("{\"country\":\"CN\",\"areaCode\":\"86\",\"password\":\"a9e18b3c663c627bd030c06fae4fe288\",\"captcha\":\"123456\",\"cityId\":729,\"account\":\"%s\",\"invitationCode\":\"R480I9IH\"}", newTel);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);

        newUserId = Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString());
        accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }

    private void driverBaseInfo() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/driverBaseInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void essentialInfoDriver() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/essentialInfoDriver";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        String body = "{\"workDate\":2,\"source\":2,\"workHour\":2,\"experience\":1,\"workTime\":[1],\"vehicleType\":0}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void loadGlobalConfig() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/config/loadGlobalConfig";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void bindAlias() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/jPush/bindAlias";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        String body = "{\"registrationId\":\"170976fa8bbc96d1988\",\"alias\":\"472be5fcdcc4bb0002a8f539122c5f7d\",\"type\":2}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void newDriverBannerList() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driverCenter/newList";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void checkRegisterDriverStatus() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/checkRegisterDriverStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void loadAreaByToken() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/common/loadAreaByToken";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void certificationInfo() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/certificationInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void saveCertificationInfo() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/saveCertificationInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        // 直接复用 Apifox raw body（字段较多）
        String body = "{\"regoExpirationDate\":\"2055-11-29\",\"lastName\":\"Kasp\",\"certificatesExpirationDate\":\"2055-07-26\",\"certificatesHandlePhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482552322309c47f463ee84408eb9f3193c969111c8.jpg\",\"needCertificate\":\"1\",\"driverBirthday\":\"2006-08-23\",\"drivingLicensePhotoFrontUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255281119d91197320a484732bbd9b647a8b4a8a7.jpg\",\"personRace\":\"0\",\"caWorkPermit\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748263128130686882d341a64bfd9417568424f4cf43.jpg\",\"drivingLicensePhotoFrontUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482631438528af3d3114e214fdc9aad46dd0cbe673b.jpg\",\"certificatesPhotoBackUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260638330cf19d68d83564832aff667275486b25c.jpg\",\"caWorkPermitDate\":\"2055-11-29\",\"address\":\"滨江\",\"jpTrafficInsurance\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260713869f64a5d94c3704457ad9eab6b48218e91.jpg\",\"studyIdCartUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482606475826662d70843454c30ad9942a5c34797ee.jpg\",\"weekHoursPer\":\"2\",\"areaIds\":\"919\",\"timeType\":\"1\",\"jpHealthInsuranceDate\":\"2025-11-29\",\"jpTrafficInsuranceDate\":\"2055-01-31\",\"extraitKibsPhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1747830596842235e4e95921b4f91a80779682196ee28.jpg\",\"drivingLicensePhotoBackUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17478306218264ddc2ab951a24099b02eb6a58bddeaad.jpg\",\"irdNumber\":\"9909877677\",\"cbtUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174826237604174f2feb1089d44498aef17d6d619975f.jpg\",\"isRegGst\":\"1\",\"socialInsuranceNumber\":\"888899\",\"caWorkPermitType\":\"0\",\"cbtExpirationDate\":\"2055-07-26\",\"landingPermitNumber\":\"1234567890123\",\"landingPermitType\":\"4\",\"areaIds\":\"919\",\"residentialArea\":\"919\",\"residenceCardType\":\"3\",\"lightVestUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825527135254e6d5625075498cb72303b74f7e9a54.jpg\",\"lightVest\":\"1\",\"certificatesPhotoFrontUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255187773cf0fb0567ad04b0f9972c0f1e0613fb8.jpg\",\"email\":\"1351792983@qq.com\",\"vehicleType\":\"0\",\"vehicleLicense\":\"112233\",\"visaUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255211931e96ec00d192e40c0a1f5cf7fb2024d26.jpg\",\"visaDate\":\"2055-02-28\",\"visaNumber\":\"123456789\",\"certificationInfoStatus\":2,\"passportType\":\"1\",\"regoCtpPhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825524690886fd6dee90bf4f1888d2d0c523fba16d.jpg\",\"jpHealthInsurance\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174826068932072d33f1171454409a495312ddfe546c7.jpg\",\"certificatesPhotoType\":\"0\",\"visaType\":\"2\",\"firstName\":\"auto\",\"drivingLicenseExpirationDate\":\"2055-10-31\",\"residentAddress\":\"滨江地址\"}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void newDriverAuthOperation(String erpToken, int operation, String template) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        Map<String, Object> headers = createErpHeaders(erpToken);

        String body = String.format(template, newUserId);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void submitBackgroundCheck() {
        String uri = "https://api-cn-f2e-test.hungrypanda.cn/api/delivery/app/auth/auth/background";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);

        long commitTime = System.currentTimeMillis();
        String body = String.format("{\"phone\":\"%s\",\"email\":\"ghk66yk@163.com\",\"firstName\":\"iik\",\"middleName\":\"\",\"lastName\":\"hj\",\"driverLicenseState\":\"WI\",\"driverLicenseNumber\":\"\",\"confirmDriverLicenseNumber\":\"\",\"driverLicenseExpirationDate\":\"\",\"socialSecurityNumber\":\"1234\",\"confirmSocialSecurityNumber\":\"1234\",\"zipCode\":\"88888\",\"birthday\":\"1992-09-26\",\"runType\":2,\"city\":\"日照市\",\"country\":\"中国\",\"copyRequested\":1,\"driverId\":%d,\"ssn\":\"\",\"postalAddressName\":\"\",\"postalAddressStreet\":\"\",\"postalAddressStreet2\":\"\",\"postalAddressCity\":\"\",\"postalAddressState\":\"\",\"commitTime\":%d,\"backgroundStatus\":0,\"authStatus\":-1,\"authTime\":0,\"authOperator\":\"\",\"candidatesId\":\"\",\"reportsId\":\"\",\"reportsResult\":\"\"}", newTel, newUserId, commitTime);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void testOrderComplete(String erpToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/authDriver/testOrderComplete";
        Map<String, Object> headers = createErpHeaders(erpToken);

        String body = String.format("{\"driverId\":%d,\"reason\":\"test\"}", newUserId);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void finalSettleIn(String erpToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        Map<String, Object> headers = createErpHeaders(erpToken);

        String body = String.format("{\"userId\":%d,\"operation\":9,\"status\":1}", newUserId);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void modifyDriverPassword(String erpToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/deliveryman/" + newUserId;
        Map<String, Object> headers = createErpHeaders(erpToken);

        String body = "{\"op\":\"pwd_modify\",\"newPassword\":\"Test1234\"}";
        var responseBody = TestCaseHelpful.sendRequest("PATCH", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.21674955924935");
        headers.put("latitude", "30.20344076263413");
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
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    private Map<String, Object> createErpHeaders(String erpToken) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", erpToken);
        headers.put("priority", "u=1, i");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

