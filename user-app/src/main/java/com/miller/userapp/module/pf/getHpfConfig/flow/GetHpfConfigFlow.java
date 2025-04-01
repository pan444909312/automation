package com.miller.userapp.module.pf.getHpfConfig.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.pf.getHpfConfig.response.GetHpfConfigResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
/**
 * 融合开关接口请求
 */
public class GetHpfConfigFlow {
    static String uri= BusinessConstant.DOMAIN + "/api/user/supermarket/getHpfConfig";
    public static GetHpfConfigResponseDTO getHpfConfigFlow(){
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("latitude","30.20111");
        myheaders.put("longitude","120.22136");
        RequestUtils.setHeaders(myheaders);
        return HttpUtils.sendGetRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),null,
                GetHpfConfigResponseDTO.class );

    }
}
