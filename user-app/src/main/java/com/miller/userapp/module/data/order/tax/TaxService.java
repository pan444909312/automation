package com.miller.userapp.module.data.order.tax;

import com.hungrypanda.app.server.dto.order.TaxPriceDTO;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;

public interface TaxService {
    /**
     * 计算消费税
     *
     */
    TaxPriceDTO computeTaxPrice(CalculateOrderBasicData ov, OrderCountInfoEx countInfo);

}
