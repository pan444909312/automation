package com.miller.userapp.module.person.address.list.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.module.person.address.list.response.AddressListResponseDTO;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.util.LoginUtils;

/**
 * @author panjuxiang
 * @since 2024/3/21 19:53
 */
public class AddressListFlow {

    private String url = BusinessConstant.DOMAIN + "/api/user/delivery/address";

    public AddressListResponseDTO getAddressList() {

        return HttpUtils.sendGetRequestReturnJavaObject(url, null, LoginUtils.getHeaderWithAuth(), null, AddressListResponseDTO.class);
    }
}
