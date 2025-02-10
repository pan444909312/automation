package com.miller.userapp.module.activity.group.popup.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.activity.group.popup.request.UserPopupRequestDTO;
import com.miller.userapp.module.activity.group.popup.responose.UserPopupResponseDTO;
import com.miller.userapp.util.RequestUtils;


/**
 * 首页弹窗接口-用来测试天降弹窗
 */
public class UserPopupFlow {
    static String uri= BusinessConstant.DOMAIN + "/api/user/popup";

    /**
     * @param userPopupRequestDTO 首页弹窗入参
     * @return 天降弹窗结果
     */
    public static UserPopupResponseDTO getGroupPopupResult(UserPopupRequestDTO userPopupRequestDTO){
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("_sign","3689e6e39bc3c5c01520ba9c44fad9ab");
        // 传cityName服务端不支持
        RequestUtils.getHeaders().remove("cityName");
        return HttpUtils.sendGetRequestReturnJavaObject(uri,RequestUtils.putBodyOfForm(userPopupRequestDTO),RequestUtils.getHeaders(),null, UserPopupResponseDTO.class);

    }

}
