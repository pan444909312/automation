package com.miller.userapp.moduleEnAu.shop.card.version3.home.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_首页店铺流
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/6/24 17:13:46
 */
public class ShopListFlow {
    /**
     * 接口_首页店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v2/index/shopList";

    /**
     * 流程_获取首页店铺流
     */
    public static ShopListResponseDTO getShopList(ShopListRequestDTO shopListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        String testGroupNew = BusinessConstant.testGroup.replace("SKYX02", "SKYX01");
        RequestUtils.getHeaders().put("testGroup", testGroupNew);
        // au英文版 请求头 i18n-language=EN && language=CN
        RequestUtils.getHeaders().put("i18n-language", "EN");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }

    /**
     * 根据店铺ID获取店铺列表
     *
     * @param shopListRequestDTO 请求参数
     * @param shopId             目标店铺ID
     * @return 包含目标店铺的响应对象
     */
    public static ShopListResponseDTO getShopListByShopId(ShopListRequestDTO shopListRequestDTO, Long shopId) {
        try {
            // 设置请求头
            RequestUtils.getHeaders().put("Content-Type", "application/json");
            String testGroupNew = BusinessConstant.testGroup.replace("SKYX02", "SKYX01");
            RequestUtils.getHeaders().put("testGroup", testGroupNew);
            // au英文版 请求头 i18n-language=EN && language=CN
            RequestUtils.getHeaders().put("i18n-language", "EN");
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