package com.miller.service.platform;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.report.AutomationCoverageApiEntity;

import java.util.List;
import java.util.Set;

public interface AutomationCoverageApiService extends IService<AutomationCoverageApiEntity> {

    Boolean updateCoverageApi(AutomationCoverageApiEntity entity);

    // 手动执行方法，因为apifox是本地服务，没法远程调用
    Set<String> initApifoxCoverageApi() throws Exception;

} 