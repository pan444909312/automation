package com.miller.market.pf.getShopCardInfo.flow;

import com.miller.market.constants.PFBusinessConstant;
import com.miller.market.pf.getShopCardInfo.request.GetShopCardInfoRequestDTO;
import com.miller.market.util.PFRequestUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.market.pf.getShopCardInfo.response.GetShopCardInfoResponseDTO;

import java.util.HashMap;
/**
 * 融合开关接口请求
 */
public class GetShopCardInfoFlow {
    static String uri= PFBusinessConstant.DOMAIN + "/pandafresh/api/getShopCardInfo";
    public static GetShopCardInfoResponseDTO getShopCardInfoFlow(GetShopCardInfoRequestDTO requestDTO){
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("latitude","30.20111");
        myheaders.put("longitude","120.22136");
        PFRequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri,null,PFRequestUtils.getHeaders(),requestDTO,null, GetShopCardInfoResponseDTO.class);

    }
}
