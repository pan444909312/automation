package com.miller.erp.login.flow;

import com.alibaba.fastjson2.JSON;
import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.login.request.LoginRequestDTO;
import com.miller.erp.login.response.LoginResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录流程
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 18:59:46
 */
public class LoginFlow {
    /**
     * 登录接口
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/auth/login/v2";

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
        return HttpUtils.sendPostRequest(uri, null, RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(loginRequestDTO), null);
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
     * ERP-登录，通过默认的 dongdong_test 账号
     * 登录的一种特化方式，登录之后将 token 设置到全局 headers 中，多用户登录时请勿使用。
     *
     * @return 响应体对象
     */
    public static LoginResponseDTO loginByDefaultUser() {
        LoginRequestDTO user = new LoginRequestDTO();
        user.setUserName(BusinessConstantOfERP.USERNAME);
        // ERP 个人账号，不使用明文
        user.setPassword(BusinessConstantOfERP.PASSWORD);
        LoginResponseDTO loginResponseDTO = loginReturnBodyObject(user);
        // 获取token
        var token = loginResponseDTO.getData().getToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("token", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return loginResponseDTO;
    }


}