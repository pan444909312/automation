package com.miller.delivery.testcase.module.deliveryUtils.im;

import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * IM会话删除数据
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPF8ZAFXN5PC1SMYMTPTJBY",
        scenarioName = "IM会话删除数据",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("IM会话删除数据")
public class ImSessionDeleteTests {

    // 硬编码的环境变量值
    private static final String DIM_CURSOR = "ZGNiMjRmNGY1YjczYjlhYTNkYjk1MDY2YmEyNzFmODQ6aW06dXNlcjoxMTM5MjIxMDEyMTM4MjEzI2RlbW86MTQ0MDcwNjkxNDY4OTU4ODY1NQ";
    private static final String DIM_TOKEN = "Bearer YWMtJ-AMsmrYSiO1BPnveqHZUgAAAAAAAAAAAAAAAAAAAAHJ2Sl9kmJHKJgHhqBt6KRHAQMAAAGamtrd8gAABwjEVdv04vcgqRlI0md1B6LrZrvzt_4chvFd-rFwKfJT3Q";

    @DisplayName("IM会话删除数据")
    @Test
    void shouldDeleteImSession() throws InterruptedException {
        // 步骤1: 获取会话列表
        String groupId = getChatGroups();
        
        // 延迟500ms
        Thread.sleep(500);
        
        // 步骤2: OPTIONS预检请求 (CORS)
        optionsRequest(groupId);
        
        // 延迟500ms
        Thread.sleep(500);
        
        // 步骤3: 删除会话
        deleteChatGroup(groupId);
        
        // 延迟500ms
        Thread.sleep(500);
    }

    /**
     * 获取会话列表
     */
    private String getChatGroups() {
        String uri = "https://im-console.chat.agora.io/1139221012138213/demo/chatgroups";
        String method = "GET";
        Map<String, Object> headers = createImHeaders();
        Map<String, Object> params = new java.util.HashMap<>();
        params.put("limit", "1");
        params.put("cursor", DIM_CURSOR);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, params, headers, null);
        // 从响应中提取groupid
        return TestCaseHelpful.extractValue(responseBody, "$.data[0].groupid").toString();
    }

    /**
     * OPTIONS预检请求
     */
    private void optionsRequest(String groupId) {
        String uri = "https://im-console.chat.agora.io/1139221012138213/demo/chatgroups/" + groupId;
        String method = "OPTIONS";
        Map<String, Object> headers = createOptionsHeaders();
        
        TestCaseHelpful.sendRequest(method, uri, null, headers, null);
    }

    /**
     * 删除会话
     */
    private void deleteChatGroup(String groupId) {
        String uri = "https://im-console.chat.agora.io/1139221012138213/demo/chatgroups/" + groupId;
        String method = "DELETE";
        Map<String, Object> headers = createDeleteHeaders();
        
        TestCaseHelpful.sendRequest(method, uri, null, headers, null);
        // 可以添加断言验证删除成功
        // var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, null);
        // TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(200);
    }

    /**
     * 创建IM请求头 (GET请求)
     */
    private Map<String, Object> createImHeaders() {
        Map<String, Object> headers = new java.util.HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", DIM_TOKEN);
        headers.put("origin", "https://console.easemob.com");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://console.easemob.com/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"137\", \"Chromium\";v=\"137\", \"Not/A)Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "cross-site");
        headers.put("sec-fetch-storage-access", "active");
        headers.put("source", "NORMAL");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36");
         
        return headers;
    }

    /**
     * 创建OPTIONS请求头
     */
    private Map<String, Object> createOptionsHeaders() {
        Map<String, Object> headers = new java.util.HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("access-control-request-headers", "authorization,content-type,source");
        headers.put("access-control-request-method", "DELETE");
        headers.put("origin", "https://console.easemob.com");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://console.easemob.com/");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "cross-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36");
         
        return headers;
    }

    /**
     * 创建DELETE请求头
     */
    private Map<String, Object> createDeleteHeaders() {
        Map<String, Object> headers = new java.util.HashMap<>();
        headers.put("accept", "application/json");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", DIM_TOKEN);
        headers.put("origin", "https://console.easemob.com");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://console.easemob.com/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"137\", \"Chromium\";v=\"137\", \"Not/A)Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "cross-site");
        headers.put("sec-fetch-storage-access", "active");
        headers.put("source", "NORMAL");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36");
         
        headers.put("content-type", "application/json; charset=utf-8");
        return headers;
    }
}

