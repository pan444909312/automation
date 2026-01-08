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
 * 【常用】随机手机号注册 -非澳大利亚国家
 *
 * @author penglulu@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JWG1VHMW2Y9GKMY8P0YXVHB2",
        scenarioName = "骑手APP-骑手注册提交认证材料",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 600, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("【常用】随机手机号注册 -非澳大利亚国家")
public class RandomPhoneRegisterNonAustraliaTests {

    private String newTel;
    private Long newUserId;
    private String accessToken;

    @DisplayName("随机手机号注册完整流程")
    @Test
    void shouldRegisterDriverWithRandomPhone() {
        // 0) 前置操作：从数据库获取最大手机号并+1
        preparePhoneNumber();

        // 1) 前置操作：插入验证码到数据库
        insertVerificationCode();

        // 2) 注册页 -随机手机号验证码提交
        registerNewDriver();

        // 3) 注册-欢迎页
        getDriverBaseInfo();

        // 4) 再需3步就可以跑单弹窗
        getEssentialInfoDriver();

        // 5) bindAlias接口
        bindAlias();

        // 6) 新骑手banner列表
        getNewDriverBannerList();

        // 7) 获取当前审核进度
        checkRegisterDriverStatus();

        // 8) 根据token获取全部区域信息
        loadAreaByToken();

        // 9) 点击去认证-进入骑手认证页面
        getCertificationInfo();

        // 10) 骑手认证信息填写完成提交审核
        saveCertificationInfo();
    }

    /**
     * 从数据库获取最大手机号并+1（查询user_telphone like '%9901%'）
     */
    private void preparePhoneNumber() {
        Map<String, Object> result = PandaTestDBHelpful.executeSelectOneSql(
                "select * from `panda_test`.`hp_delivery_user` where user_telphone like '%9901%' order by user_id desc limit 1");
        if (result != null && result.get("user_telphone") != null) {
            String oldTel = result.get("user_telphone").toString();
            long oldTelNum = Long.parseLong(oldTel);
            newTel = String.valueOf(oldTelNum + 1);
        } else {
            // 如果没有找到，使用默认值
            newTel = "13300099010";
        }
    }

    /**
     * 插入验证码到数据库
     */
    private void insertVerificationCode() {
        long nowTime = System.currentTimeMillis();
        long futureTime = nowTime + 1200000; // 20分钟后
        
        String sql = String.format(
                "insert into `panda_test`.`hp_delivery_user_log` " +
                "(`login_ip`, `create_time`, `user_id`, `user_type`, `telephone`, `macId`, `verify_code`, " +
                "`user_name`, `access_token`, `expires_in`, `refresh_token`, `over_time`, `area_code`, " +
                "`device_id`, `user_name_mask`, `telephone_mask`, `content_mask`) " +
                "values ('157.15.28.49, 162.158.114.173', %d, '0', '3', '%s', '', '123456', '', '', '0', '', %d, '86', '', '', '', '')",
                nowTime, newTel, futureTime);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
    }

