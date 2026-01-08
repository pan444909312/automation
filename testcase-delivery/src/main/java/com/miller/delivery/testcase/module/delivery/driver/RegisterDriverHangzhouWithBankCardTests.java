package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 杭州-注册骑手-有银行卡
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KEDNG43262W23GSYBG299HE8", // 需要从质量平台获取实际的 scenarioID
        scenarioName = "杭州-注册骑手-有银行卡",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 300, maintenanceTime = 0, manualTestTime = 120)
@DisplayName("杭州-注册骑手-有银行卡")
public class RegisterDriverHangzhouWithBankCardTests {

    private String newTel;
    private String newTelLast4;
    private Long newUserId;
    private String accessToken;
    private String userTelPhone;
    private String siGuanToken;

    @DisplayName("完整端到端流程-杭州注册骑手-有银行卡")
    @Test
    void shouldRegisterDriverHangzhouWithBankCard() {
        // ========== 第一部分：准备数据 ==========
        // 步骤1: 从数据库获取最大手机号并+1
        preparePhoneNumber();
        
        // 步骤2: 插入验证码到数据库
        insertVerificationCode();
        
        // ========== 第二部分：骑手app注册流程 ==========
        // 步骤3: 注册页 -随机手机号验证码提交
        registerNewDriver();
        
        // 步骤4: 注册-欢迎页
        getDriverBaseInfo();
        
        // 步骤5: 再需3步就可以跑单弹窗
        submitEssentialInfoDriver();
        
        // 步骤6: 获取全部配置接口
        loadGlobalConfig();
        
        // 步骤7: bindAlias接口
        bindAlias();
        
        // 步骤8: 新骑手banner列表
        getNewDriverBannerList();
        
        // 步骤9: 获取当前审核进度
        checkRegisterDriverStatus();
        
        // 步骤10: 根据token获取全部区域信息
        loadAreaByToken();
        
        // 步骤11: 点击去认证-进入骑手认证页面
        getCertificationInfo();
        
        // 步骤12: 骑手认证信息填写完成提交审核
        saveCertificationInfo();
        
        // ========== 第三部分：司管后台审核流程 ==========
        // 步骤13: 司管后台登录
        siGuanToken = erpLogin();
        
        // 步骤14: 司管第一步审核
        firstStepAudit();
        
        // 步骤15: 第二步审核
        secondStepAudit();
        
        // 步骤16: 第三部模拟审核
        thirdStepSimulateAudit();
        
        // 步骤17: 第四部培训审核
        fourthStepTrainingAudit();
        
        // 步骤18: 最后一步入驻
        finalStepSettlement();
        
        // 步骤19: 入驻后修改密码
        modifyPasswordAfterSettlement();
        
        // ========== 第四部分：添加银行卡 ==========
        // 步骤20: 添加银行卡
        addBankCard();
    }

