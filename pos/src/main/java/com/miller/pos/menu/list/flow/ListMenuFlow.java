package com.miller.pos.menu.list.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.menu.add.request.AddMenuRequestDTO;
import com.miller.pos.menu.add.response.AddMenuResponseDTO;
import com.miller.pos.menu.list.response.ListMenuResponseDTO;
import com.miller.pos.util.RequestUtils;

import java.util.HashMap;


/**
 * 查询店铺详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class ListMenuFlow {

    private final static String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/menu/"+BusinessConstant.shop_id+"/categories";


    public static ListMenuResponseDTO listMenu(boolean secondCategories) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("second_categories",secondCategories);
        return RequestUtils.sendGetRequest(uri,params, ListMenuResponseDTO.class);
    }

}
