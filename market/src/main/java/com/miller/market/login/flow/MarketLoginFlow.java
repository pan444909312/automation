package com.miller.market.login.flow;

import com.alibaba.fastjson.JSON;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.login.request.MarketLoginRequestDTO;
import com.miller.market.login.response.MarketLoginResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录流程
 *
 */
public class MarketLoginFlow {
    /**
     * 登录接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/user/login";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param marketLoginRequestDTO {@link MarketLoginRequestDTO}
     * @return 响应结构
     */
    private static Map<String, Object> login(MarketLoginRequestDTO marketLoginRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequest(uri, null, RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(marketLoginRequestDTO), null);
    }

    /**
     * 登录返回响应体字符串
     *
     * @param marketLoginRequestDTO {@link MarketLoginRequestDTO}
     * @return 响应体字符串
     */
    @SuppressWarnings("unchecked")
    public static String loginReturnBodyString(MarketLoginRequestDTO marketLoginRequestDTO) {
        Map<String, Object> responseBodyMap = (Map<String, Object>) login(marketLoginRequestDTO).get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }

    /**
     * 登录返回响应体对象
     *
     * @param marketLoginRequestDTO {@link MarketLoginRequestDTO}
     * @return 响应体对象
     */
    public static MarketLoginResponseDTO loginReturnBodyObject(MarketLoginRequestDTO marketLoginRequestDTO) {
        return JSON.parseObject(loginReturnBodyString(marketLoginRequestDTO), MarketLoginResponseDTO.class);
    }




    /**
     * 登录返回响应头
     *
     * @param marketLoginRequestDTO {@link MarketLoginRequestDTO}
     * @return 响应头
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static Map<String, Object> loginReturnHeaders(MarketLoginRequestDTO marketLoginRequestDTO) {
        return (Map<String, Object>) login(marketLoginRequestDTO).get("headers");
    }

    /**
     * 登录的一种特化方式，登录之后将 token 设置到全局 headers 中，多用户登录时请勿使用。
     *
     * @param marketLoginRequestDTO {@link MarketLoginRequestDTO}
     * @return 响应体对象
     */
    @SuppressWarnings({"unused"})
    public static MarketLoginResponseDTO loginAndPutToken(MarketLoginRequestDTO marketLoginRequestDTO) {
        MarketLoginResponseDTO marketLoginResponseDTO = loginReturnBodyObject(marketLoginRequestDTO);

        // 获取token
        var token = marketLoginResponseDTO.getData().getToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return marketLoginResponseDTO;
    }

    /**
     * 切换当前用户
     *
     * @param areaCode 国家区号
     * @param username 用户名
     * @param password 密码
     */
//    public static void switchUser(String areaCode, String username, String password) {
//        MarketLoginRequestDTO marketLoginRequestDTO = new MarketLoginRequestDTO();
//        marketLoginRequestDTO.setAreaCode(areaCode);
//        marketLoginRequestDTO.setPhone(username);
//        marketLoginRequestDTO.setCode(MD5Util.string2MD5(password));
//        MarketLoginResponseDTO marketLoginResponseDTO = loginReturnBodyObject(marketLoginRequestDTO);
//        var token = marketLoginResponseDTO.getResult();
//        var headers = new HashMap<String, Object>();
//        headers.put("Content-Type", RequestUtils.getHeaders().get("Content-Type"));
//        headers.put(BusinessConstant.authorization, token);
//        // 更新全局请求头参数。设置测试用例的用户。
//        RequestUtils.setHeaders(headers);
//    }

//    /**
//     * 切换当前用户，使用默认的区号86
//     *
//     * @see #switchUser(String, String, String)
//     */
//    public static void switchUser(String username, String password) {
//        switchUser("86", username, password);
//    }
}