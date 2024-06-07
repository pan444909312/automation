package com.miller.userapp.address.list.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.address.list.request.LocationChangeRequestDTO;
import com.miller.userapp.address.list.response.LocationChangeResponseDTO;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.util.LoginUtils;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author panjuxiang
 * @since 2024/6/3 9:25
 */
public class LocationChangeFlow {
    private String url = BusinessConstant.DOMAIN + "/api/app/user/location/change/notice";
    public LocationChangeResponseDTO locationChange(LocationChangeRequestDTO locationChangeRequestDTO){



        return HttpUtils.sendPostRequestReturnJavaObject(url, null, LoginUtils.getHeaderWithAuth(),locationChangeRequestDTO,null,LocationChangeResponseDTO.class);

    }
}
