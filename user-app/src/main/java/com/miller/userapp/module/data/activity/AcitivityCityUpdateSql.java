package com.miller.userapp.module.data.activity;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.miller.userapp.mapper.redpacket.ActivityCityRuleConfigMapper;
import com.miller.userapp.util.DBUtils;
import com.panda.erp.server.dal.dataobject.popup.redpacket.group.ActivityPopupRedPacketSendRuleConfig;
import org.apache.ibatis.session.SqlSession;

/**
 * 修改天降相关配置
 */
public class AcitivityCityUpdateSql {
    SqlSession sqlSession = DBUtils.getDBOfPandaTest();
    /**
     * @param city :城市,修改指定城市的发放规则为直塞
     */
    public void setCityRuleAuto(String city,int needCollect){
        ActivityCityRuleConfigMapper activityCityRuleConfigMapper = sqlSession.getMapper(ActivityCityRuleConfigMapper.class);
        UpdateWrapper<ActivityPopupRedPacketSendRuleConfig> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("city",city);
        updateWrapper.set("need_collect",needCollect);
        activityCityRuleConfigMapper.update(updateWrapper);
    }
}
