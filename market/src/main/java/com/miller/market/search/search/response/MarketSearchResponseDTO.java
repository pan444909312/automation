package com.miller.market.search.search.response;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.market.dto.BasicResponseDTO;
import com.panda.market.dal.dto.IndexGoodsListDTO;

/**
 * 搜索响应对象
 */
public class MarketSearchResponseDTO extends BasicResponseDTO<Page<IndexGoodsListDTO>> {

}
