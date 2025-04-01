package com.miller.userapp.module.pf.getShopCardInfo.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.activity.userpack.response.UserPackResponseDTO;
import com.miller.userapp.module.pf.getShopCardInfo.request.GetShopCardInfoRequestDTO;
import com.miller.userapp.module.pf.getShopCardInfo.response.GetShopCardInfoResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
/**
 * 融合开关接口请求
 */
public class GetShopCardInfoFlow {
    static String uri= BusinessConstant.PFDOMAIN + "/pandafresh/api/getShopCardInfo";
    public static GetShopCardInfoResponseDTO getShopCardInfoFlow(GetShopCardInfoRequestDTO requestDTO){
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("latitude","30.20111");
        myheaders.put("longitude","120.22136");
        RequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(),requestDTO,null, GetShopCardInfoResponseDTO.class);

    }
}
