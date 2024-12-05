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
    public void batchProduct(String token, BatchProductDao productDao, int addCount,String taskId) {
        //  手动写入 Token
        Map<String, Object> headers = RequestUtils.getHeaders();
        headers.put("token",token);
        RequestUtils.setHeaders(headers);

        for (int i = 0; i < addCount; i++) {
            final String productName = this.insertProduct(token, productDao);
            try {
                // 停顿 1s
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executePoolConfig.setTaskPool(taskId,productName);
        }

    }

    String insertProduct(String token, BatchProductDao batchProductDao) {

        JSONObject insertProductReqBody = JSON.parseObject("{\"menuId\":4169154,\"productName\":\"商品名称\",\"productNameEn\":\"en_name\",\"productImg\":\"https://static-img.hungrypanda.co/product-img/172735260970010d7c9b67e784df8916e6929a82d7400.jpg?x-oss-process=style/prod_detail\",\"refer\":1,\"productPrice\":10,\"productSaleType\":1,\"referenceOnly\":true,\"proNum\":\"\",\"packingCharges\":0,\"skuSpecName\":\"\",\"skuSpecNameEn\":\"\",\"status\":0,\"skuAddMode\":0,\"attributeList\":[],\"tagList\":[],\"specs\":[],\"productSkuList\":[],\"details\":\"描述描述描述描述描述\",\"detailsEn\":\"en desc en desc en desc en desc en desc en desc\",\"taste\":\"\",\"quantity\":\"\",\"mainIngredient\":\"\",\"promotionLabel\":\"\",\"productLabel\":0,\"productBuyLimitMin\":1,\"isSpecial\":0,\"limit\":1,\"store\":\"\",\"taxRate\":0,\"isSingleSku\":1,\"productId\":0}");
        ProductSaveRequest productSaveRequest = JSON.toJavaObject(insertProductReqBody, ProductSaveRequest.class);
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


        ProductSaveResponse response = ProductSaveFlow.productSave(productSaveRequest);
        if (response.getCode() != 1) {
            return String.format("%s ：创建失败：%s", productName, response.getMessage());
        }
        return productName;

    }

}
