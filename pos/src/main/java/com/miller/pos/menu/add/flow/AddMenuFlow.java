package com.miller.pos.menu.add.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.menu.add.request.AddMenuRequestDTO;
import com.miller.pos.menu.add.response.AddMenuResponseDTO;
import com.miller.pos.util.RequestUtils;


/**
 * 查询店铺详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class AddMenuFlow {

    private final static String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/menu/" + BusinessConstant.shop_id + "/categories";


    public static AddMenuResponseDTO addMenu(AddMenuRequestDTO addMenuRequestDTO) {
        return RequestUtils.sendPostRequest(uri, addMenuRequestDTO, AddMenuResponseDTO.class);
    }

}
