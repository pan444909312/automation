package com.miller.market.pf.redPacket.queryPFRedPacketList.flow;

import com.miller.market.constants.PFBusinessConstant;
import com.miller.market.pf.redPacket.queryPFRedPacketList.request.QueryPFRedPacketListRequestDTO;
import com.miller.market.pf.redPacket.queryPFRedPacketList.response.QueryPFRedPacketListResponseDTO;
import com.miller.market.util.PFRequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;

/**
 * App端根据红包id过滤出生效红包
 *
 */
public class QueryPFRedPacketListFlow {
    private static final String uri = PFBusinessConstant.DOMAIN + "/pandafresh/api/redPacket/list";

    public static QueryPFRedPacketListResponseDTO getRedPacketCount(QueryPFRedPacketListRequestDTO requestDTO) {
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("latitude","30.20111");
        myheaders.put("longitude","120.22136");
        PFRequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,PFRequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                PFRequestUtils.putBodyOfJson(requestDTO), null, QueryPFRedPacketListResponseDTO.class);
    }


}