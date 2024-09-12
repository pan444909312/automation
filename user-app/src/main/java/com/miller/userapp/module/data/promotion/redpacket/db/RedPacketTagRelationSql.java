package com.miller.userapp.module.data.promotion.redpacket.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.userapp.mapper.redpacket.RedPacketTagRelationMapper;
import com.panda.promotion.server.dal.dataobject.redpacket.RedPacketTagRelationDO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class RedPacketTagRelationSql {

    private SqlSession sqlSession;
    public RedPacketTagRelationSql(SqlSession sqlSession){
        this.sqlSession=sqlSession;
    }
    public RedPacketTagRelationMapper getRedPacketTagRelationMapper(){
        return sqlSession.getMapper(RedPacketTagRelationMapper.class);
    }
    public List<RedPacketTagRelationDO> getTagIdByRedPacketId(Long redpacketId){
        QueryWrapper<RedPacketTagRelationDO> queryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<RedPacketTagRelationDO> lamda = queryWrapper.lambda();
        lamda.eq(RedPacketTagRelationDO::getRedPacketId,redpacketId);
        lamda.eq(RedPacketTagRelationDO::getIsDelete,0);
        return getRedPacketTagRelationMapper().selectList(queryWrapper);
    }
}