    /**
     * 注册页 -随机手机号验证码提交
     */
    private void registerNewDriver() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = String.format(
                "{\"country\":\"CN\",\"areaCode\":\"86\",\"password\":\"a9e18b3c663c627bd030c06fae4fe288\",\"captcha\":\"123456\",\"cityId\":1,\"account\":\"%s\",\"invitationCode\":\"\"}",
                newTel);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        newUserId = Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString());
        accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }

    /**
     * 注册-欢迎页
     */
    private void getDriverBaseInfo() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/driverBaseInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        // 后置操作：查询骑手注册状态为待提交基础信息
        verifyInfoStatus(0);
    }

    /**
     * 再需3步就可以跑单弹窗
     */
    private void getEssentialInfoDriver() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/essentialInfoDriver";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        String body = "{\"workDate\":2,\"source\":2,\"workHour\":2,\"experience\":1,\"workTime\":[1],\"vehicleType\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * bindAlias接口
     */
    private void bindAlias() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/jPush/bindAlias";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        String body = "{\"registrationId\":\"170976fa8bbc96d1988\",\"alias\":\"472be5fcdcc4bb0002a8f539122c5f7d\",\"type\":2}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 新骑手banner列表
     */
    private void getNewDriverBannerList() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driverCenter/newList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 获取当前审核进度
     */
    private void checkRegisterDriverStatus() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/checkRegisterDriverStatus";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        // 后置操作：查询骑手认证状态为待提交信息
        verifyInfoStatus(10);
    }

    /**
     * 根据token获取全部区域信息
     */
    private void loadAreaByToken() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/common/loadAreaByToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 点击去认证-进入骑手认证页面
     */
    private void getCertificationInfo() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/certificationInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        // 后置操作：查询骑手认证状态为待提交信息
        verifyInfoStatus(10);
    }

    /**
     * 骑手认证信息填写完成提交审核
     */
    private void saveCertificationInfo() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/saveCertificationInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        // 注意：这里使用JSON中的完整请求体，包含所有认证信息字段
        String body = "{\"regoExpirationDate\":\"2030-11-29\",\"lastName\":\"Griffin\",\"certificatesExpirationDate\":\"2030-07-26\"," +
                "\"certificatesHandlePhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482552322309c47f463ee84408eb9f3193c969111c8.jpg\"," +
                "\"needCertificate\":\"1\",\"driverBirthday\":\"1983-02-10\"," +
                "\"drivingLicensePhotoFrontUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255281119d91197320a484732bbd9b647a8b4a8a7.jpg\"," +
                "\"personRace\":\"0\",\"caWorkPermit\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748263128130686882d341a64bfd9417568424f4cf43.jpg\"," +
                "\"certificatesPhotoBackUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260638330cf19d68d83564832aff667275486b25c.jpg\"," +
                "\"caWorkPermitDate\":\"2035-11-29\",\"address\":\"滨江\"," +
                "\"jpTrafficInsurance\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260713869f64a5d94c3704457ad9eab6b48218e91.jpg\"," +
                "\"studyIdCartUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482606475826662d70843454c30ad9942a5c34797ee.jpg\"," +
                "\"weekHoursPer\":\"2\",\"areaIds\":\"163,488\",\"timeType\":\"1\"," +
                "\"jpHealthInsuranceDate\":\"2035-11-29\",\"jpTrafficInsuranceDate\":\"2036-01-31\"," +
                "\"extraitKibsPhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1747830596842235e4e95921b4f91a80779682196ee28.jpg\"," +
                "\"drivingLicensePhotoBackUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17478306218264ddc2ab951a24099b02eb6a58bddeaad.jpg\"," +
                "\"irdNumber\":\"9909877677\",\"cbtUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174826237604174f2feb1089d44498aef17d6d619975f.jpg\"," +
                "\"isRegGst\":\"1\",\"socialInsuranceNumber\":\"888899\",\"caWorkPermitType\":\"0\"," +
                "\"cbtExpirationDate\":\"2035-07-26\",\"landingPermitNumber\":\"1234567890123\",\"landingPermitType\":\"4\"," +
                "\"residentialArea\":\"163\",\"residenceCardType\":\"3\"," +
                "\"lightVestUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825527135254e6d5625075498cb72303b74f7e9a54.jpg\"," +
                "\"lightVest\":\"1\"," +
                "\"certificatesPhotoFrontUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255187773cf0fb0567ad04b0f9972c0f1e0613fb8.jpg\"," +
                "\"email\":\"1351792983@qq.com\",\"vehicleType\":\"0\",\"vehicleLicense\":\"112233\"," +
                "\"visaUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255211931e96ec00d192e40c0a1f5cf7fb2024d26.jpg\"," +
                "\"visaDate\":\"2036-02-28\",\"visaNumber\":\"123456789\",\"certificationInfoStatus\":2," +
                "\"passportType\":\"1\",\"regoCtpPhotoUrl\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825524690886fd6dee90bf4f1888d2d0c523fba16d.jpg\"," +
                "\"jpHealthInsurance\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174826068932072d33f1171454409a495312ddfe546c7.jpg\"," +
                "\"certificatesPhotoType\":\"0\",\"visaType\":\"2\",\"firstName\":\"Peter\"," +
                "\"drivingLicenseExpirationDate\":\"2035-10-31\",\"residentAddress\":\"滨江地址\"}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        // 后置操作：查询骑手认证状态为待审核
        verifyInfoStatus(20);
    }

    /**
     * 验证骑手认证状态
     */
    private void verifyInfoStatus(int expectedStatus) {
        Map<String, Object> result = PandaTestDBHelpful.executeSelectOneSql(
                "select info_status from `panda_test`.`hp_delivery_driver_information` where user_id = ?",
                newUserId);
        if (result != null && result.get("info_status") != null) {
            int infoStatus = ((Number) result.get("info_status")).intValue();
            assert infoStatus == expectedStatus : String.format("期望info_status为%d，实际为%d", expectedStatus, infoStatus);
        }
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216806");
        headers.put("latitude", "30.203427");
        headers.put("version", "5.68.3");
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

