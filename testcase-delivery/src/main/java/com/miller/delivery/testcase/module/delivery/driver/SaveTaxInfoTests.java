package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存税务信息（未完成，有报错）
 *
 * @author penglulu@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JXJ0C8XAXJF5DBX9BNW3W32M",
        scenarioName = "获取骑手报税详情",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("保存税务信息（未完成，有报错）")
public class SaveTaxInfoTests {

    // 注意：需要在实际使用时替换为真实的driverId
    private static final Long DRIVER_ID = 2086355L; // 请从质量平台或实际业务中获取

    @DisplayName("保存税务信息")
    @Test
    void shouldSaveTaxInfo() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 保存税务信息
        saveTax(driverAccessToken);
    }

    /**
     * 保存税务信息
     * 注意：此接口使用formdata格式，并且有query参数
     */
    private void saveTax(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/tax/saveTax";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        // 注意：此接口使用query参数传递DeliveryDriverTaxInfoSaveReq对象
        // 根据JSON，query参数格式为：driverId=2086355, email=null, firstName=liuping, ...
        // 由于Java中处理这种复杂query参数较为复杂，这里使用空body（formdata为空）
        // 实际使用时可能需要根据接口文档调整
        Map<String, String> formData = new HashMap<>();
        
        // 构建query参数
        Map<String, Object> queryParams = new HashMap<>();
        String queryValue = String.format(
                "driverId=%d, email=null, firstName=liuping, middleName=, lastName=huang, companyName=, " +
                "federalTaxClassification=0, federalTaxClassificationDesc=, address=1328 El Camino Real %%232, " +
                "city=Burlingam, state=CA, zipCode=94101, tinType=1, socialSecurityNumber=842417302, " +
                "signedPicture=http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/175143604220377743188d1a54d9cacb48d0c9a6f7b78.png, finalPage=1",
                DRIVER_ID);
        queryParams.put("DeliveryDriverTaxInfoSaveReq", queryValue);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, queryParams, headers, formData);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216775");
        headers.put("latitude", "30.203361");
        headers.put("version", "5.64.0");
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
        // 注意：formdata请求通常不需要content-type，或者使用multipart/form-data
        // 但根据JSON，这里没有设置content-type，所以不添加
        return headers;
    }
}

