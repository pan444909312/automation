package com.miller.userapp.moduleEnAu.shop.card.version3.userPack.promotion.firstOrder.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.promotion.firstOrder.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class ShopListFlowControlGroup {
    /**
     * 接口_自取频道店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/userPack/list";

    /**
     * 流程_获取自取频道店铺流
     */
    public static ShopListResponseDTO getShopList(ShopListRequestDTO shopListRequestDTO) {
        // 这里将新人价实验组(默认)改为对照组,需要改两个实验
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        String newTestGroup=BusinessConstant.testGroup.replace("XRJ01","XRJ02").replace("SKXRB01","SKXRB02");
        RequestUtils.getHeaders().put("testGroup", newTestGroup);
//        RequestUtils.getHeaders().put("authorization", null);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }
    public static ShopListResponseDTO getShopListByShopId(ShopListRequestDTO shopListRequestDTO, Long shopId) {
        try {
            // 设置请求头
            RequestUtils.getHeaders().put("Content-Type", "application/json");
            String testGroupNew = BusinessConstant.testGroup.replace("SKYX02", "SKYX01");
            RequestUtils.getHeaders().put("testGroup", testGroupNew);
            int pageNo = 1;
            RequestUtils.getHeaders().put("pageNo", pageNo);
            // 发送请求获取数据
            ShopListResponseDTO shopListResponse = HttpUtils.sendPostRequestReturnJavaObject(
                    uri,
                    null,
                    RequestUtils.getHeaders(),
                    RequestUtils.putBodyOfJson(shopListRequestDTO),
                    null,
                    ShopListResponseDTO.class
            );

            // 检查响应是否有效
            if (shopListResponse == null || shopListResponse.getResult() == null ||
                    shopListResponse.getResult().getShopList() == null) {
                throw new RuntimeException("Invalid response from server");
            }

            // 循环查找目标店铺
            while (true) {
                // 检查当前页是否包含目标店铺
                boolean found = shopListResponse.getResult().getShopList().stream()
                        .anyMatch(item -> (item.getShopId()).equals(shopId));

                if (found) {
                    return shopListResponse;
                }

                // 检查是否还有下一页
                if (shopListResponse.getResult().getShopList().isEmpty()) {
                    break;
                }

                // 获取下一页
                pageNo++;
                RequestUtils.getHeaders().put("pageNo", pageNo);

                shopListResponse = HttpUtils.sendPostRequestReturnJavaObject(
                        uri,
                        null,
                        RequestUtils.getHeaders(),
                        RequestUtils.putBodyOfJson(shopListRequestDTO),
                        null,
                        ShopListResponseDTO.class
                );

                // 检查响应是否有效
                if (shopListResponse == null || shopListResponse.getResult() == null ||
                        shopListResponse.getResult().getShopList() == null) {
                    throw new RuntimeException("Invalid response from server");
                }
            }

            // 如果没找到店铺，返回null
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get shop list by shop ID: " + shopId, e);
        }
    }

}
