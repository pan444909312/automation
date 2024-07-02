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

    public static Map<String,Object> getCommonHeader(){
        Map<String, Object> headers = new HashMap<>();
        headers.put("latitude", "27.909985");
        headers.put("longitude", "120.809057");
        headers.put("host", "app-test.hungrypanda.cn");
        headers.put("cityname", "%E6%B8%A9%E5%B7%9E");
        headers.put("zipCode", BusinessConstant.zipCode);
        headers.put("countryCode", BusinessConstant.countryCode);
        headers.put("version", "8.38.5");
        headers.put("platform", BusinessConstant.platform);
        headers.put("type", "1");
        headers.put("appTypeId", BusinessConstant.appTypeId);
        headers.put("language", BusinessConstant.language);
        headers.put("testGroup", "I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,S_H_R_L_TEST_GROUP_5,22,23,30,31,32,NUMBER_MASKING_00,33,34,35,40,39,45,50,49,52,53,55,56,HPF,FASTD01,YSDCS02,IST02,HYBQ01,SKEQ01,XRJ01,TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,ZDFQ02,SKXRB01,ABT02,XRTC01,QYTCD01,SMSS02,RRREC01,ZFBMM01,SSJLY01,SPSS01,MRBX02,PLCC01,SXAU01,PAYTO01,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS01,RTR01,SYUI01,SWS01,HHAB02,YHTX01,TCZT01,XTZA01,XTZB01");
        headers.put("pandaAppId", BusinessConstant.pandaAppId);
        headers.put("timezoneoffset", "-480");
        headers.put("content-type", "application/json;charset=UTF-8");

//        headers.put("androidSafeToken", BusinessConstant.androidSafeToken);
//        headers.put("marketchannel", BusinessConstant.unionId);
//        headers.put("device_safe_token", BusinessConstant.authorization);
//        headers.put("smblackbox", BusinessConstant.authorization);
//        headers.put("uniqueToken", BusinessConstant.uniqueToken);
        return headers;
    }

}
