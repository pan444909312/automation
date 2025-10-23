package com.miller.controller.tools;

import com.miller.entity.tools.ToolEfficiencyStatsEntity;
import com.miller.service.tools.excel.ToolEfficiencyStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/automation/tools/toolEfficiencyStats/")
public class ToolEfficiencyController {

    @Autowired
    private ToolEfficiencyStatsService toolEfficiencyStatsService;

    @PostMapping("/saveOrUpdate")
    public ResultVO toolEfficiencyStats(@RequestBody ToolEfficiencyStatsEntity entity, @RequestHeader("executor") String executor) {
        toolEfficiencyStatsService.toolEfficiencyStatsSavaOrUpdate(entity,executor);
        return ResultVO.success();
    }

    @GetMapping("/all")
    public ResultVO getAllToolEfficiencyStats() {
        List<ToolEfficiencyStatsEntity> allToolEfficiencyStats = toolEfficiencyStatsService.getAllToolEfficiencyStats();
        return ResultVO.success(allToolEfficiencyStats);
    }

}
