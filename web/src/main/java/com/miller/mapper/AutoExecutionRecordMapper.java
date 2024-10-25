package com.miller.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.AutoExecutionRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.AutoExecutionRecordEntity;
import com.miller.entity.dto.PageAutoCaseExecutionRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 自动化用例执行记录表 Mapper 接口
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-10
 */

public interface AutoExecutionRecordMapper extends BaseMapper<AutoExecutionRecord> {


    Page<AutoExecutionRecordEntity> selectPageByCondition(Page<?> page,@Param("req") PageAutoCaseExecutionRecordDTO req);


}
