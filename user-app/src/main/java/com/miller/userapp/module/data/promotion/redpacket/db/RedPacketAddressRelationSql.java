package com.miller.userapp.module.data.promotion.redpacket.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.userapp.mapper.redpacket.RedPacketAddressRelationMapper;
import com.panda.promotion.server.dal.dataobject.redpacket.RedPacketAddressRelationDO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class RedPacketAddressRelationSql {
    private SqlSession sqlSession;

    public RedPacketAddressRelationSql(SqlSession sqlSession) {
        this.sqlSession=sqlSession;
    }

    public RedPacketAddressRelationMapper getRedPacketAddressRelationMapper(){
        return sqlSession.getMapper(RedPacketAddressRelationMapper.class);
    }

    public List<RedPacketAddressRelationDO> getAddTagIdByRedPacketId(Long redPacketId){
        QueryWrapper<RedPacketAddressRelationDO> queryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<RedPacketAddressRelationDO> lambdaQueryWrapper=queryWrapper.lambda();
        lambdaQueryWrapper.eq(RedPacketAddressRelationDO::getRedPacketId,redPacketId);
        lambdaQueryWrapper.eq(RedPacketAddressRelationDO::getIsDel,0);
        return getRedPacketAddressRelationMapper().selectList(queryWrapper);
    }
}
