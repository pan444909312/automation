package com.miller.pos.product.deleteproduct.provider;


import com.github.javafaker.Faker;
import com.miller.pos.product.deleteproduct.request.DelProductRequestDTO;
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
public class DelProductDataProvider {
    static Faker faker= new Faker(CHINESE);
    static Stream<Arguments> delproduct() {
        DelProductRequestDTO delProductRequestDTO = new DelProductRequestDTO();



        return Stream.of(Arguments.of(delProductRequestDTO));
    }
}
