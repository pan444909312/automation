package com.miller.pos.login.flow;

import com.alibaba.fastjson.JSON;
import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.login.request.PosLoginRequestDTO;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.pos.login.response.PosLoginResponseDTO;
import java.util.HashMap;
import java.util.Map;

import static com.miller.pos.constants.BusinessConstant.app_key;


/**
 * 用户登录流程
 *
 * @author zhangli
 * @version 1.0
 * @since 2024/04/01 20:59:46
 */
public class PosLoginFlow {
    /**
     * 登录接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/token/access_token";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param posLoginRequestDTO {@link PosLoginRequestDTO}
     * @return 响应结构
     */
    private static Map<String, Object> login(PosLoginRequestDTO posLoginRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json");
        header.put("Connection", "keep-alive");
        RequestUtils.setHeaders(header);
//        Map<String, String> body = new HashMap<>();
//        body.put("app_key",app_key);
//        body.put("app_secret","123");
        return HttpUtils.sendPostRequest(uri, null, RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(posLoginRequestDTO), null);
    }

    /**
     * 登录返回响应体对象
     *
     * @param posLoginRequestDTO {@link PosLoginRequestDTO}
     * @return 响应体对象
     */
    public static PosLoginResponseDTO loginReturnBodyObject(PosLoginRequestDTO posLoginRequestDTO) {
        return JSON.parseObject(loginReturnBodyString(posLoginRequestDTO), PosLoginResponseDTO.class);
    }

    /**
     * 登录返回响应体字符串
     *
     * @param PosLoginRequestDTO {@link PosLoginRequestDTO}
     * @return 响应体字符串
     */
    @SuppressWarnings("unchecked")
    public static String loginReturnBodyString(PosLoginRequestDTO PosLoginRequestDTO) {
        Map<String, Object> responseBodyMap = (Map<String, Object>) login(PosLoginRequestDTO).get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }


    /**
     * 登录返回响应头
     *
     * @param PosLoginRequestDTO {@link PosLoginRequestDTO}
     * @return 响应头
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static Map<String, Object> loginReturnHeaders(PosLoginRequestDTO PosLoginRequestDTO) {
        return (Map<String, Object>) login(PosLoginRequestDTO).get("headers");
    }

    /**
     * 登录的一种特化方式，登录之后将 token 设置到全局 headers 中，多用户登录时请勿使用。
     *
     * @param posLoginRequestDTO {@link PosLoginRequestDTO}
     * @return 响应体对象
     */
    @SuppressWarnings({"unused"})
    public static PosLoginResponseDTO loginAndPutToken(PosLoginRequestDTO posLoginRequestDTO) {
        PosLoginResponseDTO posLoginResponseDTO = loginReturnBodyObject(posLoginRequestDTO);

        // 获取token
        var token = posLoginResponseDTO.getData().getAccessToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return posLoginResponseDTO;
    }

    /**
     * 切换当前用户
     *

     * @param app_key 用户名
     * @param app_secret 密码app_key
     */
    public static void switchUser(String app_key , String app_secret) {
        PosLoginRequestDTO posLoginRequestDTO = new PosLoginRequestDTO();
        posLoginRequestDTO.setAppKey(app_key);
        posLoginRequestDTO.setAppSecret(app_secret);
        PosLoginResponseDTO posLoginResponseDTO = loginReturnBodyObject(posLoginRequestDTO);
        var token = posLoginResponseDTO.getData().getAccessToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", RequestUtils.getHeaders().get("Content-Type"));
        headers.put(BusinessConstant.authorization, token);
        // 更新全局请求头参数。设置测试用例的用户。
        RequestUtils.setHeaders(headers);
    }

    /**
     * 切换当前用户，使用默认的区号86
     *
     * @see #switchUser(String, String)
     */
//    public static void switchUser(String app_key, String app_secret) {
//        switchUser(app_key, app_secret);
//    }
}