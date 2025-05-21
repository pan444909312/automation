package com.miller.service.framework.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.report.AutomationCoverageApiEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AutomationCoverageApiMapper extends BaseMapper<AutomationCoverageApiEntity> {
    /**
     * 根据API路径查询自动化覆盖数据
     *
     * @param testCasePath 根据测试用例的 Path 路径在库里查找对应的线上 Path, 如果匹配才做更新
     * @return 自动化覆盖数据列表
     */
    @Select("SELECT * FROM automation_coverage_api WHERE path = #{testCasePath}")
    List<AutomationCoverageApiEntity> selectByPath(@Param("testCasePath") String testCasePath);

    /**
     * 根据API路径更新自动化覆盖数据
     *
     * @param testCasePath 根据测试用例的 Path 路径在库里查找对应的线上 Path, 如果匹配才做更新
     * @param entity 要更新的实体对象
     * @return 更新的记录数
     */
    @Update("UPDATE automation_coverage_api SET " +
            "is_automation = #{entity.isAutomation}, " +
            "last_execute_time = #{entity.lastExecuteTime}, " +
            "executor = #{entity.executor}, " +
            "test_case_path = #{entity.testCasePath}, " +
            "test_case_request_last = #{entity.testCaseRequestLast}, " +
            "test_case_response_last = #{entity.testCaseResponseLast} " +
            "WHERE path = #{testCasePath}")
    int updateByPath(@Param("testCasePath") String testCasePath, @Param("entity") AutomationCoverageApiEntity entity);

}