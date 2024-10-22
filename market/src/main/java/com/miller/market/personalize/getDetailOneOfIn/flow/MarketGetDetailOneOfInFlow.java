package com.miller.market.personalize.getDetailOneOfIn.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.personalize.getDetailOneOfIn.request.MarketGetDetailOneOfInRequestDTO;
import com.miller.market.personalize.getDetailOneOfIn.response.MarketGetDetailOneOfInResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 个性化详情接口
 *
 */
public class MarketGetDetailOneOfInFlow {
    /**
     * 个性化详情接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/personalize/getDetailOneOfIn";

    public static MarketGetDetailOneOfInResponseDTO getDetailOneOfIn(MarketGetDetailOneOfInRequestDTO requestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(requestDTO), null, MarketGetDetailOneOfInResponseDTO.class);
    }


}