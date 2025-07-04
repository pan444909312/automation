package com.miller.userapp.module.data.activity;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.popup.ActivityPopupRedPacketGroupRedPacketCollect;
import com.miller.userapp.mapper.redpacket.ActivityPopupRedPacketGroupRedPacketCollectMapper;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * 清除用户天降活动数据
 */
public class UserActivityDeleteSql {
    SqlSession sqlSession = DBUtils.getDBOfPandaTest();

    /**
     * @param userId ：要删除天降领取记录的用户id
     */
    public void deleteCollectRecords(String userId){
        ActivityPopupRedPacketGroupRedPacketCollectMapper mapper = sqlSession.getMapper(ActivityPopupRedPacketGroupRedPacketCollectMapper.class);
        UpdateWrapper<ActivityPopupRedPacketGroupRedPacketCollect> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("user_id",userId);
        mapper.delete(deleteWrapper);
    }

}
