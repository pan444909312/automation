package com.miller.pos.product.addproductdemo.provider;

import com.github.javafaker.Faker;
import com.miller.pos.product.addproductdemo.request.AddProductRequestDTO;
import com.miller.service.framework.util.ResourceUtils;
import com.miller.service.framework.util.JSONUtils;
import org.junit.jupiter.params.provider.Arguments;

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
        String requestBody = ResourceUtils.readFileFromResources("/data/product/addproduct/addproduct.json");
        AddProductRequestDTO addProductRequestDTO = JSONUtils.jsonToObjectByJackson(requestBody, AddProductRequestDTO.class);
        addProductRequestDTO.getName().setEnUs("add_prodcutname_test_"+pos_item_id);
        addProductRequestDTO.getName().setZhCn(product_name);
        addProductRequestDTO.setPosProductId(pos_item_id);

//        属性组的添加
        addProductRequestDTO.getReqAttrGroupList().get(0).getName().setZhCn("新增属性组测试"+product_name);
        addProductRequestDTO.getReqAttrGroupList().get(0).getName().setEnUs("add_groupattr_test"+product_name);

        addProductRequestDTO.getReqAttrGroupList().get(0).getAttrValueList().get(0).setCode(faker.number().digits(6));
        addProductRequestDTO.getReqAttrGroupList().get(0).getAttrValueList().get(0).getName().setZhCn(product_name+"新增属性测试");

        addProductRequestDTO.getReqAttrGroupList().get(0).getAttrValueList().get(0).getName().setEnUs("add_atter_test"+pos_item_id);

        addProductRequestDTO.getReqAttrGroupList().get(0).getAttrValueList().get(1).setCode(faker.number().digits(6));
        addProductRequestDTO.getReqAttrGroupList().get(0).getAttrValueList().get(1).getName().setZhCn(product_name+"新增属性测试1");

        addProductRequestDTO.getReqAttrGroupList().get(0).getAttrValueList().get(1).getName().setEnUs("add_atter_test1"+pos_item_id);
//规格的添加
        addProductRequestDTO.getSpecification().getName().setZhCn("新增规格组测试"+product_name);
        addProductRequestDTO.getSpecification().getName().setEnUs("add_groupspec_test"+product_name);

        addProductRequestDTO.getSpecification().getSpecValueList().get(0).getName().setZhCn(product_name+"新增规格测试");
        addProductRequestDTO.getSpecification().getSpecValueList().get(0).getName().setEnUs("add_spec_test"+pos_item_id);
        addProductRequestDTO.getSpecification().getSpecValueList().get(0).setCode(faker.number().digits(6));
        addProductRequestDTO.getSpecification().getSpecValueList().get(0).setPrice(Long.valueOf(faker.number().digits(4)));

        addProductRequestDTO.getSpecification().getSpecValueList().get(1).getName().setZhCn(product_name+"新增规格测试1");
        addProductRequestDTO.getSpecification().getSpecValueList().get(1).getName().setEnUs("add_spec_test1"+pos_item_id);
        addProductRequestDTO.getSpecification().getSpecValueList().get(1).setCode(faker.number().digits(6));
        addProductRequestDTO.getSpecification().getSpecValueList().get(1).setPrice(Long.valueOf(faker.number().digits(4)));
// 配料的添加


        addProductRequestDTO.getReqTagGroupList().get(0).getName().setZhCn(product_name+"新增配料测试");
        addProductRequestDTO.getReqTagGroupList().get(0).getName().setEnUs("add_tag_test"+pos_item_id);
        return Stream.of(Arguments.of(addProductRequestDTO));
    }
}
