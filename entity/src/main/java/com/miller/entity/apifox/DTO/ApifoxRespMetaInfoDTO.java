package com.miller.entity.apifox.DTO;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Apifox cli 报告中的 step 信息 ： metainfo  字段
 */
@Data
@Accessors(chain = true)
public class ApifoxRespMetaInfoDTO {

    private String httpApiPath;
    private String httpApiId;
    private String httpApiName;
    private String httpApiMethod;
    private String type;
    private String relatedId;
    private String blockId;
    private String httpApiCaseId;
    private String environmentId;
    private String blockNumber;
    private String httpApiCaseName;
    private String name;
    private String id;
    private String projectId;

}
