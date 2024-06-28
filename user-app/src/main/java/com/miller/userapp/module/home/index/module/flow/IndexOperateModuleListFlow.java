package com.miller.userapp.module.home.index.module.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.index.module.response.IndexOperateModuleListResponseDTO;
import com.miller.userapp.util.LoginUtils;

/**
 * @author panjuxiang
 * @since 2024/6/27 20:42
 */
public class IndexOperateModuleListFlow {
    private String url = BusinessConstant.DOMAIN + "/api/user/index/operateModuleList";

    /**
     * 首页operateModuleList
     * @return
     */
    public IndexOperateModuleListResponseDTO getIndexOperateModuleList() {


        IndexOperateModuleListResponseDTO indexOperateModuleListResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(url, null, LoginUtils.getCommonHeader(), "{}", null, IndexOperateModuleListResponseDTO.class);

        return indexOperateModuleListResponseDTO;
    }
}
