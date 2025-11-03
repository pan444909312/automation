package com.miller.demo.loginv2.flow;

import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.loginv2.response.LoginV2ResponseDTO;
import com.miller.demo.user.request.UserRequestDTO;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程_登录
 *
 * @author Miller Shan
 * @version 2.0
 * @since 2024/21/01 11:10:12
 */
public class LoginV2Flow {
    /**
     * 登录接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/user/login";

    /**
     * 登录_将响应体转换为Java对象返回
     *
     * @param username 邮箱
     * @param password 密码
     * @return {@link LoginV2ResponseDTO}
     */
    public static LoginV2ResponseDTO loginReturnJavaObject(String username, String password) {
        // Given. 请求参数
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setEmail(username);
        userDTO.setPassword(password);
        String body = JSONUtils.toJSONString(userDTO);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // When. 发送请求
        LoginV2ResponseDTO responseUser = HttpUtils.
                sendPostRequestReturnJavaObject(uri, null, headers, body, null, LoginV2ResponseDTO.class);
        // Then
        return responseUser;
    }
}
