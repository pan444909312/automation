package com.miller.userapp.module.pay.card.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.pay.card.request.AddCardRecordRequestDTO;
import com.miller.userapp.module.pay.card.response.AddCardRecordResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class AddCardRecordFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/payment/v2/addCardRecord";

    /**
     * 添加卡
     * @param addCardRecordRequestDTO
     * @return
     */
    public static AddCardRecordResponseDTO addCardRecord(AddCardRecordRequestDTO addCardRecordRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(addCardRecordRequestDTO), null, AddCardRecordResponseDTO.class);
    }
}
