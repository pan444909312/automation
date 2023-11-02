package com.miller.demo.issues;

import com.miller.common.util.ULIDUtils;
import com.miller.demo.constants.BusinessConstant;
import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.dto.BasicResponseDTO;
import com.miller.demo.issues.request.IssueRequestDTO;
import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.login.response.LoginResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONPathUtils;
import com.miller.service.framework.util.JSONUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * 缺陷新增接口对应的测试用例
 * <p>
 * 缺陷管理平台，可用于测试基本的增、删、改、查*
 *  <ul>
 *      <li>
 *        地址：<a href="http://127.0.0.1">http://127.0.0.1</a>
 *      </li>
 *      <li>
 *          账号密码: admin@aliyun.com:123456
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
@DisplayName("缺陷新增")
public class IssueCreateTests {
    /**
     * 新增缺陷接口
     */
    private static final String createIssueURI = SystemConfigConstant.DOMAIN + "/issues/createIssue/";
    /**
     * 用于接口之间的值传递，提供数据给下一个接口
     */
    static HashMap<String, BasicResponseDTO> values = new HashMap<>();
    private static final HashMap<String, Object> headers = new HashMap<>();

    @BeforeAll
    public static void beforeAll() {
        LoginResponseDTO loginResponseDTO = LoginFlow.
                loginReturnJavaObject(BusinessConstant.USERNAME_MILLER, BusinessConstant.PASSWORD_MILLER);
        // 从请求体获取 token 字段
        String token = loginResponseDTO.getData().getToken();
        headers.put("Authorization", token);
    }


    @DisplayName("It should be added issue successfully")
    @Test
    void shouldAddIssueIntoDB() {
        // Given
        String uri = this.createIssueURI;
        IssueRequestDTO issueDTO = new IssueRequestDTO();
        issueDTO.setTitle("测试创建缺陷" + ULIDUtils.generateULID());
        issueDTO.setDescription("这是缺陷描述");
        issueDTO.setHandler("miller.shan@aliyun.com");
        String body = JSONUtils.toJSONString(issueDTO);

        // When
        BasicResponseDTO responseBody = HttpUtils.
                sendPostRequestReturnJavaObject(uri, null, headers, body, null, BasicResponseDTO.class);

        // Then
        String issueID = String.valueOf(responseBody.getData());
        assertThat(issueID, notNullValue());
        // 将响应对象数据放到Map中，供后续的接口使用
        values.put("issueResponseDTO", responseBody);
    }
}
