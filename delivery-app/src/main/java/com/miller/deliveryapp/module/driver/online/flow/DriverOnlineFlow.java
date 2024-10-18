package com.miller.deliveryapp.module.driver.online.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.module.driver.online.request.DriverOnlineRequestDTO;
import com.miller.deliveryapp.module.driver.online.response.DriverOnlineResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 骑手上线流程
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 19:45:30
 */
public class DriverOnlineFlow {
    /**
     * 骑手上线、下线接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/driver/onOffline";

    /**
     * 骑手上线流程
     */
    public static DriverOnlineResponseDTO driverOnline(DriverOnlineRequestDTO driverOnlineRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(driverOnlineRequestDTO),
                null, DriverOnlineResponseDTO.class);
    }

}
