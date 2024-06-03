package com.miller.pos.product.addproduct;

import com.miller.pos.product.addproduct.request.AddProductRequestDTO;
import com.miller.service.framework.util.FileUtils;
import com.miller.service.framework.util.JSONUtils;
import org.junit.jupiter.api.Test;

public class TestJsonToObject {

    @Test
    void testGetJavaObjectFromJsonString() {
        String requestBody = FileUtils.readFileFromResources("/data/product/addproduct/addproduct.json");
        AddProductRequestDTO addProductRequestDTO = JSONUtils.jsonToObjectByJackson(requestBody, AddProductRequestDTO.class);
        System.out.println(addProductRequestDTO.getName());
        System.out.println(addProductRequestDTO.getSpecification());
        addProductRequestDTO.getImageList().forEach(System.out::println);


    }
}
