package com.miller.userapp.module.shop.card.version2.pandaLeague.base.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.shop.card.version2.pandaLeague.base.request.InfoRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.base.response.InfoResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * @author heyuan
 * @version 1.0
 * @create: 2024-04-30 16:25
 */
public class InfoFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/shop/base/info";

    /**
     * 获取店铺信息
     */
    public static InfoResponseDTO getShopInfo(InfoRequestDTO infoRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(infoRequestDTO), null, InfoResponseDTO.class);
    }

}
