package com.miller.userapp.mapper.product;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.vo.shop.product.Product;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
