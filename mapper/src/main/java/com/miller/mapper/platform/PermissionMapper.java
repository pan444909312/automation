package com.miller.mapper.platform;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.miller.entity.platform.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 21:03
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 查询用户拥有的所有权限
     */
    Set<String> getAllPermissionCodeByUsername(String username);

    /**
     * 查询用户的父权限
     */
    List<Permission> getParentPermissionByUsername(String username);

    /**
     * 查询权限，根据父权限ID，查找出此父ID下的所有子节点数据
     */
    List<Permission> getChildrenPermissionByParentId(String parentId);
}
