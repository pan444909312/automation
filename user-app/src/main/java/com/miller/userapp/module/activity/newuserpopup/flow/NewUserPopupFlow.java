package com.miller.userapp.module.activity.newuserpopup.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.activity.newuserpopup.response.NewUserPopupResponseDTO;
import com.miller.userapp.module.activity.newuserpopup.request.NewUserPopupRequestDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求首页pop接口，其中deviceId暂时写死：d88a89d4913c70bd
 */
public class NewUserPopupFlow {
    private static final String uri= BusinessConstant.DOMAIN + "/api/user/popup";
    public static NewUserPopupResponseDTO NewUserPopupflow(NewUserPopupRequestDTO NewUserPopupRequestdto){
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("uniquetoken", "d88a89d4913c70bd");
        myheaders.put("longitude","120.216719");
        myheaders.put("latitude","30.203436");
        myheaders.put("realLongitude","120.216719");
        myheaders.put("realLatitude","30.203436");
        myheaders.put("countryCode","CN");
        myheaders.put("language","CN");
        RequestUtils.setHeaders(myheaders);
        Map<String, Object> headers=RequestUtils.getHeaders();
        headers.remove("cityName");
        NewUserPopupResponseDTO responseDTO=HttpUtils.sendGetRequestReturnJavaObject(uri,RequestUtils.putBodyOfForm(NewUserPopupRequestdto),headers,null,NewUserPopupResponseDTO.class);
        return HttpUtils.sendGetRequestReturnJavaObject(uri,RequestUtils.putBodyOfForm(NewUserPopupRequestdto),headers,null,NewUserPopupResponseDTO.class);

    }
}
