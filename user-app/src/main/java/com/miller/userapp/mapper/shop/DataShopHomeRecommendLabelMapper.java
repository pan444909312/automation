package com.miller.userapp.mapper.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.shop.DataShopHomeRecommendLabelEntity;

/**
 * hp_data_shop_home_recommend_label表
 */
public interface DataShopHomeRecommendLabelMapper extends BaseMapper<DataShopHomeRecommendLabelEntity> {

    /**
     * 根据商家id获取回头客数量
     * @param shopId
     * @return
     */
    int getReturnedVis2ByShopId(Long shopId);

    /**
     * 根据商家id获取月销量
     * @param shopId
     * @return
     */
    int getMonthlySalesByShopId(Long shopId);

    /**
     * 根据商家id获取店铺180天评价分数大于4分的人数合计
     * @param shopId
     * @return
     */
    int getEvaluationOver4Usercnt180dByShopId(Long shopId);

    /**
     * 根据商家id获取收藏店铺的人数
     * @param shopId
     * @return
     */
    int getCollectShopUsercntByShopId(Long shopId);



}
