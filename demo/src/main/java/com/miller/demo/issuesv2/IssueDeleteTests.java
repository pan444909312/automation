package com.miller.demo.issuesv2;

import com.miller.demo.constants.BusinessConstant;
import com.miller.demo.dto.BasicResponseDTO;
import com.miller.demo.issues.request.IssueRequestDTO;
import com.miller.demo.issuesv2.flow.IssueCreateFlow;
import com.miller.demo.issuesv2.flow.IssueDeleteFlow;
import com.miller.demo.login.flow.LoginFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

/**
 * 缺陷删除测试用例
 *
 * <p>
 * 由于这里我使用的是调用 Flow 层，所以这个用例可以不用依赖 {@link  IssueCreateTests}, 自己独立就能完成删除缺陷的流程。
 * 这并不是一个好的例子，只是为例演示存在这种场景的解决方案。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 20:24:15
 */
@EnvTag.Test
@TestFramework
@DisplayName("缺陷删除")
public class IssueDeleteTests {
    private static Map<String, Object> headers;

    @BeforeAll
    public static void beforeAll() {
        headers = LoginFlow.loginAndPutToken
                (BusinessConstant.USERNAME_MILLER, BusinessConstant.PASSWORD_MILLER);
    }

    @MethodSource("com.miller.demo.issuesv2.provider.IssueDataProvider#creatIssueDataProvider")
    @ParameterizedTest
    @DisplayName("It should be deleted issue successfully by issue id")
    void shouldDeleteSuccessByIssueId(IssueRequestDTO issueRequestDTO) {
        // Given.
        // 构造删除缺陷的前置条件.需要注意的是如果前置条件会产生"副作用"那么最好提供数据回收的方法，否则可能会产生脏数据。
        // 当然也可以通过 @DependClass 来指定需要依赖的测试用例类来完成前置执行。
        BasicResponseDTO responseDTO = IssueCreateFlow.createIssue(headers, issueRequestDTO);
        String issueId = responseDTO.getData().toString();

        // When
        BasicResponseDTO responseBody = IssueDeleteFlow.
                deleteIssue(headers, issueId);

        // Then
        Integer data = Integer.valueOf(String.valueOf(responseBody.getData()));
        assertThat(data, greaterThan(0));
    }
}
