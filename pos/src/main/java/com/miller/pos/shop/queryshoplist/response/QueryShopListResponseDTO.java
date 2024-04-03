

package com.miller.pos.shop.queryshoplist.response;

import com.miller.pos.dto.BasicResponseDTO;
import com.panda.pos.server.api.dto.open.shop.OpenApiShopResponse;

import java.util.List;

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
public class QueryShopListResponseDTO extends BasicResponseDTO<List<OpenApiShopResponse>> {
    // 测试用例应该基本不需要添加额外的响应信息
}