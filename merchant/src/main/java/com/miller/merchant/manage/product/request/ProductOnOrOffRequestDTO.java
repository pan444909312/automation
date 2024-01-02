package com.miller.merchant.manage.product.request;

import com.panda.product.server.api.dto.product.req.ProductStateReqDTO;

/**
 * 请求对象-商品上架、下架
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
public class ProductOnOrOffRequestDTO extends ProductStateReqDTO {

}


