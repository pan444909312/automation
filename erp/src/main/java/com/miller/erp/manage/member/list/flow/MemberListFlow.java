package com.miller.erp.manage.member.list.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * 流程_会员管理-会员列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/23 17:07:51
 */
public class MemberListFlow {

    /**
     * 接口_会员管理-会员列表
     * 这个接口返回的是HTML。。。。。
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_BACKUP + "/admin/user/member/memberList.htm";

    /**
     * 通过会员名称查询会员主键ID。
     * <p>
     * 需要优化：目前此接口返回的是html，等后段改成返回json之后在优化
     *
     * @param userName 手机号
     * @return 会员主键ID
     */
    public static String getIDByMemberName(String userName) {
        var params = new HashMap<String, Object>();
        params.put("userName", userName);
        var headers = new HashMap<String, Object>();
        // 必传字段，使用erp的登录token进行校验
        String cookie = "CN_isNewFramework=1;CN_token=" + RequestUtils.getHeaders().get(BusinessConstantOfERP.TOKEN);
        headers.put("Cookie", cookie);
        headers.put("Content-Type", RequestUtils.getHeaders().get("Content-Type"));
        RequestUtils.setHeaders(headers);

        String responseBody = HttpUtils.sendGetRequestReturnBody(uri, params, RequestUtils.getHeaders(), null);
        Document document = Jsoup.parse(responseBody);
        // 从字符串：到期时间 操作 18924 18711110002 东东测试用户0002 中解析出主键ID
        return document.text().split("18711110002")[0].split("操作")[1].trim();
    }

    @Disabled("仅针对当前类中的方法进行测试或调试使用")
    @Test
    void test() {
        ERPLoginFlow.loginByDefaultUser();
        String bodyOfHtml = getIDByMemberName("18711110002");
        Document document = Jsoup.parse(bodyOfHtml);
        System.out.println(document.text().split("18711110002")[0].split("操作")[1].trim());

    }

}
