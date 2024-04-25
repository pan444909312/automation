package com.miller.deliveryapp.drivercenter.info.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.drivercenter.info.request.DriverInfoRequestDTO;
import com.miller.deliveryapp.drivercenter.info.response.DriverInfoResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 骑手个人信息获取
 *
 * @author penglulu
 * @version 1.0
 * @since 2024/04/25 10:59:46
 */
public class DriverInfoFlow {
    /**
     * 个人信息接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/driver/driverInfo";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param driverInfoRequestDTO {@link DriverInfoRequestDTO}
     * @return 响应结构
     */

    public static DriverInfoResponseDTO driverInfoFlow(DriverInfoRequestDTO driverInfoRequestDTO) {

        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(driverInfoRequestDTO),
                null, DriverInfoResponseDTO.class);
    }


}