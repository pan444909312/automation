package com.miller.demo.issues;

import com.miller.demo.constants.BusinessConstant;
import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.dto.external.Issues;
import com.miller.demo.issues.response.IssuesListResponseDTO;
import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.login.response.LoginResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.http.HttpUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.demo.constants.BusinessConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;

/**
 * 测试用例_缺陷查询
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
 * @since 2023/11/2 16:57:00
 */
@EnvTag.Test
@TestFramework
@DisplayName("缺陷查询")
public class IssueQueryTests {
    /**
     * 查询缺陷接口
     */
    private static final String queryIssueURI = SystemConfigConstant.DOMAIN + "/issues/list/Miller/1/5";
    private static final HashMap<String, Object> headers = new HashMap<>();

    @BeforeAll
    public static void beforeAll() {
        LoginResponseDTO loginResponseDTO = LoginFlow.
                loginReturnJavaObject(USERNAME_MILLER, BusinessConstant.PASSWORD_MILLER);
        // 从请求体获取 token 字段
        String token = loginResponseDTO.getData().getToken();
        headers.put("Authorization", token);
    }

    @DisplayName("Query issues by token")
    @Test
    void queryIssuesByToken() {
        // Given
        String uri = this.queryIssueURI;

        // When
        IssuesListResponseDTO responseBody = HttpUtils.sendGetRequestReturnJavaObject(
                uri, null, headers, null, IssuesListResponseDTO.class);

        // Then
        Issues issues = responseBody.getData().getRecords().get(0);
        assertThat(issues, hasProperty("issueId"));
    }

    @DependsOnMethod("queryIssuesByToken")
    @DisplayName("Query issues by Cookie")
    @Test
    void queryIssuesByCookies() {
        // Given
        String uri = this.queryIssueURI;

        // When
        IssuesListResponseDTO responseBody = HttpUtils.sendGetRequestReturnJavaObject(
                uri, null, headers, null, IssuesListResponseDTO.class);

        // Then
        Issues issues = responseBody.getData().getRecords().get(0);
        assertThat(issues, hasProperty("issueId"));
    }

    /**
     * 测试测试用例中的多用户切换。根据不同登录的用户查询出不同的数据。
     */
    @Test
    @DisplayName("It should query different data if login user account different ")
    void queryIssuesByDifferentUser() {
        // Given
        String uri = this.queryIssueURI;
        // Admin 用户登录
        Map<String, Object> cookiesByAdmin = LoginFlow.
                loginReturnCookies(USERNAME_ADMIN, PASSWORD_ADMIN);
        // Miller 用户登录
        Map<String, Object> cookiesByMiller = LoginFlow.
                loginReturnCookies(USERNAME_MILLER, PASSWORD_MILLER);

        // When
        // 将请求工具的 Cookie 设置为 admin 用户
        HttpUtils.sendGetRequest(uri, null, null, cookiesByAdmin);
        // 将请求工具的 Cookie 设置为 Miller 用户
        HttpUtils.sendGetRequest(uri, null, null, cookiesByMiller);

        //Then
        String adminJsessionid = String.valueOf(cookiesByAdmin.get("JSESSIONID"));
        String millerJsessionid = String.valueOf(cookiesByMiller.get("JSESSIONID"));
        // 断言两个用户登录之后返回的 Cookie 值不同
        assertThat(adminJsessionid, not(millerJsessionid));
    }

}
