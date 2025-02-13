package com.miller.userapp.module.activity.group.collect.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.activity.group.collect.request.CollectBatchRequestDTO;
import com.miller.userapp.module.activity.group.collect.respnose.CollectBatchResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 天降红包领取接口
 */
public class CollectBatchFlow {
//    领取接口路径
    static String uri= BusinessConstant.DOMAIN + "/api/user/redPacket/group/collect/batch";

    /**
     * @param collectBatchRequestDTO 领取接口入参
     * @return CollectBatchResponseDTO
     */
    public static CollectBatchResponseDTO collectBatchFlow(CollectBatchRequestDTO collectBatchRequestDTO){
    return HttpUtils.sendPostRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(), collectBatchRequestDTO,null,CollectBatchResponseDTO.class);
    }
}
