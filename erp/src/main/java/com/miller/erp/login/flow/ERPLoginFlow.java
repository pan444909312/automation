package com.miller.erp.login.flow;

import com.alibaba.fastjson.JSON;
import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.login.request.ERPLoginRequestDTO;
import com.miller.erp.login.response.ERPLoginResponseDTO;
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
public class ERPLoginFlow {
    /**
     * 登录接口
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/auth/login/v2";

    /**
     * 是否手动登陆，有效期8小时
     */
    private static final boolean isManualLogin = false;
    // 获取手工登陆的token，有效期8小时
    private static final String token = "a0a3a0413b2c02858c4de3b5976adecc";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param ERPLoginRequestDTO {@link ERPLoginRequestDTO}
     * @return 响应结构
     */
    private static Map<String, Object> login(ERPLoginRequestDTO ERPLoginRequestDTO) {
        /* TODO
            线上环境无法通过账号密码登陆，解决方案：
            1. 手动扫码登陆；
            2. 扫码登陆后，获取到token，通过token访问接口；
            3. token 机制目前是默认8小时失效，使用这个token通过定时任务每4小时访问一次接口，会自动续签token；
            4. 将响应数据写死直接设置到 Map 中，避免请求失败，测试用的token写到配置文件中。
         */
        var mockLoginResponse = new HashMap<String, Object>();

        var responseBody = "{\n" +
                "    \"code\": 1,\n" +
                "    \"message\": \"成功\",\n" +
                "    \"data\": {\n" +
                "        \"token\": \"" + token + "\",\n" +
                "        \"manager\": {\n" +
                "            \"userId\": 1748,\n" +
                "            \"userName\": \"dongdong_test\",\n" +
                "            \"userNick\": \"单东东\",\n" +
                "            \"userPic\": \"\",\n" +
                "            \"userTelphone\": \"15606690056\",\n" +
                "            \"passwordUpdateTime\": null\n" +
                "        },\n" +
                "        \"showManagerBinding\": false\n" +
                "    },\n" +
                "    \"currencySymbol\": null,\n" +
                "    \"sql\": null,\n" +
                "    \"queryList\": null\n" +
                "}";
        mockLoginResponse.put("body", responseBody);

        var mockResponseBody = new HashMap<String, Object>();
        mockResponseBody.put("body", mockLoginResponse);

        if (isManualLogin) return mockResponseBody;

        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequest(uri, null, RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(ERPLoginRequestDTO), null);
    }

    /**
     * 登录返回响应体字符串
     *
     * @param ERPLoginRequestDTO {@link ERPLoginRequestDTO}
     * @return 响应体字符串
     */
    @SuppressWarnings("unchecked")
    public static String loginReturnBodyString(ERPLoginRequestDTO ERPLoginRequestDTO) {
        Map<String, Object> responseBodyMap = (Map<String, Object>) login(ERPLoginRequestDTO).get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }

    /**
     * 登录返回响应体对象
     *
     * @param ERPLoginRequestDTO {@link ERPLoginRequestDTO}
     * @return 响应体对象
     */
    public static ERPLoginResponseDTO loginReturnBodyObject(ERPLoginRequestDTO ERPLoginRequestDTO) {
        return JSON.parseObject(loginReturnBodyString(ERPLoginRequestDTO), ERPLoginResponseDTO.class);
    }

    /**
     * 登录返回响应头
     *
     * @param ERPLoginRequestDTO {@link ERPLoginRequestDTO}
     * @return 响应头
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> loginReturnHeaders(ERPLoginRequestDTO ERPLoginRequestDTO) {
        return (Map<String, Object>) login(ERPLoginRequestDTO).get("headers");
    }

    /**
     * ERP-登录，通过默认的 dongdong_test 账号
     * 登录的一种特化方式，登录之后将 token 设置到全局 headers 中，多用户登录时请勿使用。
     *
     * @return 响应体对象
     */
    public static ERPLoginResponseDTO loginByDefaultUser() {
        ERPLoginRequestDTO user = new ERPLoginRequestDTO();
        user.setUserName(BusinessConstantOfERP.USERNAME);
        // ERP 个人账号，不使用明文
        user.setPassword(BusinessConstantOfERP.PASSWORD);
        ERPLoginResponseDTO ERPLoginResponseDTO = loginReturnBodyObject(user);
        // 获取token
        var token = ERPLoginResponseDTO.getData().getToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("token", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return ERPLoginResponseDTO;
    }

    /**
     * ERP 后台老的项目接口请求需要在请求头中放置token，所以需要手动登录获取token，然后设置到全局 headers 中。
     */
    public static void erpLoginByCookie() {
        var headers = new HashMap<String, Object>();
        // 先从请求头中把token取出来，放到新的 headers 中，否则每次 setHeaders 方法调用token都会初始化为空
        headers.put(BusinessConstantOfERP.TOKEN, RequestUtils.getHeaders().get(BusinessConstantOfERP.TOKEN));
        headers.put("Content-Type", RequestUtils.getHeaders().get("Content-Type"));
        // 必传字段，使用erp的登录token进行校验
        String cookie = "CN_isNewFramework=1;CN_token=" + RequestUtils.getHeaders().get(BusinessConstantOfERP.TOKEN);
        headers.put("Cookie", cookie);
        RequestUtils.setHeaders(headers);
    }
}