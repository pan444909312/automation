package com.miller.userapp.module.order.member;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.miller.userapp.mapper.member.MemberCityDao;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

public class MemberByToCreateVirtualTest {
    //这里只判断虚单会员模块memberBuyDetailOrderShowRes的显示，前提是虚单其他字段都是正确的，比如城市
    //1.城市下是否是会员
    //2.是会员的话，还要看memberBuyType（即MemberCombinedTypeEnum为1和0的情况），续费合单和未开通会员合单
    @Test
    public void memberBuyDetailOrderShowResTest(){
        //会员城市
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        MemberCityEntity memberCityEntity = sqlSession.getMapper(MemberCityDao.class).getMemberCityByCityId(188L);
        System.out.println(JSON.toJSON(memberCityEntity));
    }
}
