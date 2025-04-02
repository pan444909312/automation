package com.miller.userapp.module.game.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.game.request.GameRequestDTO;
import com.miller.userapp.module.game.response.LuckyDrawResponseDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * @author panjuxiang
 * @since 2025/1/8 10:15
 */
public class LuckyDrawFlow {

    /**
     * 接口_抽奖
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/activity/game/luckyDraw";


    /**
     * 流程_抽奖
     */
    public static LuckyDrawResponseDTO luckyDraw(GameRequestDTO gameRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(gameRequestDTO), null, LuckyDrawResponseDTO.class);
    }
}
