package com.miller.userapp.mapper.search;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;

/**
 * 搜索结果中间表
 *
 * <p>
 * 查询数据库表与Java实体对象字段的映射关系，这样就可以自动将数据库的表字段映射到Java对象中了。
 * 查找开发代码中的对应Java实体对象方法: 在代码工程中中搜索 @TableName("表名")，例如: @TableName("hp_shop_search_middle")。
 * 包命名可以参考开发代码工程，开发代码工程包路径一般在 com.xxx.entity.xxx
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/6/24 20:00:26
 */
public interface ShopSearchMiddleMapper extends BaseMapper<ShopSearchMiddleEntity> {
}
