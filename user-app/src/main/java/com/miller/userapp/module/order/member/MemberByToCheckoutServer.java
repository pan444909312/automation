package com.miller.userapp.module.order.member;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.res.member.MemberBuyDetailRes;
import com.hungrypanda.app.server.common.AppContext;
import com.hungrypanda.app.server.dao.member.MemberDao;
import com.hungrypanda.app.server.dto.member.MemberInfo;
import com.hungrypanda.app.server.entity.member.MemberAutoRenewEntity;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.miller.userapp.mapper.member.MemberCityDao;
import com.miller.userapp.module.data.member.db.MemberAutoRenewSql;
import com.miller.userapp.util.DBUtils;
import com.panda.erp.server.dal.dataobject.member.MemberCity;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

public class MemberByToCheckoutServer {
    SqlSession sqlSession = DBUtils.getDBOfPandaTest();
    MemberAutoRenewSql memberAutoRenewSql = new MemberAutoRenewSql(sqlSession);

    public MemberInfo getMemberInfoByCityName(String cityName,Long userId) {

        if (userId == null || StringUtils.isEmpty(cityName)) {
            return null;
        }
        Long currentTime = System.currentTimeMillis();
        // 先查询新会员,老会员只有member,没有member_entity,member_city_relation
        MemberInfo memberInfo = sqlSession.getMapper(MemberDao.class).getMemberByUserIdAndCityName(userId,cityName,currentTime);
        return memberInfo;
    }
    public MemberBuyDetailRes getMemberBuyDetail(Long cityId,Long userId){
        //协议暂不校验，后期写死

        MemberBuyDetailRes memberBuyDetailRes = new MemberBuyDetailRes();
        memberBuyDetailRes.setProtocolUrl("");
        MemberCityEntity memberCityEntity = sqlSession.getMapper(MemberCityDao.class).getMemberCityByCityId(cityId);
        MemberAutoRenewEntity renewEntity = memberAutoRenewSql.getMemberAutoRenew(userId);
        memberBuyDetailRes.setAddressConfigId(cityId);
        return  memberBuyDetailRes;
    }

}
