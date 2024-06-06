package com.miller.bdm.app.privateShop.sign.request;


import com.panda.erp.server.dal.dto.bdm.eversign.SignContractReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * bdm-发起签约
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@Data
public class SignRequestDTO extends SignContractReq {
    @ApiModelProperty(value = "合同流程类型", required = true)
    private Integer flowType;
    /**
     * erp-dal 最新打包时间：23-07-27
     * 更新未成功，erp-dal包未打 路径为https://mvn.hungrypanda.it/#browse/browse:hungrypanda-snapshot:com%2Fpanda
     * 账号：hungrypanda-public
     * 密码：KvnP4LAz0T4QmOKn
     */
}
