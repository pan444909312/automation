package com.miller.demo.issues;

import com.miller.demo.constants.BusinessConstant;
import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.dto.BasicResponseDTO;
import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.login.response.LoginResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.depend.DependsOnClass;
import com.miller.service.framework.http.HttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.miller.demo.constants.BusinessConstant.PASSWORD_MILLER;
import static com.miller.demo.constants.BusinessConstant.USERNAME_MILLER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * 缺陷删除接口对应的测试用例
 * <p>
 * 缺陷管理平台，可用于测试基本的增、删、改、查*
 *  <ul>
 *      <li>
 *        地址：<a href="http://127.0.0.1">Click Me</a>
 *      </li>
 *      <li>
 *          账号密码: admin@aliyun.com:123456
 *      </li>
 *  </ul>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 17:57:00
 */
@DependsOnClass(IssueUpdateTests.class)
@EnvTag.Test
@TestFramework
@DisplayName("缺陷删除")
public class IssueDeleteTests {
    /**
     * 删除缺陷接口
     */
    private static final String deleteIssueURI = SystemConfigConstant.DOMAIN + "/issues/deleteIssue/";
    private static final HashMap<String, Object> headers = new HashMap<>();

    @BeforeAll
    public static void beforeAll() {
        LoginResponseDTO loginResponseDTO = LoginFlow.
                loginReturnJavaObject(USERNAME_MILLER, PASSWORD_MILLER);
        // 从请求体获取 token 字段
        String token = loginResponseDTO.getData().getToken();
        headers.put("Authorization", token);
    }

    @DisplayName("It should be deleted issue successfully by issue id")
    @Test
    void shouldDeleteSuccessByIssueId() {
        // Given
        String uri = deleteIssueURI + IssueCreateTests.values.get("issueResponseDTO").getData();

        // When
        BasicResponseDTO responseBody = HttpUtils.sendDeleteRequestReturnJavaObject(
                uri, null, headers, null, null, BasicResponseDTO.class);

        // Then
        Integer data = Integer.valueOf(String.valueOf(responseBody.getData()));
        assertThat(data, greaterThan(0));
    }

    @DisplayName("It should be deleted issue failure because ID is not exist")
    @Test
    void shouldDeleteFailedBecauseIDIsNotExist() {
        // Given
        String uri = deleteIssueURI + RandomStringUtils.random(10, true, false);

        // When
        BasicResponseDTO responseBody = HttpUtils.sendDeleteRequestReturnJavaObject(
                uri, null, headers, null, null, BasicResponseDTO.class);

        // Then
        Integer code = responseBody.getCode();
        String message = responseBody.getMessage();

        assertThat(code, is(301));
        assertThat(message, containsStringIgnoringCase("影响的记录数小于1"));
    }

}
