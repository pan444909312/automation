package com.miller.merchant.login.flow;

import com.alibaba.fastjson2.JSON;
import com.miller.common.util.MD5Util;
import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.login.request.LoginRequestDTO;
import com.miller.merchant.login.response.LoginResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录流程
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/14 20:59:46
 */
public class LoginFlow {
    /**
     * 登录接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/oauth2/merchant/login";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param loginRequestDTO {@link LoginRequestDTO}
     * @return 响应结构
     */
    private static Map<String, Object> login(LoginRequestDTO loginRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequest(uri, null, RequestUtils.getHeaders(), RequestUtils.putBodyOfForm(loginRequestDTO), null);
    }

    /**
     * 登录返回响应体对象
     *
     * @param loginRequestDTO {@link LoginRequestDTO}
     * @return 响应体对象
     */
    public static LoginResponseDTO loginReturnBodyObject(LoginRequestDTO loginRequestDTO) {
        return JSON.parseObject(loginReturnBodyString(loginRequestDTO), LoginResponseDTO.class);
    }

    /**
     * 登录返回响应体字符串
     *
     * @param loginRequestDTO {@link LoginRequestDTO}
     * @return 响应体字符串
     */
    @SuppressWarnings("unchecked")
    public static String loginReturnBodyString(LoginRequestDTO loginRequestDTO) {
        Map<String, Object> responseBodyMap = (Map<String, Object>) login(loginRequestDTO).get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }


    /**
     * 登录返回响应头
     *
     * @param loginRequestDTO {@link LoginRequestDTO}
     * @return 响应头
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> loginReturnHeaders(LoginRequestDTO loginRequestDTO) {
        return (Map<String, Object>) login(loginRequestDTO).get("headers");
    }

    /**
     * 登录的一种特化方式，登录之后将 token 设置到全局 headers 中，多用户登录时请勿使用。
     *
     * @param loginRequestDTO {@link LoginRequestDTO}
     * @return 响应体对象
     */
    public static LoginResponseDTO loginAndPutToken(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = loginReturnBodyObject(loginRequestDTO);

        // 获取token
        var token = loginResponseDTO.getResult().getAccessToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "x-www-form-urlencoded");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return loginResponseDTO;
    }

    /**
     * 切换当前用户
     *
     * @param areaCode 国家区号
     * @param username 用户名
     * @param password 密码
     */
    public static void switchUser(String areaCode, String username, String password) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setAreaCode(areaCode);
        loginRequestDTO.setAccount(username);
        loginRequestDTO.setPassword(MD5Util.string2MD5(password));
        LoginResponseDTO loginResponseDTO = loginReturnBodyObject(loginRequestDTO);
        var token = loginResponseDTO.getResult().getAccessToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", RequestUtils.getHeaders().get("Content-Type"));
        headers.put(BusinessConstant.authorization, token);
        // 更新全局请求头参数。设置测试用例的用户。
        RequestUtils.setHeaders(headers);
    }

    /**
     * 切换当前用户，使用默认的区号86
     *
     * @see #switchUser(String, String, String)
     */
    public static void switchUser(String username, String password) {
        switchUser("86", username, password);
    }
}