package com.miller.userapp.search.category.flow;

import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.search.category.request.SearchCategoryRequestDTO;
import com.miller.userapp.search.category.response.SearchCategoryResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;

/**
 * 搜索中间页：分类接口
 */
public class SearchCategoryFlow {
    static String uri= BusinessConstant.DOMAIN + "/api/user/search/categories";

    /**
     * @param SearchCategoryRequest 搜索中间页分类接口入参
     * @return SearchCategoryResponseDTO 搜索中间页分类接口返回
     */
    public SearchCategoryResponseDTO flowSearchCategory(SearchCategoryRequestDTO SearchCategoryRequest){
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
        SearchCategoryResponseDTO SearchCategoryResponseDTO=HttpUtils.sendPostRequestReturnJavaObject(uri,null, RequestUtils.getHeaders(),SearchCategoryRequest,null,SearchCategoryResponseDTO.class);
//        缓存类目接口返回值
        CacheUtils.set("SEARCH_CATEGORY_OBJECT_KEY",SearchCategoryResponseDTO);
        return SearchCategoryResponseDTO;

    }
}
