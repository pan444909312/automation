package com.miller.common.util;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @author panjuxiang
 * @since 2024/11/28 19:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePageResponse<T> {

    @Schema(description = "总数")
    private long total;

    @Schema(description = "分页数据")
    private List<T> list = new ArrayList();

}
