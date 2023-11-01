package com.miller.demo.login.flow;

import com.miller.demo.constants.SystemConfigConstant;
import com.miller.service.framework.http.HttpUtils;
import org.hamcrest.Matchers;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 退出登陆
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 19:02:54
 */
public class LogoutFlow {
    /**
     * 登出接口
     */
    private static final String logoutURI = SystemConfigConstant.DOMAIN + "/user/logout";


    public static String shouldLogoutSuccessful() {
        // 这个接口返回的不是 JSON 数据格
        return HttpUtils.sendGetRequestReturnBody(logoutURI, null, null, null);
    }
}
