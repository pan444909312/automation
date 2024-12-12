package com.miller.userapp.module.data.promotion.redpacket.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.redpacket.RedPacketEntity;
import com.miller.userapp.mapper.redpacket.RedPacketMapper;
import org.apache.ibatis.session.SqlSession;

public class RedPacketSql {
    private SqlSession sqlSession;

    public RedPacketSql(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public RedPacketMapper getRedPacketMapper() {
        return sqlSession.getMapper(RedPacketMapper.class);
    }

    public RedPacketEntity selectOneByRedPacketId(Long redPacketId) {
        QueryWrapper<RedPacketEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<RedPacketEntity> lambda = queryWrapper.lambda();
        lambda.eq(RedPacketEntity::getRedPacketId, redPacketId);
        return getRedPacketMapper().selectOne(queryWrapper);
    }

    public RedPacketEntity selectEffectRedPacket(Long redPacketId) {
        QueryWrapper<RedPacketEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<RedPacketEntity> lambda = queryWrapper.lambda();
        lambda.eq(RedPacketEntity::getRedPacketId, redPacketId);
        lambda.eq(RedPacketEntity::getStatus, 0);
        lambda.eq(RedPacketEntity::getIsDel, 0);
        return getRedPacketMapper().selectOne(queryWrapper);
    }

    public int updateSendNum(Long redPacketId) {
        RedPacketEntity redPacketEntity = new RedPacketEntity();
        redPacketEntity.setRedPacketId(redPacketId);
        UpdateWrapper<RedPacketEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<RedPacketEntity> lamda = updateWrapper.lambda();
        lamda.eq(RedPacketEntity::getRedPacketId, redPacketId);
        lamda.setSql("`send_number`=`send_number`+1");
        return getRedPacketMapper().update(redPacketEntity, updateWrapper);
    }
}
