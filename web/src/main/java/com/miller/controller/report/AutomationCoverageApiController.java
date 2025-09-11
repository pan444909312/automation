package com.miller.controller.report;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.service.listener.ExcelDataListener;
import com.miller.service.platform.AutomationCoverageApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report/automationCoverageApi")
@Api(tags = "自动化测试接口覆盖率管理")
@Slf4j
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


    @Operation(description = "上传活跃接口数据")
    @PostMapping("/import")
    public Map<String, Object> importData(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        List<String> errors = new ArrayList<>();
        int successCount = 0;
        int skipCount = 0;

        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                result.put("success", false);
                result.put("message", "文件不能为空");
                return result;
            }
            log.info("文件不为空");

            // 检查文件格式
            String fileName = file.getOriginalFilename();
            if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
                result.put("success", false);
                result.put("message", "仅支持Excel文件格式(.xlsx或.xls)");
                return result;
            }

            log.info("文件格式正确");

            // 创建临时文件用于EasyExcel读取
            File tempFile = File.createTempFile("excel_import_", "_" + fileName);
            file.transferTo(tempFile);

            // 使用EasyExcel读取数据
            List<ExcelDataListener.ExcelData> dataList = new ArrayList<>();
            ExcelDataListener listener = new ExcelDataListener(dataList);

            try {
                EasyExcel.read(tempFile, ExcelDataListener.ExcelData.class, listener)
                        .sheet()
                        .doRead();
            } catch (Exception e) {
                log.info(e.getMessage());
                result.put("success", false);
                result.put("message", "Excel文件解析失败: " + e.getMessage());
                return result;
            } finally {
                // 删除临时文件
                if (tempFile.exists()) {
                    tempFile.delete();
                }
            }

            // 解析数据并处理
            List<AutomationCoverageApiEntity> entitiesToSave = new ArrayList<>();

            for (int i = 0; i < dataList.size(); i++) {
                ExcelDataListener.ExcelData data = dataList.get(i);
                int rowIndex = i + 2; // 第一行是表头，所以从第2行开始

                try {
                    // 数据验证
                    if (data.getHost() == null || data.getHost().trim().isEmpty()) {
                        errors.add("第" + rowIndex + "行：域名不能为空");
                        continue;
                    }

                    if (data.getMethod() == null || data.getMethod().trim().isEmpty()) {
                        errors.add("第" + rowIndex + "行：请求方法不能为空");
                        continue;
                    }

                    if (data.getPath() == null || data.getPath().trim().isEmpty()) {
                        errors.add("第" + rowIndex + "行：接口URL不能为空");
                        continue;
                    }

                    // 检查path是否已存在
                    LambdaQueryWrapper<AutomationCoverageApiEntity> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(AutomationCoverageApiEntity::getPath, data.getPath().trim());
                    List<AutomationCoverageApiEntity> existingEntity = automationCoverageApiService.list(queryWrapper);

                    if (!existingEntity.isEmpty()) {
                        skipCount++;
                        continue; // 跳过已存在的记录
                    }

                    // 创建新实体
                    AutomationCoverageApiEntity entity = new AutomationCoverageApiEntity();
                    entity.setHost(data.getHost().trim());
                    entity.setMethod(data.getMethod().trim().toUpperCase());
                    entity.setPath(data.getPath().trim());
                    entity.setRequestsTimesProduction(data.getRequestsTimesProduction());
                    entity.setApiStatus(0); // 设置默认状态
                    entity.setIsAutomation(0); // 默认未自动化

                    entitiesToSave.add(entity);

                } catch (Exception e) {
                    errors.add("第" + rowIndex + "行数据解析失败: " + e.getMessage());
                }
            }

            // 批量保存数据
            if (!entitiesToSave.isEmpty()) {

                boolean saveSuccess = automationCoverageApiService.saveBatch(entitiesToSave);
                if (saveSuccess) {
                    successCount = entitiesToSave.size();
                } else {
                    errors.add("数据保存失败");
                }
            }

            // 构建返回结果
            result.put("success", true);
            result.put("message", "导入完成");
            result.put("successCount", successCount);
            result.put("skipCount", skipCount);
            result.put("errorCount", errors.size());
            if (!errors.isEmpty()) {
                result.put("errors", errors);
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "导入失败: " + e.getMessage());
            log.error("导入Excel数据失败", e);
        }

        return result;
    }


}