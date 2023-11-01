package com.miller.demo.menu.flow;

import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.menu.response.UserGetMenuListResponseDTO;
import com.miller.service.framework.http.HttpUtils;

import java.util.Map;

/**
 * 获取菜单栏
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 20:33:32
 */
public class MenuInfoFlow {
    /**
     * 菜单栏接口
     */
    private static final String menuURI = SystemConfigConstant.DOMAIN + "/user/getMenuList/";

    public static UserGetMenuListResponseDTO getMenu(Map<String, Object> cookies, String username){
        UserGetMenuListResponseDTO responseBody = HttpUtils.sendGetRequestReturnJavaObject(
                menuURI + username , null, null, cookies, UserGetMenuListResponseDTO.class);
        return responseBody;
    }
}
