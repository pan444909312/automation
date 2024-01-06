package com.miller.merchant.order.delivery.response;

import com.miller.merchant.dto.BasicResponseDTO;
import com.panda.merchant.server.api.vo.app.merchant.resp.OrderBlockDataRespDTO;

/**
 * 响应对象_配送中列表-商家点击"用户已取餐"
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一响应对象。
 * </p>
 * <p>
 * 注意: 这里的对象应该是需要引用app-server工程中的，但是这个工程是个war包无法引用，所以这里直接引用了merchant-api，因为发现接口
 * 请求需要用到的几个字段刚好可以复用。后续等开发修改了之后在同步修改测试用例吧。而且很申请的是虽然是两个工程，但是开发代码这对象居然是一摸一样的，不
 * 知道为什么开发代码不复用。。。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/28 19:59:18
 */
public class MerchantConfirmUserReceivedOrderResponseDTO extends BasicResponseDTO<OrderBlockDataRespDTO> {
    // 测试用例应该基本不需要添加额外的响应信息
}
