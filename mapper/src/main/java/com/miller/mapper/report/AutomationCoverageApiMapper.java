package com.miller.mapper.report;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.report.AutomationCoverageApiEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AutomationCoverageApiMapper extends BaseMapper<AutomationCoverageApiEntity> {

    List<AutomationCoverageApiEntity> findByTestCaseRequestPath(@Param("path") String path);

    boolean batchUpdateCoverageApi(@Param("ids") List<String> ids, @Param("entity") AutomationCoverageApiEntity entity);
} 