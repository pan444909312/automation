package com.miller.userapp.module.data.activity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.miller.userapp.mapper.redpacket.UserCdKeyMapper;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * 查询用户账户里是否有神券，以及神券当前的可膨胀状态：red_packet_scopeType
 */
public class UserCdkeyInfoSql {
    SqlSession sqlSession = DBUtils.getDBOfPandaTest();

    /**
     * @param userId 用户id
     * @param redPacketId 红包id
     */
    public UserCdKeyEntity selectUserCdkeyInfo(String userId,Long redPacketId){
        UserCdKeyMapper userCdKeyMapper = sqlSession.getMapper(UserCdKeyMapper.class);
        QueryWrapper<UserCdKeyEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("red_packet_id", redPacketId);
        return userCdKeyMapper.selectOne(queryWrapper);
    }
}
