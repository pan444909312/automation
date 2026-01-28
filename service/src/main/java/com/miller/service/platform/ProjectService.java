package com.miller.service.platform;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.platform.Project;

import java.util.List;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
public interface ProjectService extends IService<Project> {

    Project findByName(String name);

    List<Project> getAll();
}
