package com.miller.userapp.module.person.address.list.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.module.person.address.list.response.AddressSearchConfirmResponseDTO;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.util.LoginUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author panjuxiang
 * @since 2024/5/31 17:25
 */
public class AddressSearchConfirmFlow {
    private String url = BusinessConstant.DOMAIN + "/api/user/address/search/confirm";
    public AddressSearchConfirmResponseDTO searchAddress(){



        Map<String,Object> params = new HashMap<>();
        params.put("placeId","ChIJmaqaQym2SzQROuhNgoPRv6c");



        return HttpUtils.sendGetRequestReturnJavaObject(url, params, LoginUtils.getHeaderWithAuth(), null, AddressSearchConfirmResponseDTO.class);
    }
}
