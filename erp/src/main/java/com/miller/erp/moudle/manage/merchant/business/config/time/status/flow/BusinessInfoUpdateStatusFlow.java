package com.miller.erp.moudle.manage.merchant.business.config.time.status.flow;

import com.hungrypanda.app.server.common.enums.ShopStatusEnum;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.manage.merchant.business.config.time.status.request.BusinessInfoUpdateStatusRequestDTO;
import com.miller.erp.moudle.manage.merchant.business.config.time.status.request.BusinessInfoUpdateStopOrderRequestDTO;
import com.miller.erp.moudle.manage.merchant.business.config.time.status.response.BusinessInfoUpdateStatusResponseDTO;
import com.miller.erp.moudle.manage.merchant.business.config.time.status.response.BusinessInfoUpdateStopOrderResponseDTO;
import com.miller.service.framework.http.HttpUtils;
import com.miller.erp.util.RequestUtils;

import com.panda.merchant.server.api.vo.crm.merchant.req.ShopStopOrderReq;

/**
 * 流程_编辑商家-营业配置-营业时间
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/07/28 16:59:46
 */
public class BusinessInfoUpdateStatusFlow {
    /**
     * 接口_编辑商家-营业配置-营业时间-修改营业状态为营业、打烊
     */
    private static final String businessTimeUri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/business/updateStatus";

    public static BusinessInfoUpdateStatusResponseDTO businessInfoUpdateStatus(BusinessInfoUpdateStatusRequestDTO businessInfoUpdateStatusRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(businessTimeUri, null, RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(businessInfoUpdateStatusRequestDTO), null, BusinessInfoUpdateStatusResponseDTO.class);
    }

    /**
     * 暂停接单
     * TODO
     */
    private static final String stopOrderUri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/shop/order-stop/stop";

    /**
     * 接口_编辑商家-营业配置-营业时间-暂停接单接口
     * @param ShopStopOrderReq
     * @return
     */
    public static BusinessInfoUpdateStopOrderResponseDTO businessInfoUpdateStopOrder(BusinessInfoUpdateStopOrderRequestDTO ShopStopOrderReq) {
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(stopOrderUri, null, RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(ShopStopOrderReq), null, BusinessInfoUpdateStopOrderResponseDTO.class);
    }
    /**
     * 恢复营业
     */
    private static final String recoverOrderUri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/shop/order-stop/recover";


    /**
     * 营业或打烊
     *
     * @param shopId         商家ID
     * @param shopStatusEnum 营业状态 {@link  ShopStatusEnum}
     * @return BusinessInfoUpdateStatusResponseDTO
     */
    public static BusinessInfoUpdateStatusResponseDTO updateShopStatusToOpen(Long shopId, ShopStatusEnum shopStatusEnum) {
        BusinessInfoUpdateStatusRequestDTO businessInfoUpdateStatusRequestDTO = new BusinessInfoUpdateStatusRequestDTO();
        businessInfoUpdateStatusRequestDTO.setShopId(shopId);
        businessInfoUpdateStatusRequestDTO.setShopStatus(shopStatusEnum.getCode());
        businessInfoUpdateStatusRequestDTO.setCloseTimeLength(0);
        return businessInfoUpdateStatus(businessInfoUpdateStatusRequestDTO);
    }



    /**
     * 暂停接单
     * <p>
     * https://test-gateway.hungrypanda.cn/api/erp/shop/order-stop/stop
     * {"shopId":"387549545","stopOrderMinutes":"5","stopOrderToClose":false}
     * <p>
     */
    public static BusinessInfoUpdateStatusResponseDTO updateShopStatusToStop(Long shopId) {
        return null;
    }

    /**
     * 恢复营业
     * https://test-gateway.hungrypanda.cn/api/erp/shop/order-stop/recover
     * {"shopId":"59750820"}
     */
    public static BusinessInfoUpdateStatusResponseDTO updateShopStatusToRecover(Long shopId) {
        return null;
    }


    // 忙碌
    public static BusinessInfoUpdateStatusResponseDTO updateShopStatusToBusy(Long shopId) {
        return null;
    }

}