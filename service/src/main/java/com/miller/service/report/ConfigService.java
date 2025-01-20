package com.miller.service.report;

import com.miller.entity.report.ConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统配置表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-12-31
 */
public interface ConfigService extends IService<ConfigEntity> {

    String getConfigByKey(String key);
}
