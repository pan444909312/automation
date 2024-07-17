package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.payserver.entity.PayCardRecord;
import com.miller.userapp.mapper.pay.PayCardRecordMapper;
import com.miller.userapp.module.data.DataName;
import com.miller.userapp.module.data.PandaDB;
import org.apache.ibatis.session.SqlSession;

@DataName("pay")
public class PayCardRecordSql {
    private SqlSession sqlSession;
    public  PayCardRecordSql(){
        this.sqlSession = PandaDB.getSqlSession(this.getClass());
    }
    public PayCardRecordMapper getPayCardRecordMapper(){
        return sqlSession.getMapper(PayCardRecordMapper.class);
    }
    public PayCardRecord getPayCardRecord(String cardNoMd5,String userId,String countryCode){
        QueryWrapper<PayCardRecord> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PayCardRecord> lambda = queryWrapper.lambda();
        lambda.eq(PayCardRecord::getCardNoMd5,cardNoMd5);
        lambda.eq(PayCardRecord::getCountryCode,countryCode);
        lambda.eq(PayCardRecord::getUserId,userId);
        lambda.eq(PayCardRecord::getSts,1);
        queryWrapper.last("limit 1");
        return getPayCardRecordMapper().selectOne(queryWrapper);
    }
    public int deletePayCardRecord(PayCardRecord payCardRecord){
        UpdateWrapper<PayCardRecord> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<PayCardRecord> lambda = updateWrapper.lambda();
        lambda.eq(PayCardRecord::getId,payCardRecord.getId());
        lambda.eq(PayCardRecord::getCardNoMd5,payCardRecord.getCardNoMd5());
        lambda.eq(PayCardRecord::getUserId,payCardRecord.getUserId());
        lambda.eq(PayCardRecord::getCountryCode,payCardRecord.getCountryCode());
        lambda.set(PayCardRecord::getSts,0);
        return getPayCardRecordMapper().update(updateWrapper);
    }


}
