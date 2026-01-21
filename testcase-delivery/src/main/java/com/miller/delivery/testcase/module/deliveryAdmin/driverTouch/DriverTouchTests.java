package com.miller.delivery.testcase.module.deliveryAdmin.driverTouch;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-手动给骑手发触达
 */
@Scenario(
        scenarioID = "01K0BKNCTAQ3045V2C9N55K9JC",
        scenarioName = "手动给骑手发送触达",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("手动给骑手发触达")
public class DriverTouchTests {

    private static final Long DRIVER_ID = 1398717294L; // 假设一个driverId

    @DisplayName("应用内触达创建并审批")
    @Test
    void shouldCreateAndApproveInAppTouch() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 应用内触达创建
        String createUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/updateData";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String createBody = "{\n" +
                "    \"id\": null,\n" +
                "    \"driverIds\": [\n" +
                "        " + DRIVER_ID + "\n" +
                "    ],\n" +
                "    \"popupUrl\": null,\n" +
                "    \"informationUrl\": \"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1752734448525c8758263d91042db81473799b96ce898blob\",\n" +
                "    \"msgType\": 1,\n" +
                "    \"cityNameList\": [\n" +
                "        \"杭州市\"\n" +
                "    ],\n" +
                "    \"pushModeType\": 0,\n" +
                "    \"centerShowType\": 1,\n" +
                "    \"pushTimeType\": 0,\n" +
                "    \"pushTime\": null,\n" +
                "    \"title\": \"应用内推送\",\n" +
                "    \"summary\": \"摘要\",\n" +
                "    \"jumpAddressType\": 1,\n" +
                "    \"linkAddress\": \"www.baidu.com\",\n" +
                "    \"markdownContent\": null\n" +
                "}";
        var createResponseBody = TestCaseHelpful.sendRequest(method, createUri, null, headers, createBody);
        TestCaseHelpful.assertThatJson(createResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(createResponseBody)
                .node("message").isEqualTo("成功");

        // 3) 等待1秒
        Thread.sleep(1000);

        // 4) 待审批列表-获取ID
        String queryUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/queryData";
        String queryBody = "{\"title\":null,\"cityNameList\":[],\"pushStartTime\":null,\"pushEndTime\":null,\"pushModeType\":[],\"centerShowType\":null,\"createName\":null,\"pageNo\":1,\"pageSize\":10,\"queryType\":null,\"msgType\":null}";
        var queryResponseBody = TestCaseHelpful.sendRequest(method, queryUri, null, headers, queryBody);
        TestCaseHelpful.assertThatJson(queryResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(queryResponseBody)
                .node("message").isEqualTo("成功");

        String touchId = TestCaseHelpful.extractValue(queryResponseBody, "$.data.list.[0].id").toString();

        // 5) 等待1秒
        Thread.sleep(1000);

        // 6) 触达审批通过
        String reviewUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/review";
        String reviewBody = "{\"id\":\"" + touchId + "\",\"driverIds\":[" + DRIVER_ID + "],\"popupUrl\":\"\",\"informationUrl\":\"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1752734448525c8758263d91042db81473799b96ce898blob\",\"msgType\":1,\"cityNameList\":[\"杭州市\"],\"pushModeType\":0,\"centerShowType\":1,\"pushTimeType\":0,\"pushTime\":null,\"title\":\"应用内推送\",\"summary\":\"摘要\",\"jumpAddressType\":1,\"linkAddress\":\"www.baidu.com\",\"markdownContent\":null,\"reviewedStatus\":1}";
        var reviewResponseBody = TestCaseHelpful.sendRequest(method, reviewUri, null, headers, reviewBody);
        TestCaseHelpful.assertThatJson(reviewResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(reviewResponseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("系统推送触达创建并审批")
    @Test
    void shouldCreateAndApproveSystemPushTouch() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 系统推送-触达创建
        String createUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/updateData";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String createBody = "{\n" +
                "    \"id\": null,\n" +
                "    \"driverIds\": [\n" +
                "        " + DRIVER_ID + "\n" +
                "    ],\n" +
                "    \"popupUrl\": null,\n" +
                "    \"informationUrl\": \"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/175273782879589e76c5ef52c4c3a8e5000ab1a83a33eblob\",\n" +
                "    \"msgType\": 1,\n" +
                "    \"cityNameList\": [\n" +
                "        \"杭州市\"\n" +
                "    ],\n" +
                "    \"pushModeType\": 1,\n" +
                "    \"centerShowType\": 1,\n" +
                "    \"pushTimeType\": 0,\n" +
                "    \"pushTime\": null,\n" +
                "    \"title\": \"首页推送触达\",\n" +
                "    \"summary\": \"首页推送触达内容\",\n" +
                "    \"jumpAddressType\": 0,\n" +
                "    \"linkAddress\": null,\n" +
                "    \"markdownContent\": null\n" +
                "}";
        var createResponseBody = TestCaseHelpful.sendRequest(method, createUri, null, headers, createBody);
        TestCaseHelpful.assertThatJson(createResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(createResponseBody)
                .node("message").isEqualTo("成功");

        // 3) 等待1秒
        Thread.sleep(1000);

        // 4) 待审批列表-获取ID
        String queryUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/queryData";
        String queryBody = "{\"title\":null,\"cityNameList\":[],\"pushStartTime\":null,\"pushEndTime\":null,\"pushModeType\":[],\"centerShowType\":null,\"createName\":null,\"pageNo\":1,\"pageSize\":10,\"queryType\":null,\"msgType\":null}";
        var queryResponseBody = TestCaseHelpful.sendRequest(method, queryUri, null, headers, queryBody);
        TestCaseHelpful.assertThatJson(queryResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(queryResponseBody)
                .node("message").isEqualTo("成功");

        String touchId = TestCaseHelpful.extractValue(queryResponseBody, "$.data.list.[0].id").toString();

        // 5) 等待1秒
        Thread.sleep(1000);

        // 6) 触达审批通过
        String reviewUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/review";
        String reviewBody = "{\"id\":\"" + touchId + "\",\"driverIds\":[" + DRIVER_ID + "],\"popupUrl\":\"\",\"informationUrl\":\"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1752734448525c8758263d91042db81473799b96ce898blob\",\"msgType\":1,\"cityNameList\":[\"杭州市\"],\"pushModeType\":0,\"centerShowType\":1,\"pushTimeType\":0,\"pushTime\":null,\"title\":\"应用内推送\",\"summary\":\"摘要\",\"jumpAddressType\":1,\"linkAddress\":\"www.baidu.com\",\"markdownContent\":null,\"reviewedStatus\":1}";
        var reviewResponseBody = TestCaseHelpful.sendRequest(method, reviewUri, null, headers, reviewBody);
        TestCaseHelpful.assertThatJson(reviewResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(reviewResponseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("首页弹窗触达创建并审批")
    @Test
    void shouldCreateAndApproveHomePagePopupTouch() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 首页弹窗-触达创建
        String createUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/updateData";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String createBody = "{\n" +
                "    \"id\": null,\n" +
                "    \"driverIds\": [\n" +
                "        " + DRIVER_ID + "\n" +
                "    ],\n" +
                "    \"popupUrl\": \"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1752738237351ffb6bbc66fff4a67a3bc7d354ae18534blob\",\n" +
                "    \"informationUrl\": \"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1752738249186390bc5bebd8641b9ad717dffbbbfbe24blob\",\n" +
                "    \"msgType\": 1,\n" +
                "    \"cityNameList\": [\n" +
                "        \"杭州市\"\n" +
                "    ],\n" +
                "    \"pushModeType\": 2,\n" +
                "    \"centerShowType\": 1,\n" +
                "    \"pushTimeType\": 0,\n" +
                "    \"pushTime\": null,\n" +
                "    \"title\": \"首页弹窗\",\n" +
                "    \"summary\": \"首页弹窗摘要\",\n" +
                "    \"jumpAddressType\": 1,\n" +
                "    \"linkAddress\": \"www.baidu.com\",\n" +
                "    \"markdownContent\": null\n" +
                "}";
        var createResponseBody = TestCaseHelpful.sendRequest(method, createUri, null, headers, createBody);
        TestCaseHelpful.assertThatJson(createResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(createResponseBody)
                .node("message").isEqualTo("成功");

        // 3) 等待1秒
        Thread.sleep(1000);

        // 4) 待审批列表-获取ID
        String queryUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/queryData";
        String queryBody = "{\"title\":null,\"cityNameList\":[],\"pushStartTime\":null,\"pushEndTime\":null,\"pushModeType\":[],\"centerShowType\":null,\"createName\":null,\"pageNo\":1,\"pageSize\":10,\"queryType\":null,\"msgType\":null}";
        var queryResponseBody = TestCaseHelpful.sendRequest(method, queryUri, null, headers, queryBody);
        TestCaseHelpful.assertThatJson(queryResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(queryResponseBody)
                .node("message").isEqualTo("成功");

        String touchId = TestCaseHelpful.extractValue(queryResponseBody, "$.data.list.[0].id").toString();

        // 5) 等待1秒
        Thread.sleep(1000);

        // 6) 触达审批通过
        String reviewUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/review";
        String reviewBody = "{\"id\":\"" + touchId + "\",\"driverIds\":[" + DRIVER_ID + "],\"popupUrl\":\"\",\"informationUrl\":\"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1752734448525c8758263d91042db81473799b96ce898blob\",\"msgType\":1,\"cityNameList\":[\"杭州市\"],\"pushModeType\":0,\"centerShowType\":1,\"pushTimeType\":0,\"pushTime\":null,\"title\":\"应用内推送\",\"summary\":\"摘要\",\"jumpAddressType\":1,\"linkAddress\":\"www.baidu.com\",\"markdownContent\":null,\"reviewedStatus\":1}";
        var reviewResponseBody = TestCaseHelpful.sendRequest(method, reviewUri, null, headers, reviewBody);
        TestCaseHelpful.assertThatJson(reviewResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(reviewResponseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("短信触达创建并审批")
    @Test
    void shouldCreateAndApproveSmsTouch() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 短信-触达创建
        String createUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/updateData";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String createBody = "{\n" +
                "    \"id\": null,\n" +
                "    \"driverIds\": [\n" +
                "        " + DRIVER_ID + "\n" +
                "    ],\n" +
                "    \"popupUrl\": null,\n" +
                "    \"informationUrl\": null,\n" +
                "    \"msgType\": 1,\n" +
                "    \"cityNameList\": [\n" +
                "        \"杭州市\"\n" +
                "    ],\n" +
                "    \"pushModeType\": 3,\n" +
                "    \"centerShowType\": 1,\n" +
                "    \"pushTimeType\": 0,\n" +
                "    \"pushTime\": null,\n" +
                "    \"title\": \"短信发送标题\",\n" +
                "    \"summary\": \"短信发送标题内容\",\n" +
                "    \"jumpAddressType\": 1,\n" +
                "    \"linkAddress\": null,\n" +
                "    \"markdownContent\": null\n" +
                "}";
        var createResponseBody = TestCaseHelpful.sendRequest(method, createUri, null, headers, createBody);
        TestCaseHelpful.assertThatJson(createResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(createResponseBody)
                .node("message").isEqualTo("成功");

        // 3) 等待1秒
        Thread.sleep(1000);

        // 4) 待审批列表-获取ID
        String queryUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/queryData";
        String queryBody = "{\"title\":null,\"cityNameList\":[],\"pushStartTime\":null,\"pushEndTime\":null,\"pushModeType\":[],\"centerShowType\":null,\"createName\":null,\"pageNo\":1,\"pageSize\":10,\"queryType\":null,\"msgType\":null}";
        var queryResponseBody = TestCaseHelpful.sendRequest(method, queryUri, null, headers, queryBody);
        TestCaseHelpful.assertThatJson(queryResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(queryResponseBody)
                .node("message").isEqualTo("成功");

        String touchId = TestCaseHelpful.extractValue(queryResponseBody, "$.data.list.[0].id").toString();

        // 5) 等待1秒
        Thread.sleep(1000);

        // 6) 触达审批通过
        String reviewUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/review";
        String reviewBody = "{\"id\":\"" + touchId + "\",\"driverIds\":[" + DRIVER_ID + "],\"popupUrl\":\"\",\"informationUrl\":\"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1752734448525c8758263d91042db81473799b96ce898blob\",\"msgType\":1,\"cityNameList\":[\"杭州市\"],\"pushModeType\":0,\"centerShowType\":1,\"pushTimeType\":0,\"pushTime\":null,\"title\":\"应用内推送\",\"summary\":\"摘要\",\"jumpAddressType\":1,\"linkAddress\":\"www.baidu.com\",\"markdownContent\":null,\"reviewedStatus\":1}";
        var reviewResponseBody = TestCaseHelpful.sendRequest(method, reviewUri, null, headers, reviewBody);
        TestCaseHelpful.assertThatJson(reviewResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(reviewResponseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("邮箱触达创建并审批")
    @Test
    void shouldCreateAndApproveEmailTouch() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 邮箱-触达创建
        String createUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/updateData";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String createBody = "{\n" +
                "    \"id\": null,\n" +
                "    \"driverIds\": [\n" +
                "        " + DRIVER_ID + "\n" +
                "    ],\n" +
                "    \"popupUrl\": null,\n" +
                "    \"informationUrl\": null,\n" +
                "    \"msgType\": 1,\n" +
                "    \"cityNameList\": [\n" +
                "        \"杭州市\"\n" +
                "    ],\n" +
                "    \"pushModeType\": 4,\n" +
                "    \"centerShowType\": 1,\n" +
                "    \"pushTimeType\": 0,\n" +
                "    \"pushTime\": null,\n" +
                "    \"title\": \"邮箱发送\",\n" +
                "    \"summary\": \"邮箱发送内容\",\n" +
                "    \"jumpAddressType\": 1,\n" +
                "    \"linkAddress\": null,\n" +
                "    \"markdownContent\": null\n" +
                "}";
        var createResponseBody = TestCaseHelpful.sendRequest(method, createUri, null, headers, createBody);
        // 邮箱触达可能返回成功或超过限额的错误
        String message = TestCaseHelpful.extractValue(createResponseBody, "$.message").toString();
        assert message.equals("成功") || message.contains("当前触达计划发送的邮件已超过今日最大可发送限额") : "message应该是成功或超过限额";

        // 3) 如果创建成功，继续审批流程
        if (message.equals("成功")) {
            // 等待1秒
            Thread.sleep(1000);

            // 待审批列表-获取ID
            String queryUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/queryData";
            String queryBody = "{\"title\":null,\"cityNameList\":[],\"pushStartTime\":null,\"pushEndTime\":null,\"pushModeType\":[],\"centerShowType\":null,\"createName\":null,\"pageNo\":1,\"pageSize\":10,\"queryType\":null,\"msgType\":null}";
            var queryResponseBody = TestCaseHelpful.sendRequest(method, queryUri, null, headers, queryBody);
            TestCaseHelpful.assertThatJson(queryResponseBody)
                    .node("code").isEqualTo(1);
            TestCaseHelpful.assertThatJson(queryResponseBody)
                    .node("message").isEqualTo("成功");

            String touchId = TestCaseHelpful.extractValue(queryResponseBody, "$.data.list.[0].id").toString();

            // 等待1秒
            Thread.sleep(1000);

            // 触达审批通过
            String reviewUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/custom/touch/review";
            String reviewBody = "{\"id\":\"" + touchId + "\",\"driverIds\":[" + DRIVER_ID + "],\"popupUrl\":\"\",\"informationUrl\":\"https://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-new/1752734448525c8758263d91042db81473799b96ce898blob\",\"msgType\":1,\"cityNameList\":[\"杭州市\"],\"pushModeType\":0,\"centerShowType\":1,\"pushTimeType\":0,\"pushTime\":null,\"title\":\"应用内推送\",\"summary\":\"摘要\",\"jumpAddressType\":1,\"linkAddress\":\"www.baidu.com\",\"markdownContent\":null,\"reviewedStatus\":1}";
            var reviewResponseBody = TestCaseHelpful.sendRequest(method, reviewUri, null, headers, reviewBody);
            TestCaseHelpful.assertThatJson(reviewResponseBody)
                    .node("code").isEqualTo(1);
            TestCaseHelpful.assertThatJson(reviewResponseBody)
                    .node("message").isEqualTo("成功");
        }
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

