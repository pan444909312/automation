package com.miller.demo.issuesv2;

import com.miller.common.util.ULIDUtils;
import com.miller.demo.constants.BusinessConstant;
import com.miller.demo.dto.BasicResponseDTO;
import com.miller.demo.issues.request.IssueRequestDTO;
import com.miller.demo.issuesv2.flow.IssueCreateFlow;
import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.login.response.LoginResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 19:51:15
 */
@EnvTag.Test
@TestFramework
@DisplayName("缺陷新增")
public class IssueCreateTests {
    private static Map<String, Object> headers;

    @BeforeAll
    public static void beforeAll() {
        headers = LoginFlow.loginAndPutToken
                (BusinessConstant.USERNAME_MILLER, BusinessConstant.PASSWORD_MILLER);
    }

    @MethodSource("com.miller.demo.issuesv2.provider.IssueDataProvider#creatIssueDataProvider")
    @ParameterizedTest
    @DisplayName("It should be added issue successfully")
    void shouldAddIssueIntoDB(IssueRequestDTO issueRequestDTO) {
        // Given

        // When
        BasicResponseDTO responseBody = IssueCreateFlow.createIssue(headers, issueRequestDTO);
        // Then
        String issueID = String.valueOf(responseBody.getData());
        assertThat(issueID, notNullValue());
    }
}
