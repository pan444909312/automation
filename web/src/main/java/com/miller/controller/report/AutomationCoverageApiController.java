package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.service.platform.AutomationCoverageApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report/automationCoverageApi")
@Api(tags = "自动化测试接口覆盖率管理")
public class AutomationCoverageApiController {

    @Autowired
    private AutomationCoverageApiService automationCoverageApiService;

    @ApiOperation("创建自动化测试接口覆盖率")
    @PostMapping
    public Boolean create(@RequestBody AutomationCoverageApiEntity automationCoverageApiEntity) {
        return automationCoverageApiService.save(automationCoverageApiEntity);
    }

    @ApiOperation("更新自动化测试接口覆盖率")
    @PutMapping("/{id}")
    public Boolean update(@PathVariable Long id, @RequestBody AutomationCoverageApiEntity automationCoverageApiEntity) {
        automationCoverageApiEntity.setId(id);
        return automationCoverageApiService.updateById(automationCoverageApiEntity);
    }


    @ApiOperation("获取自动化测试接口覆盖率详情")
    @GetMapping("/{id}")
    public AutomationCoverageApiEntity getItem(@PathVariable Long id) {
        return automationCoverageApiService.getById(id);
    }

    @ApiOperation("分页查询自动化测试接口覆盖率")
    @GetMapping("/list")
    public Page<AutomationCoverageApiEntity> list(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<AutomationCoverageApiEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AutomationCoverageApiEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AutomationCoverageApiEntity::getApiStatus, 0);
        return automationCoverageApiService.page(page, queryWrapper);
    }

}