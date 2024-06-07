package com.miller.userapp.util;

import com.alibaba.fastjson.JSONPath;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author panjuxiang
 * @since 2024/4/3 15:22
 */
public class LoginUtils {

    private static String loginApi = BusinessConstant.DOMAIN + "/api/user/combine/login";
    private static String accout = "13990000001";
    private static String password = "e10adc3949ba59abbe56e057f20f883e";

    private static Map<String,Object> headers = null;

    public static Map<String,Object> getHeader(){
        if (headers == null){
            headers = new HashMap<>();
            headers.put("Content-Type","application/json");

            RequestUtils.setHeaders(headers);
            headers = RequestUtils.getHeaders();
        }
        return headers;
    }

    /**
     * 登录获取token
     * @return 用户身份token
     */
    public static String loginReturnToken(){

        String loginParam = "{\"areaCode\":\"86\",\"account\":\"" + accout +"\",\"password\":\"" + password + "\",\"cityName\":\"杭州市\",\"type\":2,\"distinctId\":\"AFF39007-ADD4-4B00-8B5D-4E24906115F1\"}";

        String loginResp = HttpUtils.sendPostRequestReturnBody(loginApi, null, getHeader(), loginParam, null);

        System.out.println(loginResp);
        return (String)JSONPath.read(loginResp, "$.result.accessToken");
    }

    /**
     * 获取带身份信息的默认请求头
     * @return
     */
    public static Map<String,Object> getHeaderWithAuth(){

        headers = getHeader();
        if (StringUtils.isEmpty(headers.get("authorization"))){
            headers.put("Authorization", loginReturnToken());
        }

        return headers;
    }

}
