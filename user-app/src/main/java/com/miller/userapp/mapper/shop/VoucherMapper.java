package com.miller.userapp.mapper.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.hungrypanda.app.server.entity.voucher.VoucherInfoEntity;

/**
 * @author panjuxiang
 * @since 2024/7/31 13:40
 */
public interface VoucherMapper extends BaseMapper<VoucherInfoEntity> {
    /**
     * 通过VoucherSn查店铺信息
     * @param voucherSn
     * @return
     */
    ShopEntity getShopInfoByVoucherSn(String voucherSn);
}
