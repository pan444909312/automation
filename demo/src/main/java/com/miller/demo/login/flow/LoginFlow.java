package com.miller.demo.login.flow;

import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.login.response.LoginResponseDTO;
import com.miller.demo.user.request.UserRequestDTO;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程_登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 11:10:12
 */
public class LoginFlow {
    /**
     * 登录接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/user/login";

    /**
     * 登录_将响应体转换为Java对象返回
     *
     * @param username 邮箱
     * @param password 密码
     * @return {@link LoginResponseDTO}
     */
    public static LoginResponseDTO loginReturnJavaObject(String username, String password) {
        // Given. 请求参数
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setEmail(username);
        userDTO.setPassword(password);
        String body = JSONUtils.toJSONString(userDTO);

        // When. 发送请求
        LoginResponseDTO responseUser = HttpUtils.
                sendPostRequestReturnJavaObject(uri, null, null, body, null, LoginResponseDTO.class);
        // Then
        return responseUser;
    }

    /**
     * 登录_返回响应体。
     *
     * @param username 邮箱
     * @param password 密码
     * @return 响应体
     */
    public static String loginReturnBody(String username, String password) {
        Map<String, Object> loginMap = login(username, password);
        HashMap<String, Object> responseBodyMap = (HashMap<String, Object>) loginMap.get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }

    /**
     * 登录_返回 Map 结构，包含了响应的所有信息。比如: Headers, Cookie, Body 等.
     *
     * @param username 邮箱
     * @param password 密码
     * @return HashMap
     */
    public static Map<String, Object> login(String username, String password) {
        // Given. 请求参数
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setEmail(username);
        userDTO.setPassword(password);

        String body = JSONUtils.toJSONString(userDTO);
        // When. 发送请求
        return HttpUtils.sendPostRequest(
                uri, null, null, body, null);
    }


    public static Map<String, Object> loginReturnCookies(String username, String password) {
        Map<String, Object> loginMap = login(username, password);
        Map<String, Object> cookies = (HashMap<String, Object>) loginMap.get("cookies");
        return cookies;
    }

    public static Map<String, Object> loginReturnHeaders(String username, String password) {
        Map<String, Object> loginMap = login(username, password);
        Map<String, Object> headers = (HashMap<String, Object>) loginMap.get("headers");
        return headers;
    }

    /**
     * 这个方法是登陆的一种特化，意在简化登陆并且将token放置请求的过程
     *
     * @param username 账号
     * @param password 密码
     * @return Map中的key包含Authorization
     */
    public static Map<String, Object> loginAndPutToken(String username, String password) {
        LoginResponseDTO loginResponseDTO = loginReturnJavaObject(username, password);
        // 从请求体获取 token 字段
        String token = loginResponseDTO.getData().getToken();
        var headers = new HashMap<String, Object>();
        headers.put("Authorization", token);
        return headers;
    }
}
