package com.miller.bdm.app.privateShop.templates.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * bdm-移动端私海池-私海池商家列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@Data
public class GetTemplatesRequestDTO   {
    @ApiModelProperty("合同类目ID,1:入驻合同;2~*:其他默认合同")
    private Long categoryId;
}
