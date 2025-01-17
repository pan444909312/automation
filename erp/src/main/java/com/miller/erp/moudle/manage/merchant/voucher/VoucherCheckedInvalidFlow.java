package com.miller.erp.moudle.manage.merchant.voucher;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;

public class VoucherCheckedInvalidFlow {
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_BACKUP + "/admin/voucher/checked/invalid.htm";
    //根据代金券核销 老后台，所以要cookie
    public static void voucherCheckedInvalid(String entitySn){
        ERPLoginFlow.erpLoginByCookie();
        var params = new HashMap<String, Object>();
        params.put("entitySn", entitySn);
        String responseBody = HttpUtils.sendGetRequestReturnBody(uri, params, RequestUtils.getHeaders(), null);
        if (!responseBody.contains("1000")) {
            throw new RuntimeException("核销失败");
        }
    }
//    public static void main(String[] args){
//        ERPLoginFlow.loginByDefaultUser();
//        VoucherCheckedInvalidFlow.voucherCheckedInvalid("168606633611");
//    }
}
