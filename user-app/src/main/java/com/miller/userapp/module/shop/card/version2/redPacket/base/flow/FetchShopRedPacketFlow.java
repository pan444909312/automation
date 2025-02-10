package com.miller.userapp.module.shop.card.version2.redPacket.base.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.shop.card.version2.redPacket.base.request.FetchShopRedPacketRequestDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.base.response.FetchShopRedPacketRetResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class FetchShopRedPacketFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/redPacket/fetchShopRedPacket";
    /**
     * 获取店铺信息
     */
    public static FetchShopRedPacketRetResponseDTO fetchShopRedPacketFlow(FetchShopRedPacketRequestDTO fetchShopRedPacketReq) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(fetchShopRedPacketReq), null, FetchShopRedPacketRetResponseDTO.class);
    }
}
