package com.miller.pos.product.queryproduct.provider;


import com.github.javafaker.Faker;
import com.miller.pos.product.queryproduct.request.QueryProductRequestDTO;
import com.panda.pos.server.api.dto.open.NameMultiLanguageDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.miller.pos.constants.BusinessConstant.*;
import static java.util.Locale.CHINESE;

/**
 * 数据提供者_商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:44:33
 */
@SuppressWarnings("unused")
public class QueryProductDataProvider {
    static Faker faker= new Faker(CHINESE);
    static Stream<Arguments> queryproduct() {
        QueryProductRequestDTO queryProductRequestDTO = new QueryProductRequestDTO();



        return Stream.of(Arguments.of(queryProductRequestDTO));
    }
}
