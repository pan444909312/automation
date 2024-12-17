package com.miller.erp.moudle.manage.merchant.member.delete;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.member.MemberEntityEntity;
import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.mapper.member.MemberEntityMapper;
import com.miller.erp.util.DBUtils;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;

/**
 * 流程_会员管理-会员列表-删除制定会员
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/24 11:17:51
 */
public class MemberDeleteFlow {

    /**
     * 接口_会员管理-会员列表-删除会员
     * 这个接口返回的是HTML。。。。。
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_BACKUP + "/admin/user/member/deleteMember.htm";

    /**
     * 通过会员ID删除指定会员
     * <p>
     * 需要优化：目前此接口返回的是html，等后段改成返回json之后在优化.
     *
     * @param memberId 会员主键ID
     * @see #deleteMemberByUserId(String)
     */
    @Deprecated
    public static void deleteMemberById(String memberId) {
        try {
            Long.valueOf(memberId);
        } catch (java.lang.NumberFormatException numberFormatException) {
            throw new IllegalArgumentException("memberId必须为数字");
        }
        var params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        ERPLoginFlow.erpLoginByCookie();

        // 响应结果为：{\"code\":4108,\"name\":\"删除会员成功\"}
        String responseBody = HttpUtils.sendGetRequestReturnBody(uri, params, RequestUtils.getHeaders(), null);
        // 这个接口不管ID是否存在都会返回成功。。。。
        if (!responseBody.contains("成功")) {
            throw new RuntimeException("删除会员失败");
        }
    }

    /**
     * 通过用户ID删除用户开通的会员
     *
     * @param userId 用户主键ID
     */
    public static void deleteMemberByUserId(String userId) {

        SqlSession sqlSession = DBUtils.getDBOfPandaTest();

        MemberEntityMapper memberEntityMapper = sqlSession.getMapper(MemberEntityMapper.class);
        LambdaUpdateWrapper<MemberEntityEntity> memberEntityEntityLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        memberEntityEntityLambdaUpdateWrapper.eq(MemberEntityEntity::getUserId, userId);
//        memberEntityEntityLambdaUpdateWrapper.set(MemberEntityEntity::getIsDel, Byte.parseByte("1"));
//        memberEntityEntityLambdaUpdateWrapper.set(MemberEntityEntity::getStatus, Byte.parseByte("1"));
//        memberEntityMapper.update(new MemberEntityEntity(), memberEntityEntityLambdaUpdateWrapper);
        memberEntityMapper.delete(memberEntityEntityLambdaUpdateWrapper);
//        mapper.update(
//                new LambdaUpdateWrapper<MemberEntityEntity>()
//                        .eq(MemberEntityEntity::getUserId, userId)
//                        .set(MemberEntityEntity::getIsDel, Byte.valueOf("1"))
//        );

    }
}
