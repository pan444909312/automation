package com.miller.userapp.module.data.activity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.miller.userapp.mapper.redpacket.UserCdKeyMapper;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

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

    /**
 * 修改红包的可膨胀状态 和当前金额
 *
 * @param userId 用户ID
 * @param redPacketId 红包ID
 * @param newScopeType 新的红包范围类型
 * @param newPrice 新的红包价格
 * @return 如果更新成功返回true，否则返回false
 */
public boolean updateRedPacketScopeTypeAndPrice(String userId, Long redPacketId, Integer newScopeType, int newPrice) {
    // 获取UserCdKeyMapper映射器
    UserCdKeyMapper userCdKeyMapper = sqlSession.getMapper(UserCdKeyMapper.class);

    // 查询现有的UserCdKeyEntity
    QueryWrapper<UserCdKeyEntity> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.eq("red_packet_id", redPacketId);
    UserCdKeyEntity entity = userCdKeyMapper.selectOne(queryWrapper);

    if (entity != null) {
        // 更新redpacketScopeType和redpacketprice
        entity.setRedPacketScopeType(newScopeType);
        entity.setRedPacketPrice(newPrice);

        // 执行更新
        return userCdKeyMapper.updateById(entity) > 0;
    }
    return false;
}
/**
 * 修改红包的使用状态
 *
 * @param userId 用户ID
 * @param redPacketId 红包ID
 * @param isUsed 新的使用状态
 * @return 如果更新成功返回true，否则返回false
 */
public boolean updateRedPacketUsedStatus(String userId, Long redPacketId, Byte isUsed) {
    // 获取UserCdKeyMapper映射器
    UserCdKeyMapper userCdKeyMapper = sqlSession.getMapper(UserCdKeyMapper.class);

    // 查询现有的UserCdKeyEntity
    QueryWrapper<UserCdKeyEntity> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.eq("red_packet_id", redPacketId);
    List<UserCdKeyEntity> entities = userCdKeyMapper.selectList(queryWrapper);

    if (!entities.isEmpty()) {
        // 批量更新is_used状态
        for (UserCdKeyEntity entity : entities) {
            entity.setIsUsed(isUsed);
        }
        
        // 执行批量更新
        for (UserCdKeyEntity entity : entities) {
            userCdKeyMapper.updateById(entity);
        }
        return true;
    }
    return false;
}

    /**
     * @param userId 除指定红包外其他红包都置为is_used=1
     * @param redPacketId
     * @return
     */
public boolean updateRedPacketUsedStatusExclude(String userId, Long redPacketId) {
    // 获取UserCdKeyMapper映射器
    UserCdKeyMapper userCdKeyMapper = sqlSession.getMapper(UserCdKeyMapper.class);

    // 查询所有red_packet_id不为指定redPacketId的数据
    QueryWrapper<UserCdKeyEntity> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.ne("red_packet_id", redPacketId);
    queryWrapper.eq("is_used", 0);
    List<UserCdKeyEntity> entities = userCdKeyMapper.selectList(queryWrapper);

    if (!entities.isEmpty()) {
        // 批量更新is_used状态为1
        for (UserCdKeyEntity entity : entities) {
            entity.setIsUsed((byte) 1);
        }
        for (UserCdKeyEntity entity : entities) {
            userCdKeyMapper.updateById(entity);
        }
        return true;
    }
    return false;
}}
