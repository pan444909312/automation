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
     * @param testCaseRequestPath 根据测试用例的 Path 路径在库里查找对应的线上 Path, 如果匹配才做更新
     * @return 自动化覆盖数据列表
     */
    @Select("SELECT * FROM automation_coverage_api WHERE path = #{testCaseRequestPath}")
    List<AutomationCoverageApiEntity> selectByPath(@Param("testCaseRequestPath") String testCaseRequestPath);

    /**
     * 根据API路径更新自动化覆盖数据
     *
     * @param testCaseRequestPath 根据测试用例的 Path 路径在库里查找对应的线上 Path, 如果匹配才做更新
     * @param entity 要更新的实体对象
     * @return 更新的记录数
     */
    @Update("UPDATE automation_coverage_api SET " +
            "is_automation = #{entity.isAutomation}, " +
            "last_execute_time = #{entity.lastExecuteTime}, " +
            "executor = #{entity.executor}, " +
            "test_case_response_status_code = #{entity.testCaseResponseStatusCode}, " +
            "test_case_response_body = #{entity.testCaseResponseBody}, " +
            "test_case_request_path = #{entity.testCaseRequestPath}, " +
            "test_case_request_method = #{entity.testCaseRequestMethod}, " +
            "test_case_request_uri = #{entity.testCaseRequestUri}, " +
            "test_case_request_headers = #{entity.testCaseRequestHeaders}, " +
            "test_case_request_body = #{entity.testCaseRequestBody} " +
            "WHERE path = #{testCaseRequestPath}")
    int updateByPath(@Param("testCaseRequestPath") String testCaseRequestPath, @Param("entity") AutomationCoverageApiEntity entity);

}