package com.miller.delivery.testcase.module.deliveryAdmin.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-修改骑手信息
 */
@Scenario(
        scenarioID = "01JNNE6CKKGMB6N5ZDAPCY5YXD",
        scenarioName = "【主干场景】司管后台-骑手列表-修改骑手信息",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("修改骑手信息")
public class DriverModifyInfoTests {

    @DisplayName("修改骑手信息和银行卡")
    @Test
    void shouldModifyDriverInfo() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 等待5秒
        Thread.sleep(5000);

        // 2) 修改骑手信息
        modifyDriverBaseDetail(token);

        // 等待1秒
        Thread.sleep(1000);

        // 3) 修改银行卡
        modifyDriverBankDetail(token);
    }

    private void modifyDriverBaseDetail(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/saveDriverBaseDetail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"information\":{\"certificatesPhotoFrontUrl\":\"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1741170141256f81c6abbae764bfb85b95068f8802d631741170138392_0.png\",\"certificatesPhotoBackUrl\":\"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1741170146888707ede0cab864dae829e4beb983037c71741170144092_6.png\",\"certificatesPhotoType\":1,\"certificatesExpirationDate\":\"2029-03-23\",\"certificatesExpiredStatus\":0,\"certificatesHandlePhotoUrl\":null,\"drivingLicensePhotoFrontUrl\":\"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1741170160662928c1b1e6ec749898faa162b7a1c31b81741170157950_0.png\",\"drivingLicensePhotoBackUrl\":null,\"drivingLicenseExpirationDate\":\"2028-03-24\",\"drivingLicenseExpiredStatus\":0,\"cbtUrl\":\"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/17411701521487de67f89400a43b0a6b0cc94ccc9b0d21741170149335_9.png\",\"cbtExpirationDate\":\"2029-03-22\",\"cbtExpiredStatus\":0,\"vehicleLicense\":null,\"extraitKibsPhotoUrl\":null,\"extraitKibsExpiredStatus\":0,\"regoCtpPhotoUrl\":null,\"regoExpirationDate\":null,\"regoExpiredStatus\":0,\"ctpExpirationDate\":null,\"ctpExpiredStatus\":0,\"socialInsuranceNumber\":null,\"jpHealthInsurance\":null,\"jpHealthInsuranceDate\":null,\"jpHealthInsuranceExpiredStatus\":0,\"jpTrafficInsurance\":null,\"jpTrafficInsuranceDate\":null,\"jpTrafficInsuranceExpiredStatus\":0,\"caWorkPermitType\":null,\"caWorkPermit\":null,\"caWorkPermitDate\":null,\"caWorkPermitExpiredStatus\":0,\"personRace\":0,\"passportType\":null,\"visaType\":null,\"visaDate\":null,\"visaNumber\":null,\"visaUrl\":null,\"visaUrlExpiredStatus\":0,\"ieWorkPermit\":null,\"ieWorkPermitDate\":null,\"ieWorkPermitExpiredStatus\":0,\"ieIbt\":null,\"ieIbtDate\":null,\"ieIbtExpiredStatus\":0,\"reason\":null,\"needCertificate\":0,\"driverBirthday\":\"2024-12-25\",\"residentAddress\":\"暂无\",\"cultivatePlanList\":null,\"landingPermitNumber\":null,\"landingPermitType\":null,\"alcoholLicense\":null,\"alcoholLicenseDate\":null,\"alcoholLicenseExpiredStatus\":0,\"residenceCardType\":null,\"weekHoursPer\":null,\"studyIdCartUrl\":null,\"studyIdCartExpiredStatus\":0},\"needCertificate\":0,\"driverBirthday\":\"2024-12-25\",\"residentAddress\":\"\",\"whatsApp\":0,\"grantEquip\":0,\"equipmentType\":1,\"isShowHeatMap\":1,\"personRace\":0,\"userId\":1398714150,\"firstName\":\"自动化测试\",\"lastName\":\"【勿动】\",\"accountType\":0,\"country\":\"中国\",\"cityId\":1,\"city\":\"杭州市\",\"address\":\"滨江\",\"telephone\":\"19539027924\",\"areaCode\":\"86\",\"email\":\"11@qq.com\",\"source\":4,\"sourceStr\":\"FaceBook\",\"sourceRemark\":\"\",\"experience\":0,\"areaId\":[51,163,436,470,476],\"outAreaOnline\":1,\"workType\":1,\"workDate\":0,\"workHour\":0,\"workTime\":[],\"vehicle\":4,\"vehicleLicense\":null,\"hpReceiveNum\":22,\"packageWeight\":50,\"specialDriver\":0,\"isNoviceActivity\":1,\"driverBusinessType\":0,\"siteIdList\":[],\"languageCode\":[\"zh-CN\"],\"canInsteadOrder\":1,\"residentialArea\":\"\",\"depositNo\":\"\",\"isRegGst\":0,\"isThirdCompany\":0,\"userName\":\"自动化测试【勿动】\",\"tag\":[8,7],\"employeeType\":null,\"attendance\":0,\"goodReview\":0,\"punctually\":0,\"acceptance\":0,\"avatar\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1735638607322d0a9405df7d14c159139d79560effb37.jpg\",\"areaIdList\":null,\"contactStatus\":1,\"infoStatus\":60,\"infoStatusDesc\":\"信息通过\",\"pfReceiveNum\":null,\"hasCredential\":false,\"birthday\":\"\"}";

        TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 注意：这个接口可能没有返回 code 和 message，需要根据实际响应调整断言
    }

    private void modifyDriverBankDetail(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/saveDriverBankDetail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cardholder\":\"DSA\",\"accountNumber\":\"12345678\",\"sortCode\":\"125555\",\"bsb\":null,\"australiaBusinessNumber\":null,\"routingNumber\":null,\"bankNumber\":null,\"branchNumber\":null,\"swiftCode\":null,\"iban\":null,\"bankName\":\"\",\"branchName\":\"\",\"irdNumber\":null,\"physicalAddress\":\"\",\"depositType\":\"\",\"userId\":\"1398714150\",\"insteadOrderBank\":null,\"salaryConfig\":0,\"isTipShow\":1}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();
         
        headers.put("Content-Type", "application/json");
        String body = "{\"password\":\"d9501f93554734ba83d19c9dc83ef4fb\",\"userName\":\"ding023660390221528503\"}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
         
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