    /**
     * 从数据库获取最大手机号并+1
     */
    private void preparePhoneNumber() {
        Map<String, Object> result = PandaTestDBHelpful.executeSelectOneSql(
                "select * from `panda_test`.`user` where user_telphone like '133000%' order by user_telphone desc limit 1");
        if (result != null && result.get("user_telphone") != null) {
            String oldTel = result.get("user_telphone").toString();
            long oldTelNum = Long.parseLong(oldTel);
            newTel = String.valueOf(oldTelNum + 1);
        } else {
            // 如果没有找到，使用默认值
            newTel = "13300010000";
        }
        newTelLast4 = newTel.length() >= 4 ? newTel.substring(newTel.length() - 4) : newTel;
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
                "values ('157.15.28.49, 23.15.245.199, 23.47.121.198', %d, 0, 3, '%s', '', 123456, '', '', 0, '', %d, '86', '', '', '', '')",
                nowTime, userTelPhone != null ? userTelPhone : newTel, futureTime);
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
                "{\"country\":\"CN\",\"areaCode\":\"86\",\"password\":\"a9e18b3c663c627bd030c06fae4fe288\",\"captcha\":\"123456\",\"cityId\":1,\"account\":\"%s\",\"invitationCode\":\"LGAH7ETT\"}",
                newTel);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        newUserId = Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString());
        accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
        userTelPhone = TestCaseHelpful.extractValue(responseBody, "$.result.userTelPhone").toString();
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
    }

    /**
     * 再需3步就可以跑单弹窗
     */
    private void submitEssentialInfoDriver() {
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
     * 获取全部配置接口
     */
    private void loadGlobalConfig() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/config/loadGlobalConfig";
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
    }

    /**
     * 骑手认证信息填写完成提交审核
     */
    private void saveCertificationInfo() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/saveCertificationInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        String body = String.format(
                "{\n" +
                "  \"regoExpirationDate\": \"2029-11-29\",\n" +
                "  \"lastName\": \"自动化使用\",\n" +
                "  \"certificatesExpirationDate\": \"2029-07-26\",\n" +
                "  \"certificatesHandlePhotoUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482552322309c47f463ee84408eb9f3193c969111c8.jpg\",\n" +
                "  \"needCertificate\": \"1\",\n" +
                "  \"driverBirthday\": \"1983-02-10\",\n" +
                "  \"drivingLicensePhotoFrontUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255281119d91197320a484732bbd9b647a8b4a8a7.jpg\",\n" +
                "  \"personRace\": \"0\",\n" +
                "  \"caWorkPermit\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748263128130686882d341a64bfd9417568424f4cf43.jpg\",\n" +
                "  \"certificatesPhotoBackUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260638330cf19d68d83564832aff667275486b25c.jpg\",\n" +
                "  \"caWorkPermitDate\": \"2029-11-29\",\n" +
                "  \"address\": \"滨江\",\n" +
                "  \"jpTrafficInsurance\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748260713869f64a5d94c3704457ad9eab6b48218e91.jpg\",\n" +
                "  \"studyIdCartUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17482606475826662d70843454c30ad9942a5c34797ee.jpg\",\n" +
                "  \"weekHoursPer\": \"2\",\n" +
                "  \"areaIds\": \"51\",\n" +
                "  \"timeType\": \"1\",\n" +
                "  \"jpHealthInsuranceDate\": \"2029-11-29\",\n" +
                "  \"jpTrafficInsuranceDate\": \"2026-01-31\",\n" +
                "  \"extraitKibsPhotoUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1747830596842235e4e95921b4f91a80779682196ee28.jpg\",\n" +
                "  \"drivingLicensePhotoBackUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17478306218264ddc2ab951a24099b02eb6a58bddeaad.jpg\",\n" +
                "  \"irdNumber\": \"9909877677\",\n" +
                "  \"cbtUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174826237604174f2feb1089d44498aef17d6d619975f.jpg\",\n" +
                "  \"isRegGst\": \"1\",\n" +
                "  \"socialInsuranceNumber\": \"888899\",\n" +
                "  \"caWorkPermitType\": \"0\",\n" +
                "  \"cbtExpirationDate\": \"2029-07-26\",\n" +
                "  \"landingPermitNumber\": \"1234567890123\",\n" +
                "  \"landingPermitType\": \"4\",\n" +
                "  \"residentialArea\": \"51\",\n" +
                "  \"residenceCardType\": \"3\",\n" +
                "  \"lightVestUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825527135254e6d5625075498cb72303b74f7e9a54.jpg\",\n" +
                "  \"lightVest\": \"1\",\n" +
                "  \"certificatesPhotoFrontUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255187773cf0fb0567ad04b0f9972c0f1e0613fb8.jpg\",\n" +
                "  \"email\": \"chenchunxia@hungrypandagroup.com\",\n" +
                "  \"vehicleType\": \"1\",\n" +
                "  \"vehicleLicense\": \"112233\",\n" +
                "  \"visaUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1748255211931e96ec00d192e40c0a1f5cf7fb2024d26.jpg\",\n" +
                "  \"visaDate\": \"2026-02-28\",\n" +
                "  \"visaNumber\": \"123456789\",\n" +
                "  \"certificationInfoStatus\": 2,\n" +
                "  \"passportType\": \"1\",\n" +
                "  \"regoCtpPhotoUrl\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174825524690886fd6dee90bf4f1888d2d0c523fba16d.jpg\",\n" +
                "  \"jpHealthInsurance\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/174826068932072d33f1171454409a495312ddfe546c7.jpg\",\n" +
                "  \"certificatesPhotoType\": \"0\",\n" +
                "  \"visaType\": \"2\",\n" +
                "  \"firstName\": \"%s\",\n" +
                "  \"drivingLicenseExpirationDate\": \"2029-10-31\",\n" +
                "  \"residentAddress\": \"滨江地址\"\n" +
                "}", newTelLast4);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 司管第一步审核
     */
    private void firstStepAudit() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        String body = String.format("{\"operation\":2,\"status\":1,\"userId\":%d}", newUserId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 第二步审核
     */
    private void secondStepAudit() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        String body = String.format("{\"operation\":3,\"status\":1,\"userId\":%d}", newUserId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 第三部模拟审核
     */
    private void thirdStepSimulateAudit() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/authDriver/testOrderComplete";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        String body = String.format("{\"driverId\":%d,\"reason\":\"test\"}", newUserId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 第四部培训审核
     */
    private void fourthStepTrainingAudit() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        String body = String.format("{\"operation\":8,\"status\":1,\"userId\":%d,\"trainCompleteReason\":\"test\"}", newUserId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 最后一步入驻
     */
    private void finalStepSettlement() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/newDriverAuthOperation";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        String body = String.format("{\"userId\":%d,\"operation\":9,\"status\":1}", newUserId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 入驻后修改密码
     */
    private void modifyPasswordAfterSettlement() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/deliveryman/" + newUserId;
        String method = "PATCH";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);
        
        String body = "{\"op\":\"pwd_modify\",\"newPassword\":\"Test1234\"}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 添加银行卡
     */
    private void addBankCard() {
        // 先插入验证码（用于添加银行卡时的验证）
        long nowTime = System.currentTimeMillis();
        long futureTime = nowTime + 1200000;
        String sql = String.format(
                "INSERT INTO `panda_test`.`hp_delivery_user_log`" +
                "(`login_ip`, `create_time`, `user_id`, `user_type`, `telephone`, `macId`, `verify_code`, " +
                "`user_name`, `access_token`, `expires_in`, `refresh_token`, `over_time`, `area_code`, " +
                "`device_id`, `user_name_mask`, `telephone_mask`, `content_mask`) " +
                "VALUES ('157.15.28.49, 23.15.245.199, 23.47.121.198', %d, 0, 3, '%s', '', 123456, '', '', 0, '', %d, '86', '', '', '', '')",
                nowTime, userTelPhone, futureTime);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
        
        // 添加银行卡
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/bank/modifyBank";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", accessToken);
        
        String body = "{\"accountName\":\"111\",\"accountNumber\":\"123456\",\"type\":0,\"sortCode\":\"111111\",\"checkCode\":\"123456\"}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
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

    /**
     * 创建ERP请求头
     */
    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"138\", \"Google Chrome\";v=\"138\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

