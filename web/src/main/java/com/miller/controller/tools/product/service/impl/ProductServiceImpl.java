package com.miller.controller.tools.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miller.config.ExecutePoolConfig;
import com.miller.controller.tools.product.dao.BatchProductDao;
import com.miller.controller.tools.product.service.ProductService;
import com.miller.erp.moudle.pc.product.flow.ProductSaveFlow;
import com.miller.erp.moudle.pc.product.request.ProductSaveRequest;
import com.miller.erp.moudle.pc.product.response.ProductSaveResponse;
import com.miller.erp.util.RequestUtils;
import com.panda.common.base.Result;
import com.panda.product.server.api.dto.product.req.ProductEditDTOV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Random;


@Service
@Slf4j
public class ProductServiceImpl  implements ProductService {

    @Autowired
    private ExecutePoolConfig executePoolConfig;



    @Async("taskExecutor")
    public void batchProduct(BatchProductDao productDao, int addCount,String taskId) {

        for (int i = 0; i < addCount; i++) {
            final String productName = this.insertProduct(productDao);
            try {
                // 停顿 1s
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executePoolConfig.setTaskPool(taskId,productName);
        }

    }

    String insertProduct( BatchProductDao batchProductDao) {

        JSONObject productSaveRequest = JSON.parseObject("{\"shopId\":868523294,\"productPlatformCategoryId\":83497,\"menuId\":4207168,\"productName\":\"test22\",\"productNameEn\":\"testss\",\"productImg\":\"https://static.hungrypanda.co/crm/1761296068442c93e6ac8bef04739bd7936e72a6a4af8.jpg\",\"refer\":0,\"proNum\":\"\",\"shelfNum\":\"\",\"isCombine\":0,\"changeProductStatusByCombineProduct\":0,\"combineRelationDTOS\":[],\"taxRate\":0,\"packingCharges\":0,\"productBuyLimitMin\":1,\"limit\":1,\"status\":0,\"isSpecial\":0,\"productSaleType\":1,\"productSalesLocationList\":[0],\"pickUpType\":[1,2],\"spec\":{\"id\":\"\",\"productId\":\"\",\"groupName\":\"规格\",\"groupNameEn\":\"specification\",\"valueList\":[{\"id\":null,\"skuGroupId\":null,\"skuName\":\"标准\",\"skuNameEn\":\"standard\",\"price\":1,\"platformPrice\":null,\"appPrice\":1,\"offlineDineInPrice\":1,\"sortNum\":1,\"status\":1,\"statusChanged\":true,\"otherPlatformPriceList\":[]}]},\"attributeList\":[],\"tagList\":[],\"isPersonalRecommend\":0,\"productLabel\":0,\"taste\":\"\",\"quantity\":\"\",\"mainIngredient\":\"\",\"promotionLabel\":\"\",\"showProductLabelTypeList\":[0,1,2,3,4],\"sort\":1}\n");
//        ProductEditDTOV2 productSaveRequest = JSON.toJavaObject(insertProductReqBody, ProductEditDTOV2.class);
        final Long menuId = batchProductDao.getMenuId();
        String productName = batchProductDao.getProductName();
        if (ObjectUtils.isEmpty(menuId) || menuId ==0) {
            log.error("Param error：menu_id 不能为空");
            return String.format("%s ：创建失败：%s", productName, "Param error：menu_id 不能为空");
        }
        productSaveRequest.put("menuId", menuId);
//        productSaveRequest.setMenuId(menuId);

        if (!(ObjectUtils.isEmpty(productName) || "".equals(productName))) {

            final String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            productName = productName.concat(currentTimeMillis);
            productSaveRequest.put("productName", productName);
            productSaveRequest.put("productNameEn", "productNameEn:".concat(currentTimeMillis));

//            productSaveRequest.setProductName(productName);
//            productSaveRequest.setProductNameEn(productName);
        } else {
            // 商品名称为空，初始化商品名称逻辑
            long currentTime = System.currentTimeMillis();
            int rInt = new Random().nextInt(1000);
            StringBuffer productNameBuff = new StringBuffer();
            productName = productNameBuff.append("AutoCreateProduct:").append(currentTime).append(rInt).toString();
            productSaveRequest.put("productName", productName);
            productSaveRequest.put("productNameEn", productName);
//            productSaveRequest.setProductName(productName);
//            productSaveRequest.setProductNameEn(productName);
        }


        Result result = ProductSaveFlow.productSave(productSaveRequest);
        if (result.getCode() != 1) {
            return String.format("%s ：创建失败：%s", productName, result.getMessage());
        }
        return productName;

    }

}
