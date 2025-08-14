package com.miller.userapp.mapper.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panda.erp.server.dal.dataobject.product.ProductDiscountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author panjuxiang
 * @since 2024/7/25 16:12
 */
@Mapper
public interface ProductDiscountMapper extends BaseMapper<ProductDiscountEntity> {
}
