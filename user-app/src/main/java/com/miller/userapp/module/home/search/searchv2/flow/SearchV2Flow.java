package com.miller.userapp.module.home.search.searchv2.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.search.searchv2.request.SearchV2RequestDTO;
import com.miller.userapp.module.home.search.searchv2.response.SearchV2ResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;

/**
 * 搜索v2接口请求
 */
public class SearchV2Flow {
    static String uri= BusinessConstant.DOMAIN + "/api/user/v2/search";
    public static SearchV2ResponseDTO searchV2Flow(SearchV2RequestDTO SearchV2RequestDTO){
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        RequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(),SearchV2RequestDTO,null,SearchV2ResponseDTO.class);
    }
}
