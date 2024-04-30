

package com.miller.pos.product.addproduct.response;

import com.miller.pos.dto.BasicResponseDTO;
import com.panda.pos.server.api.dto.open.OpenApiResponse;
import com.panda.pos.server.api.dto.open.product.ProductCreationResponse;


/**
 * 响应对象_店铺查询
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一响应对象。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 18:29:18
 */
public class AddProductResponseDTO extends BasicResponseDTO<ProductCreationResponse>
 {
    // 测试用例应该基本不需要添加额外的响应信息
}