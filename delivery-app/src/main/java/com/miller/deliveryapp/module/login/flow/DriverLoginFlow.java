package com.miller.deliveryapp.module.login.flow;

import com.alibaba.fastjson.JSON;
import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.login.request.DeliveryLoginRequestDTO;
import com.miller.deliveryapp.login.response.DeliveryLoginResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录流程
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 20:59:46
 */
public class DriverLoginFlow {
    /**
     * 登录接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/auth/login";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param deliveryLoginRequestDTO {@link DeliveryLoginRequestDTO}
     * @return 响应结构
     */
    private static Map<String, Object> login(DeliveryLoginRequestDTO deliveryLoginRequestDTO) {
        var header = new HashMap<String, Object>();
        header.put("Content-Type", "application/json");
        RequestUtils.setHeaders(header);
        return HttpUtils.sendPostRequest(uri, null,
                RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(deliveryLoginRequestDTO),
                null);
    }

    /**
     * 登录返回响应体字符串
     *
     * @param deliveryLoginRequestDTO {@link DeliveryLoginRequestDTO}
     * @return 响应体字符串
     */
    @SuppressWarnings("unchecked")
    public static String loginReturnBodyString(DeliveryLoginRequestDTO deliveryLoginRequestDTO) {
        Map<String, Object> responseBodyMap = (Map<String, Object>) login(deliveryLoginRequestDTO).get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }

    /**
     * 登录返回响应体对象
     *
     * @param deliveryLoginRequestDTO {@link DeliveryLoginRequestDTO}
     * @return 响应体对象
     */
    public static DeliveryLoginResponseDTO loginReturnBodyObject(DeliveryLoginRequestDTO deliveryLoginRequestDTO) {
        return JSON.parseObject(loginReturnBodyString(deliveryLoginRequestDTO), DeliveryLoginResponseDTO.class);
    }

    /**
     * 登录返回响应头
     *
     * @param deliveryLoginRequestDTO {@link DeliveryLoginRequestDTO}
     * @return 响应头
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static Map<String, Object> loginReturnHeaders(DeliveryLoginRequestDTO deliveryLoginRequestDTO) {
        return (Map<String, Object>) login(deliveryLoginRequestDTO).get("headers");
    }

    /**
     * 登录的一种特化方式，登录之后将 token 设置到全局 headers 中，多用户登录时请勿使用。
     *
     * @param deliveryLoginRequestDTO {@link DeliveryLoginRequestDTO}
     * @return 响应体对象
     */
    @SuppressWarnings("unused")
    public static DeliveryLoginResponseDTO loginAndPutToken(DeliveryLoginRequestDTO deliveryLoginRequestDTO) {
        DeliveryLoginResponseDTO deliveryLoginResponseDTO = loginReturnBodyObject(deliveryLoginRequestDTO);

        // 获取token
        var token = deliveryLoginResponseDTO.getResult().getAccessToken();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        return deliveryLoginResponseDTO;
    }

}