package com.miller.pos.menu.edit.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.menu.add.request.AddMenuRequestDTO;
import com.miller.pos.menu.add.response.AddMenuResponseDTO;
import com.miller.pos.menu.edit.request.EditMenuRequestDTO;
import com.miller.pos.menu.edit.response.EditMenuResponseDTO;
import com.miller.pos.util.RequestUtils;


/**
 * 查询店铺详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class EditMenuFlow {

    private final static String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/menu/categories/"+BusinessConstant.menuId;


    public static EditMenuResponseDTO editMenu(EditMenuRequestDTO editMenuRequestDTO) {
        return RequestUtils.sendPutRequest(uri, editMenuRequestDTO, EditMenuResponseDTO.class);
    }

}
