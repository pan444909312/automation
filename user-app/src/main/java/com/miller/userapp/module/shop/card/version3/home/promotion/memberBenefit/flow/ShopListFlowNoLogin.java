package com.miller.userapp.module.shop.card.version3.home.promotion.memberBenefit.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.shop.card.version3.home.promotion.memberBenefit.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.home.promotion.memberBenefit.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;

public class ShopListFlowNoLogin {
    /**
     * 接口_首页店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v2/index/shopList";

    /**
     * 流程_获取首页店铺流
     */
    public static ShopListResponseDTO getShopList(ShopListRequestDTO shopListRequestDTO) {
        // 这里需要测试未登录的情况，所以 RequestUtils.setHeaders(header)
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("uniquetoken", "yqqd88a89d4913c70bd");
        RequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }

    /**
     * 流程_根据shopId获取首页店铺流
     * @param shopListRequestDTO 请求参数
     * @param shopId 店铺ID
     * @return 店铺列表响应
     */
    public static ShopListResponseDTO getShopListByShopId(ShopListRequestDTO shopListRequestDTO, Long shopId) {
        int maxPage = 10; // 最大查找页数，避免无限循环
        for (int pageNo = 1; pageNo <= maxPage; pageNo++) {
            ShopListResponseDTO response = getShopListWithPage(shopListRequestDTO, pageNo);
            
            // 检查返回结果是否包含目标shopId
            boolean hasTargetShop = response.getResult().getShopList().stream()
                    .anyMatch(shop -> shop.getShopId().equals(shopId));
            
            if (hasTargetShop) {
                return response;
            }
        }
        throw new RuntimeException("未找到指定的店铺ID: " + shopId);
    }

    /**
     * 私有方法_带页码获取首页店铺流
     */
    private static ShopListResponseDTO getShopListWithPage(ShopListRequestDTO shopListRequestDTO, int pageNo) {
        var myheaders = new HashMap<String, Object>();
        myheaders.put("Content-Type", "application/json");
        myheaders.put("uniquetoken", "yqqd88a89d4913c70bd");
        myheaders.put("pageNo", pageNo);
        RequestUtils.setHeaders(myheaders);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }
}
