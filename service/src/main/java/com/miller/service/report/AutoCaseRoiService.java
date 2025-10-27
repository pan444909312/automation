package com.miller.service.report;

import com.miller.entity.report.AutoCaseRoiEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.report.req.ApifoxAutoCaseRoiDto;
import com.miller.entity.report.req.AutoCaseRoiReqDTO;
import com.miller.entity.report.req.UiAutoCaseRoiReqDTO;

import java.util.List;

/**
 * <p>
 * 自动化用例ROI表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
public interface AutoCaseRoiService extends IService<AutoCaseRoiEntity> {

    /**
     * 根据场景ID获取自动化用例名称
     * @param scenarioId
     * @return
     */
    String getAutoCaseNameByScenarioId(String scenarioId);


    /**
     * 批量更新projectId
     * @return
     */
    List<AutoCaseRoiEntity> selectAutoCaseRoiProjectId();

    /**
     * 保存或更新UI自动化用例数据
     * @param autoCaseRoiReqDTO
     * @return
     */
    boolean uiAutoCaseSaveOrUpdate(UiAutoCaseRoiReqDTO autoCaseRoiReqDTO);


    /**
     *
     * @return 获取所有测试场景的总节省成本的总和
     */
    long getAllScenarioSaveTime();
}
