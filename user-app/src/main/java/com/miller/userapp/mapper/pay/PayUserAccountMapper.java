package com.miller.userapp.mapper.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.payserver.entity.PayUserAccount;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface PayUserAccountMapper extends BaseMapper<PayUserAccount> {
}
