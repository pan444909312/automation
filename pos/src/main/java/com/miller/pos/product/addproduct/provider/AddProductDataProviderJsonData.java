package com.miller.pos.product.addproduct.provider;

import com.github.javafaker.Faker;
import com.miller.pos.product.addproduct.request.AddProductRequestDTO;
import com.miller.service.framework.util.JSONUtils;
import com.panda.pos.server.api.dto.open.NameMultiLanguageDTO;
import com.panda.pos.server.api.dto.open.NameMultiLanguageLength64DTO;
import com.panda.pos.server.api.dto.open.product.ProductAttrGroupDTO;
import com.panda.pos.server.api.dto.open.product.ProductImageDTO;
import com.panda.pos.server.api.dto.open.product.ProductSpecDTO;
import com.panda.pos.server.api.dto.open.product.ProductTagGroupDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.miller.pos.constants.BusinessConstant.pos_item_id;
import static com.miller.pos.constants.BusinessConstant.product_name;
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

        String requestBody = "{\n" +
                "        \"pos_item_id\": 111,\n" +
                "        \"code\": \"100001\",\n" +
                "        \"desc\": {\n" +
                "            \"en_us\": \"add_product_test\",\n" +
                "            \"zh_cn\": \"新增商品测试\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "            \"en_us\": \"111\",\n" +
                "            \"zh_cn\": \"name_cn\"\n" +
                "        },\n" +
                "        \"other\": {\n" +
                "            \"is_zp\": 0 \n" +
                "        },\n" +
                "        \"img_list\": [\n" +
                "            {\n" +
                "                \"type\": \"image\",\n" +
                "                \"url\": \"https://298383278759-fuji-test-us-west-2.s3.us-west-2.amazonaws.com/fuji-product-webapp-test/202312/b72765aa-8fbb-45ba-820e-c631808bdcc5.jpg\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"excise_tax_rate\": 6.998,\n" +
                "  \n" +
                "        \"seq\": 99,\n" +
                "        \"sku_data\": {\n" +
                "            \"name\": {\n" +
                "                \"en_us\": \"guigezu\",\n" +
                "                \"zh_cn\": \"规格组名称\"\n" +
                "            },\n" +
                "            \"sku_list\": [\n" +
                "                {\n" +
                "                    \"name\": {\n" +
                "                        \"en_us\": \"Standard\",\n" +
                "                        \"zh_cn\": \"标准\"\n" +
                "                    },\n" +
                "                    \"price\": 280,\n" +
                "                    \"stock\": -1,\n" +
                "                    \"stock_everyday\": -1,\n" +
                "                    \"code\": \"sku001\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": {\n" +
                "                        \"en_us\": \"Standard1\",\n" +
                "                        \"zh_cn\": \"标准1\"\n" +
                "                    },\n" +
                "                    \"price\": 290,\n" +
                "                    \"stock\": -1,\n" +
                "                    \"stock_everyday\": -1,\n" +
                "                    \"code\":\"sku002\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"attr_data\": [\n" +
                "            {\n" +
                "                \"name\": {\n" +
                "                    \"zh_cn\": \"属性组名称\",\n" +
                "                    \"en_us\": \"Attr group name\"\n" +
                "                },\n" +
                "                \"attr_list\": [\n" +
                "                    {\n" +
                "                        \"name\": {\n" +
                "                            \"zh_cn\": \"属性名称1\",\n" +
                "                            \"en_us\": \"Attr  name1\"\n" +
                "                        },\n" +
                "                        \"code\": \"1001\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": {\n" +
                "                            \"zh_cn\": \"属性名称2\",\n" +
                "                            \"en_us\": \"Attr  name2\"\n" +
                "                        },\n" +
                "                        \"code\": \"1002\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"extra_data\": [\n" +
                "            {\n" +
                "                \"name\": {\n" +
                "                    \"zh_cn\": \"配料组名称\",\n" +
                "                    \"en_us\": \"extra group name\"\n" +
                "                },\n" +
                "                \"min\": 1,\n" +
                "                \"limit\": 99,\n" +
                "                \"extra_list\": [\n" +
                "                    {\n" +
                "                        \"id\":111,\n" +
                "                        \"limit\": 9\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\":111,\n" +
                "                        \"limit\": 8\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"second_category_id\": 0\n" +
                "    }";


        AddProductRequestDTO addProductRequestDTO = JSONUtils.jsonToObject(requestBody, AddProductRequestDTO.class);
        addProductRequestDTO.getName().setEnUs("dong tai huo qu xxxx");
        return Stream.of(Arguments.of(addProductRequestDTO));
    }
}
