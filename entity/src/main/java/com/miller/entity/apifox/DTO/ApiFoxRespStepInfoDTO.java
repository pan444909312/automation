package com.miller.entity.apifox.DTO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.miller.entity.apifox.ApiTestCaseCustomHttpRequestEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ApiFoxRespStepInfoDTO {

    public ApiFoxRespStepInfoDTO() {
    }

    public ApiFoxRespStepInfoDTO(String stepId, String stepName, Set<JSONObject> failureObj) {
        this.stepName = stepName;
        this.failureList = failureObj;
        this.stepId = stepId;
    }

    String stepName;

    String reqBody;

    String respBody;

    String url;

    String method;

    String headers;

    JSONArray postProcessors;

    JSONArray preProcessors;

    Set<JSONObject> failureList;

    String params;

    String stepId;

    private Boolean runStatus = true;

    public int getAssertCount() {
        if (ObjectUtils.isEmpty(this.postProcessors)) {
            return 0;
        }
        return (int) this.postProcessors.stream().filter(obj -> {
            final String type = ((JSONObject) obj).getString("type");
            if (!type.equals("assertion")) {
                return false;
            }
            return true;
        }).count();
    }

    ;


    public static ApiFoxRespStepInfoDTO setStepInfo(ApifoxRespMetaInfoDTO metaInfo, ApiTestCaseCustomHttpRequestEntity customHttpRequestEntity) {
        ApiFoxRespStepInfoDTO stepInfoDTO = new ApiFoxRespStepInfoDTO();
        if (ObjectUtils.isNotEmpty(metaInfo)) {
            stepInfoDTO.setStepId(metaInfo.getHttpApiId())
                    .setMethod(metaInfo.getHttpApiMethod())
                    .setStepName(metaInfo.getName())
                    .setUrl(metaInfo.getHttpApiPath());
        }

        if (ObjectUtils.isNotEmpty(customHttpRequestEntity)) {
            stepInfoDTO.setPostProcessors(customHttpRequestEntity.getPostProcessors())
                    .setPreProcessors(customHttpRequestEntity.getPreProcessors())
//                    .setParams(customHttpRequestEntity.getParameters())
//                    .setReqBody(customHttpRequestEntity.getRequestBody())
                    ;
        }

        return stepInfoDTO;
    }

}
