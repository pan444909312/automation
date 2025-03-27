package com.miller.userapp.mapper.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panda.promotion.server.dal.dataobject.activity.ActivityConfigDO;

/**
 * @author panjuxiang
 * @since 2025/2/25 18:18
 */
public interface ActivityConfigMapper extends BaseMapper<ActivityConfigDO> {

    /**
     * 修改活动预算状态
     *
     * @param subStatus
     * @param breakType
     */
    int setActivityStatus(String activitySn, int subStatus, int breakType);
}
