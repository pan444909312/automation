package com.miller.controller.report;

import com.miller.entity.report.req.PageAutoCaseRoiChartReqDTO;
import com.miller.entity.report.resp.AutoCaseRoiChartRespDTO;
import com.miller.entity.util.BasePageResponse;
import com.miller.entity.util.Response;
import com.miller.service.report.AutoCaseRoiChartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 测试场景总ROI表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@RestController
@RequestMapping("/automation/autoCaseRoiChart")
@Tag(name = "自动化用例执效益统计")
public class AutoCaseRoiChartController {

    @Autowired
    AutoCaseRoiChartService autoCaseRoiChartService;


    @Operation(description = "分页查询场景总ROI报表")
    @PostMapping("/list")
    public Response<BasePageResponse<AutoCaseRoiChartRespDTO>> listAutoCaseRoiChart(@Valid @RequestBody PageAutoCaseRoiChartReqDTO pageAutoCaseRoiChartReqDTO) {

        return autoCaseRoiChartService.getAutoCaseRoiChartList(pageAutoCaseRoiChartReqDTO);

    }

}
