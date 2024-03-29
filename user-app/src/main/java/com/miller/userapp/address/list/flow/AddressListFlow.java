package com.miller.userapp.address.list.flow;

import com.alibaba.fastjson.JSON;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.address.list.response.AddressListResponseDTO;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author panjuxiang
 * @since 2024/3/21 19:53
 */
public class AddressListFlow {

    private String url = BusinessConstant.DOMAIN + "/api/user/delivery/address";

    public AddressListResponseDTO getAddressList() {

        RequestUtils.setHeaders(null);

        Map<String, Object> headers = new HashMap<>();

        headers.put("Authorization", "5e184b7f2bb29034c7e7a1850c8dceab");


        return HttpUtils.sendGetRequestReturnJavaObject(url, null, headers, null, AddressListResponseDTO.class);
    }
}
