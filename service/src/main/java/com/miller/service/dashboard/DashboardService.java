package com.miller.service.dashboard;

import com.alibaba.fastjson.JSONObject;
import dto.DashboardReqDTO;
import com.miller.service.vo.DashboardFilterOptionVO;

public interface DashboardService {


    DashboardFilterOptionVO getFilterOption();

    JSONObject getDashboardStatistics(DashboardReqDTO dashboardReqDTO);
}
