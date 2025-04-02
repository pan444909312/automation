package com.miller.userapp.module.pf.hpfOrder.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.pf.hpfOrder.request.GetOrderDetailRequestDTO;
import com.miller.userapp.module.pf.hpfOrder.response.GetOrderDetailResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
/**
 * 融合订单详情接口请求
 */
public class GetOrderDetailFlow {
    static String uri= BusinessConstant.PFDOMAIN + "/pandafresh/api/getOrderDetail";
    public static GetOrderDetailResponseDTO getShopCardInfoFlow(GetOrderDetailRequestDTO requestDTO){
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("latitude","30.20111");
        myheaders.put("longitude","120.22136");
        RequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(),requestDTO,null, GetOrderDetailResponseDTO.class);

    }
}
