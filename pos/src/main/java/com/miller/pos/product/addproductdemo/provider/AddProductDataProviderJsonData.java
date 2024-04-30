package com.miller.pos.product.addproductdemo.provider;

import com.github.javafaker.Faker;
import com.miller.pos.product.addproductdemo.request.AddProductRequestDTO;
import com.miller.service.framework.util.FileUtils;
import com.miller.service.framework.util.JSONUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static java.util.Locale.CHINESE;

/**
 * 数据提供者_商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:44:33
 */
@SuppressWarnings("unused")
public class AddProductDataProviderJsonData {
    static Faker faker= new Faker(CHINESE);
    static Stream<Arguments> addproduct() {
        String requestBody = FileUtils.readFileFromResources("/data/product/addproduct/addproduct.json");
        AddProductRequestDTO addProductRequestDTO = JSONUtils.jsonToObjectByJackson(requestBody, AddProductRequestDTO.class);
        addProductRequestDTO.getName().setEnUs("dong tai huo qu xxxx");
        return Stream.of(Arguments.of(addProductRequestDTO));
    }
}
