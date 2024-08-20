package com.miller.pos.menu.seq.flow;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.menu.list.response.ListMenuResponseDTO;
import com.miller.pos.menu.seq.request.BatchMenuSeqRequestDTO;
import com.miller.pos.menu.seq.response.BatchMenuSeqResponseDTO;
import com.miller.pos.util.RequestUtils;

import java.util.HashMap;


/**
 * 查询店铺详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:45:30
 */
public class BatchMenuSeqFlow {

    private final static String uri = BusinessConstant.DOMAIN + "/api/pos/v1/open/menu/" + BusinessConstant.shop_id + "/categories/seq";


    public static BatchMenuSeqResponseDTO batchSeqMenu(BatchMenuSeqRequestDTO requestDTO) {
        return RequestUtils.sendPostRequest(uri,requestDTO, BatchMenuSeqResponseDTO.class);
    }

}
