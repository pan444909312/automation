package com.miller.userapp.address.list.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.address.list.response.AddressSearchResponseDTO;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.util.LoginUtils;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author panjuxiang
 * @since 2024/5/31 16:17
 */
public class AddressSearchFlow {
    private String url = BusinessConstant.DOMAIN + "/api/user/address/search";
    public AddressSearchResponseDTO searchAddress(){

        Map<String,Object> params = new HashMap<>();
        params.put("addCode","%E6%9D%AD%E5%B7%9E");
        params.put("sceneType",1);

        return HttpUtils.sendGetRequestReturnJavaObject(url, params, LoginUtils.getHeaderWithAuth(), null, AddressSearchResponseDTO.class);
    }
}
