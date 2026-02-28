package com.miller.mapper.report;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.dashboard.DashBoardEntity;
import dto.DashboardReqDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashboardMapper extends BaseMapper<DashBoardEntity> {

    List<DashBoardEntity> getAll(DashboardReqDTO reqDTO);

    List<DashBoardEntity> getExecutionStatusList(DashboardReqDTO reqDTO);


    int selectCreateCountByRangeTime(DashboardReqDTO reqDTO);

}
