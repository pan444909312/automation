package com.miller.userapp.mapper.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.shop.ShopExtraInfoEntity;

/**
 * 商家扩展信息
 * <p>
 * 查询数据库表与Java实体对象字段的映射关系，这样就可以自动将数据库的表字段映射到Java对象中了。
 * 查找开发代码中的对应Java实体对象方法: 在代码工程中中搜索 @TableName("表名")，例如: @TableName("shop_extra_info")。
 * 包命名可以参考开发代码工程，开发代码工程包路径一般在 com.xxx.entity.xxx
 * </p>
 */
public interface ShopExtraInfoMapper extends BaseMapper<ShopExtraInfoEntity> {
}
