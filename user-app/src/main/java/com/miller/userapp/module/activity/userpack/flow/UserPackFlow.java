package com.miller.userapp.module.activity.userpack.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.activity.userpack.request.UserPackRequestDTO;
import com.miller.userapp.module.activity.userpack.response.UserPackResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;

public class UserPackFlow {
    static String uri= BusinessConstant.DOMAIN + "/api/app/user/userPack/list";
    public static UserPackResponseDTO flowUserPack(UserPackRequestDTO UserPackRequestDTO){
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
        return HttpUtils.sendPostRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(),UserPackRequestDTO,null, UserPackResponseDTO.class);
    }
}
