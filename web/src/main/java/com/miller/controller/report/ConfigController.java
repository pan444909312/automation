package com.miller.controller.report;

import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.report.ConfigEntity;
import com.miller.entity.report.req.ConfigReqDTO;
import com.miller.entity.report.req.PageAutoCaseExecutionRecordReqDTO;
import com.miller.entity.util.Response;
import com.miller.service.report.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统配置表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2024-12-31
 */
@RestController
@RequestMapping("/automation/config")
@Tag(name = "系统配置")
@Slf4j
public class ConfigController {

    @Autowired
    ConfigService configService;

    @Operation(description = "添加系统配置")
    @PostMapping("/addConfig")
    public Response<String> addConfig(@RequestBody ConfigReqDTO configReqDTO) {
        ConfigEntity configEntity = new ConfigEntity();

        BeanUtils.copyProperties(configReqDTO,configEntity);
        try {

            boolean flag = configService.save(configEntity);
            if (flag)
                return Response.success("添加成功");
        }catch (Exception e){
            log.info("添加配置异常：" +e.getMessage());
        }
        return Response.fail("添加失败");
    }

    @Operation(description = "编辑系统配置")
    @PostMapping("/editConfig")
    public Response<String> editConfig(@RequestBody ConfigReqDTO configReqDTO) {
        return Response.success(null);
    }

    @Operation(description = "删除系统配置")
    @PostMapping("/removeConfig")
    public Response<String> removeConfig(@RequestBody ConfigReqDTO configReqDTO) {

        return Response.success(null);
    }

    @Operation(description = "获取系统配置列表")
    @PostMapping("/getConfigList")
    public Response<List<ConfigEntity>> getConfigList() {

        configService.list();

        return Response.success(configService.list());
    }
}
