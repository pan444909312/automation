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

        JSONObject insertProductReqBody = JSON.parseObject("{\"shopId\":109925526,\"productPlatformCategoryId\":504,\"menuId\":4205981,\"productName\":\"飘香拌面\",\"productNameEn\":\"dan-Multiple specificatio2131232\",\"productImg\":\"https://static.hungrypanda.co/crm/175263595287474853f7b8a1e470f94e4cd8d9df94d10.jpg\",\"refer\":0,\"proNum\":\"\",\"shelfNum\":\"\",\"isCombine\":0,\"changeProductStatusByCombineProduct\":0,\"combineRelationDTOS\":[],\"taxRate\":0,\"packingCharges\":0,\"productBuyLimitMin\":1,\"limit\":1,\"status\":0,\"isSpecial\":0,\"productSaleType\":1,\"productSalesLocationList\":[0],\"spec\":{\"id\":\"\",\"productId\":\"\",\"groupName\":\"规格\",\"groupNameEn\":\"specification\",\"valueList\":[{\"id\":null,\"skuGroupId\":null,\"skuName\":\"标准\",\"skuNameEn\":\"standard\",\"price\":1,\"appPrice\":2,\"offlineDineInPrice\":1,\"sortNum\":1,\"status\":1,\"statusChanged\":true,\"otherPlatformPriceList\":[],\"platformPrice\":1}]},\"attributeList\":[],\"tagList\":[],\"isPersonalRecommend\":0,\"productLabel\":0,\"taste\":\"\",\"quantity\":\"\",\"mainIngredient\":\"\",\"promotionLabel\":\"\",\"showProductLabelTypeList\":[0,1,2,3,4],\"sort\":1}\n");
        ProductEditDTOV2 productSaveRequest = JSON.toJavaObject(insertProductReqBody, ProductEditDTOV2.class);
        final Long menuId = batchProductDao.getMenuId();
        String productName = batchProductDao.getProductName();
        if (ObjectUtils.isEmpty(menuId) || menuId ==0) {
            log.error("Param error：menu_id 不能为空");
            return String.format("%s ：创建失败：%s", productName, "Param error：menu_id 不能为空");
        }
        productSaveRequest.setMenuId(menuId);

        if (!(ObjectUtils.isEmpty(productName) || "".equals(productName))) {
            productName = productName.concat(String.valueOf(System.currentTimeMillis()));
            productSaveRequest.setProductName(productName);
            productSaveRequest.setProductNameEn(productName);
        } else {
            // 商品名称为空，初始化商品名称逻辑
            long currentTime = System.currentTimeMillis();
            int rInt = new Random().nextInt(1000);
            StringBuffer productNameBuff = new StringBuffer();
            productName = productNameBuff.append("AutoCreateProduct:").append(currentTime).append(rInt).toString();
            productSaveRequest.setProductName(productName);
            productSaveRequest.setProductNameEn(productName);
        }


        Result result = ProductSaveFlow.productSave(productSaveRequest);
        if (result.getCode() != 1) {
            return String.format("%s ：创建失败：%s", productName, result.getMessage());
        }
        return productName;

    }

}
