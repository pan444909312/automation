package com.miller.userapp.module.order.shopping.settlement.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.order.shopping.settlement.request.DeliveryActionEditRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.DeliveryActionConfigListResponseDTO;
import com.miller.userapp.module.order.shopping.settlement.response.DeliveryActionEditResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 结算设置地址的交付方式
 */
public class DeliveryActionFlow {
    private static final String uriList = BusinessConstant.DOMAIN+"/api/app/user/delivery/action/list";
    private static final String uriEdit = BusinessConstant.DOMAIN+"/api/app/user/delivery/action/edit";

    /**
     * 获取当前配置的所有有二级的交付方式
     */
    public static DeliveryActionConfigListResponseDTO deliveryActionListFlow(){
        RequestUtils.getHeaders().put("content-type","application/json");
       return  HttpUtils.sendPostRequestReturnJavaObject(uriList,null,RequestUtils.getHeaders(),null,null, DeliveryActionConfigListResponseDTO.class);
    }

    /**
     * 设置地址对应的交付方式
     */
    public static DeliveryActionEditResponseDTO delivertActionEditFlow(DeliveryActionEditRequestDTO deliveryActionEditRequestDTO){
        RequestUtils.getHeaders().put("content-type","application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uriEdit,null,RequestUtils.getHeaders(),RequestUtils.putBodyOfJson(deliveryActionEditRequestDTO),null, DeliveryActionEditResponseDTO.class);
    }
}
