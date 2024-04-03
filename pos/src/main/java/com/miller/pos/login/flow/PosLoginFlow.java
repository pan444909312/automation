package com.miller.pos.login.flow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
    public static JSONObject loginReturnBodyObject(PosLoginRequestDTO posLoginRequestDTO) {
        System.out.println("登录返回响应体对象json");
        System.out.println(JSON.parseObject(loginReturnBodyString(posLoginRequestDTO)));
        return JSON.parseObject(loginReturnBodyString(posLoginRequestDTO));
    }

    /**
     * 登录返回响应体字符串
     *
     * @param PosLoginRequestDTO {@link PosLoginRequestDTO}
     * @return 响应体字符串
     */
    @SuppressWarnings("unchecked")
    public static String loginReturnBodyString(PosLoginRequestDTO PosLoginRequestDTO) {
        System.out.println("登录返回响应体对象string");
        Map<String, Object> responseBodyMap = (Map<String, Object>) login(PosLoginRequestDTO).get("body");
        System.out.println("string的日志"+String.valueOf(responseBodyMap.get("body")));
        return String.valueOf(responseBodyMap.get("body"));
    }
}