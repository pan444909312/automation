package com.miller.mapper.report;

import com.miller.entity.report.AutoCaseRoiEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 自动化用例ROI表 Mapper 接口
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@Mapper
public interface AutoCaseRoiMapper extends BaseMapper<AutoCaseRoiEntity> {

    AutoCaseRoi findByScenarioId(String scenarioId);

}
