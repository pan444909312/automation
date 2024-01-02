package com.miller.demo.issues;

import com.miller.common.util.ULIDUtils;
import com.miller.demo.constants.BusinessConstant;
import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.dto.BasicResponseDTO;
import com.miller.demo.dto.external.Issues;
import com.miller.demo.issues.request.IssueRequestDTO;
import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.login.response.LoginResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.depend.DependsOnClass;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

/**
 * 测试用例_缺陷更新
 * <p>
 * 缺陷管理平台，可用于测试基本的增、删、改、查*
 *  <ul>
 *      <li>
 *        地址：<a href="http://127.0.0.1">Click Me</a>
 *      </li>
 *      <li>
 *          账号密码: admin:123456
 *      </li>
 *  </ul>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 17:57:00
 */
@DependsOnClass(IssueCreateTests.class)
@EnvTag.Test
@TestFramework
@DisplayName("缺陷更新")
public class IssueUpdateTests {
    /**
     * 更新缺陷URL
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/issues/updateIssue/";
    private static final HashMap<String, Object> headers = new HashMap<>();

    @BeforeAll
    public static void beforeAll() {
        LoginResponseDTO loginResponseDTO = LoginFlow.
                loginReturnJavaObject(BusinessConstant.USERNAME_MILLER, BusinessConstant.PASSWORD_MILLER);
        // 从请求体获取 token 字段
        String token = loginResponseDTO.getData().getToken();
        headers.put("Authorization", token);
    }

    @DisplayName("It should be update issue successfully by id")
    @Test
    void shouldUpdateSuccessById() {
        // Given
        Issues issues = new Issues();
        issues.setIssueId(String.valueOf(IssueCreateTests.values.get("issueResponseDTO").getData()));
        issues.setTitle("测试更新缺陷" + IssueCreateTests.values.get("issueResponseDTO").getData());
        issues.setDescription("这是缺陷描述");
        // 这里直接写死了后端有个默认项目ID
        issues.setProjectId("c0c42460-5945-464b-b8da-f869e979ca28");
        issues.setHandler("miller.shan@aliyun.com");
        String body = JSONUtils.toJSONString(issues);

        // When
        BasicResponseDTO responseBody = HttpUtils.sendPutRequestReturnJavaObject(
                uri, null, headers, body, null, BasicResponseDTO.class);

        // Then
        Integer code = responseBody.getCode();
        assertThat(code, is(0));
    }

    @DisplayName("It should be update issue failure because ID is not exist")
    @Test
    void shouldUpdateFailedBecauseIDIsNotExist() {
        // Given
        IssueRequestDTO issueDTO = new IssueRequestDTO();
        issueDTO.setIssueId(ULIDUtils.generateULID());
        issueDTO.setTitle("测试更新缺陷" + ULIDUtils.generateULID());
        issueDTO.setDescription("这是缺陷描述");
        issueDTO.setHandler("miller.shan@aliyun.com");
        String body = JSONUtils.toJSONString(issueDTO);

        // When
        BasicResponseDTO responseBody = HttpUtils.sendPutRequestReturnJavaObject(
                uri, null, headers, body, null, BasicResponseDTO.class);

        // Then
        Integer code = responseBody.getCode();
        String message = responseBody.getMessage();

        assertThat(code, is(301));
        assertThat(message, containsStringIgnoringCase("影响的记录数小于1"));
    }

    @DisplayName("It should be update issue failure because ID is empty")
    @Test
    void shouldUpdateFailedBecauseIDIsEmpty() {
        // Given
        Issues issues = new Issues();

        String body = JSONUtils.toJSONString(issues);

        // When
        BasicResponseDTO responseBody = HttpUtils.sendPutRequestReturnJavaObject(
                uri, null, headers, body, null, BasicResponseDTO.class);

        // Then
        Integer code = responseBody.getCode();
        String message = responseBody.getMessage();
        assertThat(code, is(300));
        assertThat(message, containsStringIgnoringCase("ID不能为空"));
    }
}
