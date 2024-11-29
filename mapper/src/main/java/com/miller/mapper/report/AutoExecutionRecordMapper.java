package com.miller.mapper.report;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.entity.report.req.PageAutoCaseExecutionRecordReqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 自动化用例执行记录表 Mapper 接口
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-10
 */

@Mapper
public interface AutoExecutionRecordMapper extends BaseMapper<AutoExecutionRecordEntity> {


    Page<AutoExecutionRecordEntity> selectPageByCondition(Page<?> page, @Param("req") PageAutoCaseExecutionRecordReqDTO req);


}
