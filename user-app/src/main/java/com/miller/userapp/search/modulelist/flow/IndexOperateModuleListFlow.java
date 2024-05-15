package com.miller.userapp.search.modulelist.flow;

import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.search.modulelist.request.IndexOperateModuleListRequestDTO;
import com.miller.userapp.search.modulelist.response.IndexOperateModuleListResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;

/**
 * 请求首页接口模块：/api/user/index/operateModuleList
 */
public class IndexOperateModuleListFlow {
    static String uri= BusinessConstant.DOMAIN + "/api/user/index/operateModuleList";
    public static IndexOperateModuleListResponseDTO flowIndexOperateModuleList(){
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        RequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri,null, RequestUtils.getHeaders(),null,null,IndexOperateModuleListResponseDTO.class);
    }

}
