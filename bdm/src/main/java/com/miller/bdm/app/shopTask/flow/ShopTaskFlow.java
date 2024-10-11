package com.miller.bdm.app.shopTask.flow;

import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.app.shopTask.request.ShopTaskRequestDTO;
import com.miller.bdm.app.shopTask.respones.ShopTaskResponseDTO;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

public class ShopTaskFlow {

    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/mobile/shop/352297264/task/page";



    public static ShopTaskResponseDTO getShowTask(ShopTaskRequestDTO shopTaskRequestDTO) {

        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(shopTaskRequestDTO),
                null, ShopTaskResponseDTO.class);
    }

}
