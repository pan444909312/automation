package com.miller.controller;

import com.miller.common.util.Response;
import com.miller.entity.AutoCaseRoi;
import com.miller.service.AutoCaseRoiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 自动化用例ROI表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@RestController
@RequestMapping("/autoCaseRoi")
@Api(description = "自动化模块")
public class AutoCaseRoiController {

    @Autowired
    AutoCaseRoiService autoCaseRoiService;
    @ApiOperation(value = "测试")
    @PostMapping("/listAutoCase")
    public Response<List<AutoCaseRoi>> listAutoCase() {
        List<AutoCaseRoi> list = autoCaseRoiService.list();
        return Response.success(list);
    }
}
