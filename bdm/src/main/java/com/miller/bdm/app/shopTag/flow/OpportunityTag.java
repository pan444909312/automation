package com.miller.bdm.app.shopTag.flow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiao jinchuan
 * @date 2023/11/20
 */
@Data
public class OpportunityTag {

    /**
     * 商机标签ID
     */
    @ApiModelProperty("商机标签ID")
    private Long tagId;

    /**
     * 商机标签名称
     */
    @ApiModelProperty("商机标签名称")
    private String tagName;

    @ApiModelProperty("状态 0:停用 1:启用")
    private Integer status;

}
