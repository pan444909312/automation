package com.miller.merchant.admin.product.list.flow;

import com.miller.merchant.admin.config.AdminDefaultConfig;
import com.miller.merchant.admin.product.list.request.ProductListDTO;
import com.miller.merchant.login.request.MerchantLoginRequestDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class ProductListFlow {

    private static final String uri = AdminDefaultConfig.DOMAIN.concat("/admin/merchant/merchantProduct.htm");


    private static Map<String, Object> list(ProductListDTO reqDto) {


//      TODO 还没完成  return RequestUtils.sendGetRequest(uri, params);

        return new HashMap<>();
    }


}
