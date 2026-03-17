package com.miller.controller.dashboard;

import com.alibaba.fastjson.JSONObject;
import com.miller.controller.tools.ResultVO;
import com.miller.service.dashboard.DashboardService;
import dto.DashboardReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;


    @GetMapping("/options")
    public ResultVO getOptions(){
       return ResultVO.success(dashboardService.getFilterOption());
    }

    /**
     * 获取仪表盘统计数据
     * @param dashboardReqDTO 查询条件
     * @return 仪表盘统计数据
     */
    @PostMapping("/statistics")
    public JSONObject getDashboardStatistics(@RequestBody DashboardReqDTO dashboardReqDTO){
        return dashboardService.getDashboardStatistics(dashboardReqDTO);
    }

}
