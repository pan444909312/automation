package com.miller.bdm.app.queryContractList.flow;

import com.miller.bdm.app.queryContractList.request.QueryContractListRequestDTO;
import com.miller.bdm.app.queryContractList.respones.QueryContractListResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

public class QueryContractListFlow {

    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/eversign/queryContractList";



    public static QueryContractListResponseDTO getShowTask(QueryContractListRequestDTO queryContractListRequestDTO) {

        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(queryContractListRequestDTO),
                null, QueryContractListResponseDTO.class);
    }

}
