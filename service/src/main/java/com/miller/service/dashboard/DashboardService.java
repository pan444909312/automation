package com.miller.service.dashboard;

import com.alibaba.fastjson.JSONObject;
import com.miller.service.dto.DashboardReqDTO;
import com.miller.service.vo.DashboardFilterOptionVO;
import com.miller.service.vo.DashboardVO;

public interface DashboardService {


    DashboardFilterOptionVO getFilterOption();

    JSONObject getDashboardStatistics(DashboardReqDTO dashboardReqDTO);
}
