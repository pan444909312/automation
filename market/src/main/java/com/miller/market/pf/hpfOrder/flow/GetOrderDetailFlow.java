package com.miller.market.pf.hpfOrder.flow;

import com.miller.market.constants.PFBusinessConstant;
import com.miller.market.pf.hpfOrder.request.GetOrderDetailRequestDTO;
import com.miller.market.pf.hpfOrder.response.GetOrderDetailResponseDTO;
import com.miller.market.util.PFRequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;
/**
 * 融合订单详情接口请求
 */
public class GetOrderDetailFlow {
    static String uri= PFBusinessConstant.DOMAIN + "/pandafresh/api/getOrderDetail";
    public static GetOrderDetailResponseDTO getShopCardInfoFlow(GetOrderDetailRequestDTO requestDTO){
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("latitude","30.20111");
        myheaders.put("longitude","120.22136");
        PFRequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri,null,PFRequestUtils.getHeaders(),requestDTO,null, GetOrderDetailResponseDTO.class);

    }
}
