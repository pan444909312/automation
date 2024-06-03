package com.miller.pos.product.addproduct.provider;

import com.github.javafaker.Faker;
import com.miller.pos.product.addproduct.request.AddProductRequestDTO;
import com.panda.pos.server.api.dto.open.NameMultiLanguageDTO;
import com.panda.pos.server.api.dto.open.NameMultiLanguageLength64DTO;
import com.panda.pos.server.api.dto.open.product.ProductAttrGroupDTO;
import com.panda.pos.server.api.dto.open.product.ProductImageDTO;
import com.panda.pos.server.api.dto.open.product.ProductSpecDTO;
import com.panda.pos.server.api.dto.open.product.ProductTagGroupDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.*;
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
public class AddProductDataProvider {
    static Faker faker= new Faker(CHINESE);
    static Stream<Arguments> addproduct() {

        AddProductRequestDTO addProductRequestDTO = new AddProductRequestDTO();

        addProductRequestDTO.setPosProductId(pos_item_id); //@JsonProperty("pos_item_id")
        addProductRequestDTO.setCode("1001");

        NameMultiLanguageDTO  nameMultiLanguageDTO =new NameMultiLanguageDTO();
        nameMultiLanguageDTO.setZhCn("新增商品测试");
        nameMultiLanguageDTO.setEnUs("add_prodcut_test");

        addProductRequestDTO.setDesc(nameMultiLanguageDTO);
        NameMultiLanguageDTO  pnameMultiLanguageDTO =new NameMultiLanguageDTO();
        pnameMultiLanguageDTO.setZhCn(product_name);
        pnameMultiLanguageDTO.setEnUs("add_prodcutname_test"+pos_item_id);

        addProductRequestDTO.setName(pnameMultiLanguageDTO);

        addProductRequestDTO.setExciseTaxRate(BigDecimal.valueOf(3.44));

        addProductRequestDTO.setSeq(1);
        addProductRequestDTO.setSubMenuId(0L);

        Map<String, Object> other = new HashMap<>();
        other.put("is_zp", 0);
        addProductRequestDTO.setOther(other);


        ProductImageDTO productImageDTO  =new ProductImageDTO();

        productImageDTO.setType("image");
        productImageDTO.setUrl("https://298productImageDTO383278759-fuji-test-us-west-2.s3.us-west-2.amazonaws.com/fuji-product-webapp-test/202312/b72765aa-8fbb-45ba-820e-c631808bdcc5.jpg");

        List<ProductImageDTO> image = new ArrayList<>();
        image.add(productImageDTO);
        addProductRequestDTO.setImageList( image  );
//属性组的添加
        ProductAttrGroupDTO productAttrGroupDTO = new ProductAttrGroupDTO();
        ProductAttrGroupDTO.ProductAttrValue  productAttrValue = new ProductAttrGroupDTO.ProductAttrValue();
        productAttrValue.setCode(faker.number().digits(6));
//        name添加
        NameMultiLanguageDTO  atterMultiLanguageDTO =new NameMultiLanguageDTO();
        atterMultiLanguageDTO.setZhCn(product_name+"新增属性测试");
        atterMultiLanguageDTO.setEnUs("add_atter_test"+pos_item_id);
        productAttrValue.setName(atterMultiLanguageDTO);
//        排序的添加
        productAttrValue.setSeq(1);
        List<ProductAttrGroupDTO.ProductAttrValue> attrValueList = new ArrayList<>();
        attrValueList.add(productAttrValue);
        productAttrGroupDTO.setAttrValueList(attrValueList);

        NameMultiLanguageLength64DTO nameMultiLanguageLength64DTO =new NameMultiLanguageLength64DTO();
        nameMultiLanguageLength64DTO.setEnUs("add_attergroup_test"+pos_item_id);
        nameMultiLanguageLength64DTO.setZhCn(product_name+"新增属性组测试");
        productAttrGroupDTO.setName(nameMultiLanguageLength64DTO);

        List<ProductAttrGroupDTO> reqAttrGroupList = new ArrayList<>();
        reqAttrGroupList.add(productAttrGroupDTO);
        addProductRequestDTO.setReqAttrGroupList(reqAttrGroupList);
//        @ApiModelProperty("配料")
//        @JsonProperty("extra_data")
//        private List<ProductTagGroupDTO> reqTagGroupList;
        ProductTagGroupDTO productTagGroupDTO = new ProductTagGroupDTO();
        NameMultiLanguageDTO  tagMultiLanguageDTO =new NameMultiLanguageDTO();
        tagMultiLanguageDTO.setZhCn(product_name+"新增配料测试");
        tagMultiLanguageDTO.setEnUs("add_tag_test"+pos_item_id);
        productTagGroupDTO.setName(tagMultiLanguageDTO);
        productTagGroupDTO.setLimit(3);
        productTagGroupDTO.setMin(1);
        ProductTagGroupDTO.ProductTagValue  productTagValue= new ProductTagGroupDTO.ProductTagValue();
        productTagValue.setLimit(1);
//        productTagValue.setSeq(1);
//        productTagValue.setPosTagId("111");
//        productTagValue.setPrice(Long.valueOf("9900"));
//        productTagValue.setTagValueNameZhCn("红藕");
        productTagValue.setId(818734L);

        List<ProductTagGroupDTO.ProductTagValue> tagValueList = new ArrayList<>();
        tagValueList.add(productTagValue);
        productTagGroupDTO.setTagValueList(tagValueList);


        List<ProductTagGroupDTO> reqTagGroupList = new ArrayList<>();
        reqTagGroupList.add(productTagGroupDTO);
        addProductRequestDTO.setReqTagGroupList(reqTagGroupList);

//        @ApiModelProperty(value = "规格", required = true)
//        @JsonProperty("sku_data")
//        @NotNull(message = "hp.pos.open-api.product.specification-missing")
//        @Valid
//        private ProductSpecDTO specification;
        ProductSpecDTO productSpecDTO =new ProductSpecDTO();
        NameMultiLanguageDTO  skuMultiLanguageDTO =new NameMultiLanguageDTO();
        skuMultiLanguageDTO.setZhCn(product_name+"新增规格测试");
        skuMultiLanguageDTO.setEnUs("add_spec_test"+pos_item_id);
        productSpecDTO.setName(skuMultiLanguageDTO);

        ProductSpecDTO.ProductSpecValue productSpecValue=new ProductSpecDTO.ProductSpecValue();
        NameMultiLanguageDTO  skulist1MultiLanguageDTO =new NameMultiLanguageDTO();
        skulist1MultiLanguageDTO.setZhCn(product_name+"新增规格值测试");
        skulist1MultiLanguageDTO.setEnUs("add_sku_test"+pos_item_id);
        productSpecValue.setName(skulist1MultiLanguageDTO);
        productSpecValue.setCode(faker.number().digits(6));
        productSpecValue.setPrice(Long.valueOf("2098"));
        productSpecValue.setStock(99);
        productSpecValue.setStockEveryday(-1);
        List<ProductSpecDTO.ProductSpecValue> specValueList = new ArrayList<>();

        specValueList.add(productSpecValue);
        productSpecDTO.setSpecValueList(specValueList);
//        入参是个对象
        addProductRequestDTO.setSpecification(productSpecDTO);

        return Stream.of(Arguments.of(addProductRequestDTO));
    }
}
