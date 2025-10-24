package com.miller.entity.report.req;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiFoxFailStepInfoDTO {
    public ApiFoxFailStepInfoDTO( String stepId,String stepName, JSONObject failureObj) {
        this.stepName = stepName;
        this.failureObj = failureObj;
        this.stepId = stepId;
    }

    String stepName;

    JSONObject failureObj;

    String stepId;

}
