package com.miller.erp.manage.member.delete;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

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
     * 需要优化：目前此接口返回的是html，等后段改成返回json之后在优化
     *
     * @param memberId 会员主键ID
     */
    public static void deleteMemberById(String memberId) {
        try {
            Long.valueOf(memberId);
        } catch (java.lang.NumberFormatException numberFormatException) {
            throw new IllegalArgumentException("memberId必须为数字");
        }
        var params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        var headers = new HashMap<String, Object>();
        // 先从请求头中把token取出来，放到新的 headers 中，否则每次 setHeaders 方法调用token都会初始化为空
        headers.put(BusinessConstantOfERP.TOKEN, RequestUtils.getHeaders().get(BusinessConstantOfERP.TOKEN));
        headers.put("Content-Type", RequestUtils.getHeaders().get("Content-Type"));
        // 必传字段，使用erp的登录token进行校验
        String cookie = "CN_isNewFramework=1;CN_token=" + RequestUtils.getHeaders().get(BusinessConstantOfERP.TOKEN);
        headers.put("Cookie", cookie);
        RequestUtils.setHeaders(headers);

        // 响应结果为：{\"code\":4108,\"name\":\"删除会员成功\"}
        String responseBody = HttpUtils.sendGetRequestReturnBody(uri, params, RequestUtils.getHeaders(), null);
        // 这个接口不管ID是否存在都会返回成功。。。。
        if (!responseBody.contains("成功")) {
            throw new RuntimeException("删除会员失败");
        }
    }
}
