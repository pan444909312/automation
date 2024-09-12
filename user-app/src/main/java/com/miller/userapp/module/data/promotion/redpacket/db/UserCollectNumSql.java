package com.miller.userapp.module.data.promotion.redpacket.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.redpacket.UserCollectNumEntity;
import com.miller.userapp.mapper.redpacket.UserCollectNumMapper;
import org.apache.ibatis.session.SqlSession;

public class UserCollectNumSql {
    private SqlSession sqlSession;
    public  UserCollectNumSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public UserCollectNumMapper getUserCollectNumMapper(){
        return sqlSession.getMapper(UserCollectNumMapper.class);
    }
    public UserCollectNumEntity selectByUserIdAndRedPacketId(Long userId,Long redPacketId){
        QueryWrapper<UserCollectNumEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserCollectNumEntity> lamda=queryWrapper.lambda();
        lamda.eq(UserCollectNumEntity::getIsDel,0);
        lamda.eq(UserCollectNumEntity::getUserId,userId);
        lamda.eq(UserCollectNumEntity::getRedPacketId,redPacketId);
        return getUserCollectNumMapper().selectOne(queryWrapper);
    }

    public void insertSelective(UserCollectNumEntity userCollectNum){
        getUserCollectNumMapper().insert(userCollectNum);
    }

    public int updateNumByUserIdAndRedPacketId(Long time,Long userId,Long redPacketId) {
        UpdateWrapper<UserCollectNumEntity> queryWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<UserCollectNumEntity> lamda = queryWrapper.lambda();
        lamda.eq(UserCollectNumEntity::getUserId, userId);
        lamda.eq(UserCollectNumEntity::getRedPacketId, redPacketId);
        lamda.eq(UserCollectNumEntity::getIsDel, 0);
        lamda.set(UserCollectNumEntity::getUpdateTime,time);
        lamda.setSql("`collect_num`=`collect_num`+1");
        lamda.setSql("`un_used_num`=`un_used_num`+1");

        return  getUserCollectNumMapper().update(queryWrapper);
    }
}
