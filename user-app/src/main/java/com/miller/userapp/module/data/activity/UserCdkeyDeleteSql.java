package com.miller.userapp.module.data.activity;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.miller.userapp.mapper.redpacket.UserCdKeyMapper;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * 删除红包（这里是用来删神券避免数据过多的，实际可删除任意红包）
 */
public class UserCdkeyDeleteSql {
    SqlSession sqlSession = DBUtils.getDBOfPandaTest();

    /**
     * @param userId 要删除红包的用户Id
     * @param redPacketId 要删除红包的红包id
     */
    public void deleteUserCdkey(String userId,Long redPacketId) {
        UserCdKeyMapper userCdKeyMapper = sqlSession.getMapper(UserCdKeyMapper.class);
        UpdateWrapper<UserCdKeyEntity> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("user_id", userId);
        deleteWrapper.eq("red_packet_id", redPacketId);
        userCdKeyMapper.delete(deleteWrapper);
    }
}
