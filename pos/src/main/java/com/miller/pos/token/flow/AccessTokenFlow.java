package com.miller.pos.token.flow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.token.request.AccessTokenRequestDTO;
import com.miller.pos.token.response.AccessTokenResponseDTO;
import com.miller.pos.util.RequestUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 用户登录流程
 *
 * @author zhangli
 * @version 1.0
 * @since 2024/04/01 20:59:46
 */
public class AccessTokenFlow {
    /**
     * 登录接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/token/access_token";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param accessTokenRequestDTO {@link AccessTokenRequestDTO}
     * @return 响应结构
     */
    public static Map<String, Object> getToken(AccessTokenRequestDTO accessTokenRequestDTO) {
        return RequestUtils.sendPostRequest(uri, accessTokenRequestDTO);
    }

    public static String getToken() {
        AccessTokenRequestDTO accessTokenRequestDTO = new AccessTokenRequestDTO(BusinessConstant.app_key, BusinessConstant.app_secret);
        AccessTokenResponseDTO responseDTO = RequestUtils.sendPostRequest(
                uri,
                accessTokenRequestDTO,
                AccessTokenResponseDTO.class
        );
        return responseDTO.getData().getAccessToken();
    }

    /**
     * 登录返回响应体对象
     *
     * @param accessTokenRequestDTO {@link AccessTokenRequestDTO}
     * @return 响应体对象
     */
    public static AccessTokenResponseDTO getAccessToken(AccessTokenRequestDTO accessTokenRequestDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(loginReturnBodyString(accessTokenRequestDTO), AccessTokenResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        return JSON.parseObject(loginReturnBodyString(accessTokenRequestDTO), AccessTokenResponseDTO.class);
    }


    /**
     * 登录返回响应体字符串
     *
     * @param AccessTokenRequestDTO {@link AccessTokenRequestDTO}
     * @return 响应体字符串
     */
    @SuppressWarnings("unchecked")
    public static String loginReturnBodyString(AccessTokenRequestDTO AccessTokenRequestDTO) {
        Map<String, Object> responseBodyMap = (Map<String, Object>) getToken(AccessTokenRequestDTO).get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }


    /**
     * 登录返回响应头
     *
     * @param AccessTokenRequestDTO {@link AccessTokenRequestDTO}
     * @return 响应头
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static Map<String, Object> loginReturnHeaders(AccessTokenRequestDTO AccessTokenRequestDTO) {
        return (Map<String, Object>) getToken(AccessTokenRequestDTO).get("headers");
    }

    /**
     * 登录的一种特化方式，登录之后将 token 设置到全局 headers 中，多用户登录时请勿使用。
     *
     * @param accessTokenRequestDTO {@link AccessTokenRequestDTO}
     * @return 响应体对象
     */
    @SuppressWarnings({"unused"})
    public static AccessTokenResponseDTO loginAndPutToken(AccessTokenRequestDTO accessTokenRequestDTO) {
        AccessTokenResponseDTO accessTokenResponseDTO = getAccessToken(accessTokenRequestDTO);

        // 获取token
        var token = accessTokenResponseDTO.getData().getAccessToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return accessTokenResponseDTO;
    }
}