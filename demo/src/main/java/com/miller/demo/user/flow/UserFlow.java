package com.miller.demo.user.flow;

import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.user.response.UserGetUserListResponseDTO;
import com.miller.service.framework.http.HttpUtils;

import java.util.Map;

/**
 * 用户管理流程
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 15:55:00
 */
public class UserFlow {
    private static final String userURI = SystemConfigConstant.DOMAIN + "/user/getUserList";

    public static UserGetUserListResponseDTO getUserList(Map<String, Object> token) {
        /*
        使用Flow的还有一个好处就是隔离了用例层，现在是使用cookies作为认证，后续如果改成header只需改这个一个地方
         */
        return HttpUtils.sendGetRequestReturnJavaObject(userURI, null, null, token, UserGetUserListResponseDTO.class);
    }

}
