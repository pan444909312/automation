package com.miller.testcase.module.account.login.phf_register_combine_login;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.miller.testcase.utils.TestCaseHelpful.getPhoneNumber;

/**
 * phf register combine login
 *
 * @author yancancan
 * @version 2.0
 * @since 2025/09/02 18:12:12
 */
@Scenario(
        scenarioID = "01K44YPX07FVXP8YX3H9ED8HS1", // 自动生成，不要修改
        scenarioName = "小程序-拼好饭/奶茶渠道：新账户注册成功",
        author = "yancancan@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("小程序-拼好饭/奶茶渠道：新账户注册成功")
public class PHFRegisterCombineLoginTests {

    @BeforeAll
    static void beforeAll(){
        // 所有 @Test 方法执行之前会执行  @BeforeAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行前置的操作，比如: SQL 初始化用例的前置条件
    }
    @AfterAll
    static void afterAll(){
        // 所有 @Test 方法执行之后会执行  @@AfterAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行后置的操作，比如: 销毁测试数据、还原数据库、清理环境等
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        String tel="19530624224";
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HOST_APP + "/api/user/combine/login";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/account/login/phf_register_combine_login/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/account/login/phf_register_combine_login/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/account/login/phf_register_combine_login/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        var verification=TestCaseHelpful.getVerificationCode(tel);
        System.out.println("获取到的验证码"+verification);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.pd.verification",verification);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
//        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).inPath("$.result.accessToken").isNotNull();
        String telephone = getPhoneNumber(tel);
        System.out.println("获取到的手机号: " + telephone);
        String user_id =PandaTestDBHelpful.executeSelectOneSql("select user_id from user where user_name = ?",telephone).get("user_id").toString();
        System.out.println("获取到的用户id: " + user_id);
        //校验表数据
        TestCaseHelpful.assertThat(PandaTestDBHelpful.executeSelectListSql("select * from user where user_id=?", user_id).size()).isEqualTo(1);
        TestCaseHelpful.assertThat(PandaTestDBHelpful.executeSelectListSql("select * from user where user_id=?", user_id).get(0).get("register_source")).isEqualTo(25);
//        // 清除已注册用户数据
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("delete from account where user_id=" + user_id);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("delete from device_login_info where user_id=" + user_id);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("delete from integral where user_id=" + user_id);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("delete from user_log where user_id=" + user_id);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("delete from user_account where user_id=" + user_id);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("delete from user where user_id=" + user_id);

    }
} 