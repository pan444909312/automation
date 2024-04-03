package com.miller.pos.shop.queryshop.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求对象_商品新增
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一请求对象。
 * 这里之所以包装了一层是因为对请求字段可能需要二次处理，比如：加密、验签等.
 * 测试用例最终发送请求时需要使用{@code RequestUtils}类对请求字段做统一的二次处理。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 18:29:02
 */
@Getter
@Setter
@ToString
public class QueryShopRequestDTO  {
    private String shop_id ;
}


