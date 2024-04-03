package com.miller.pos.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.miller.pos.constants.ResponseConstant;
import com.miller.pos.login.flow.PosLoginFlow;
import com.miller.pos.login.request.PosLoginRequestDTO;
import com.miller.pos.login.response.PosLoginResponseDTO;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.annotation.ApiDoc;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Type;
import java.util.HashMap;

import static com.alibaba.fastjson.JSONValidator.Type.Array;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/7 20:31:39
 */
@ApiDoc(value = "http://10.1.6.46:3000/project/60/interface/api/3288")
@EnvTag.Test
@TestFramework
@DisplayName("pos-登录获取token")
public class LoginTests {
    private static String token;

    @AfterAll
    static void afterAll() {
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        assertThat(RequestUtils.getHeaders().get("Authorization")).isNotNull();
    }

    @MethodSource("com.miller.pos.login.provider.PosLoginDataProvider#loginDataProviderFromDB")
    @ParameterizedTest
    @DisplayName("正常流程_POS商家登录")
    void shouldLoginSuccessfully(PosLoginRequestDTO posLoginRequestDTO) {
        JSONObject JSONObject = PosLoginFlow.loginReturnBodyObject(posLoginRequestDTO);
        assertThat(JSONObject.getInteger("code")).isEqualTo(0);
//        assertThat(posLoginResponseDTO.getData().getAccessToken()).isNotNull();
//        System.out.println(posLoginResponseDTO.getData().getAccessToken());
        JSONObject JSONOtoken  =JSON.parseObject(String.valueOf( JSONObject.getString("data")));
        // 获取token

        token=JSONOtoken.getString("access_token") ;

    }

}
