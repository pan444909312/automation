package com.miller.userapp.login.flow;

import com.alibaba.fastjson2.JSON;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.login.request.LoginRequestDTO;
import com.miller.userapp.login.response.LoginResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程_用户登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/7 15:59:46
 */
public class LoginFlow {
    /**
     * 登录接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/combine/login";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param loginRequestDTO {@link LoginRequestDTO}
     * @return 响应结构
     */
    private static Map<String, Object> login(LoginRequestDTO loginRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequest(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(loginRequestDTO), null);
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
     * 登录返回响应体对象
     *
     * @param loginRequestDTO {@link LoginRequestDTO}
     * @return 响应体对象
     */
    public static LoginResponseDTO loginReturnBodyObject(LoginRequestDTO loginRequestDTO) {
        return JSON.parseObject(loginReturnBodyString(loginRequestDTO), LoginResponseDTO.class);
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
        headers.put("Content-Type", "application/json");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return loginResponseDTO;
    }

}