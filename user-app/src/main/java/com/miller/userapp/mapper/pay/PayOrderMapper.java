package com.miller.userapp.mapper.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.payserver.entity.PayOrder;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {
}
