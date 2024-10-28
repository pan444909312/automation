package com.miller.pos.menu.info.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.menu.info.response.InfoMenuResponseDTO;
import com.miller.pos.util.RequestUtils;

import java.util.HashMap;


/**
 * 查询店铺详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class InfoMenuFlow {

    private final static String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/menu/categories/"+BusinessConstant.menuId;


    public static InfoMenuResponseDTO infoMenu(boolean secondCategories) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("second_categories",secondCategories);
        return RequestUtils.sendGetRequest(uri,params, InfoMenuResponseDTO.class);
    }

}
