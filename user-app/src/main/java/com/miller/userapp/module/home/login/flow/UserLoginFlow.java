package com.miller.userapp.module.home.login.flow;

import com.alibaba.fastjson.JSON;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.home.login.response.UserLoginResponseDTO;
import com.miller.service.util.AutoSignUtils;
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
public class UserLoginFlow {
    /**
     * 登录接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/combine/login";

    private static UserLoginResponseDTO userLoginResponseDTO;

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param userLoginRequestDTO {@link UserLoginRequestDTO}
     * @return 响应结构
     */
    private static Map<String, Object> login(UserLoginRequestDTO userLoginRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json");
        RequestUtils.setHeaders(header);

        AutoSignUtils.signHandler(RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(userLoginRequestDTO));

        Map<String, Object> objectMap = HttpUtils.sendPostRequest(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(userLoginRequestDTO), null);
        // 当前登录的用用户信息
        userLoginResponseDTO = null;
        userLoginResponseDTO = JSON.parseObject(
                String.valueOf(
                        ((Map<String, Object>) objectMap.get("body"))
                                // 获取响应体的 body 字符串
                                .get("body")
                ), UserLoginResponseDTO.class);
        return objectMap;
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return {@link UserLoginResponseDTO}
     */
    public static UserLoginResponseDTO getCurrentUserInfo() {
        return userLoginResponseDTO;
    }

    /**
     * 登录返回响应体字符串
     *
     * @param userLoginRequestDTO {@link UserLoginRequestDTO}
     * @return 响应体字符串
     */
    @SuppressWarnings("unchecked")
    public static String loginReturnBodyString(UserLoginRequestDTO userLoginRequestDTO) {
        Map<String, Object> responseBodyMap = (Map<String, Object>) login(userLoginRequestDTO).get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }

    /**
     * 登录返回响应体对象
     *
     * @param userLoginRequestDTO {@link UserLoginRequestDTO}
     * @return 响应体对象
     */
    public static UserLoginResponseDTO loginReturnBodyObject(UserLoginRequestDTO userLoginRequestDTO) {
        return JSON.parseObject(loginReturnBodyString(userLoginRequestDTO), UserLoginResponseDTO.class);

    }

    /**
     * 登录返回响应头
     *
     * @param userLoginRequestDTO {@link UserLoginRequestDTO}
     * @return 响应头
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static Map<String, Object> loginReturnHeaders(UserLoginRequestDTO userLoginRequestDTO) {
        return (Map<String, Object>) login(userLoginRequestDTO).get("headers");
    }

    /**
     * 登录的一种特化方式，登录之后将 token 设置到全局 headers 中，多用户登录时请勿使用。
     *
     * @param userLoginRequestDTO {@link UserLoginRequestDTO}
     * @return 响应体对象
     */
    @SuppressWarnings("unused")
    public static UserLoginResponseDTO loginAndPutToken(UserLoginRequestDTO userLoginRequestDTO) {
        UserLoginResponseDTO userLoginResponseDTO = loginReturnBodyObject(userLoginRequestDTO);

        // 获取token
        var token = userLoginResponseDTO.getResult().getAccessToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return userLoginResponseDTO;
    }

    /**
     * 用户端-登录，通过默认的账号
     * 登录的一种特化方式，登录之后将 token 设置到全局 headers 中，多用户登录时请勿使用。
     *
     * @return 响应体对象
     */
    public static UserLoginResponseDTO loginByDefaultUser() {
        String passWord = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.password");
        String userName = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account");
        String loginType = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type");
        String callingCode = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode");
        String distinctId = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId");

        // 构造请求数据，从数据库查询结果作为请求数据
        UserLoginRequestDTO user = new UserLoginRequestDTO();
        user.setAreaCode(callingCode);
        user.setAccount(userName);
        user.setPassword(MD5Util.string2MD5(passWord));
        user.setType(Integer.valueOf(loginType));
        user.setDistinctId(distinctId);

        UserLoginResponseDTO userLoginResponseDTO = UserLoginFlow.loginReturnBodyObject(user);

        // 获取token
        var token = userLoginResponseDTO.getResult().getAccessToken();
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return userLoginResponseDTO;
    }

}