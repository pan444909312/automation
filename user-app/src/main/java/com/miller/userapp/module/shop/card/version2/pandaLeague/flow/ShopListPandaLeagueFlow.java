package com.miller.userapp.module.shop.card.version2.pandaLeague.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListPandaLeagueRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_熊猫联盟店铺流
 * @author panjuxiang
 * @since 2024/11/20 10:58
 */
public class ShopListPandaLeagueFlow {
    /**
     * 接口_熊猫联盟店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/panda/league/channel";

    /**
     * 流程_获取熊猫联盟店铺流
     */
    public static ShopListResponseDTO getShopList(ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListPandaLeagueRequestDTO), null, ShopListResponseDTO.class);
    }
}
