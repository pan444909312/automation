package com.miller.merchant.order.delivery.request;

import com.panda.merchant.server.api.vo.app.merchant.req.OrderTerminalStatusUpdateQueryReqVO;

/**
 * 请求对象_配送中列表-商家点击"用户已取餐"
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一请求对象。
 * 这里之所以包装了一层是因为对请求字段可能需要二次处理，比如：加密、验签等.
 * 测试用例最终发送请求时需要使用{@code RequestUtils}类对请求字段做统一的二次处理。
 * </p>
 * <p>
 *     注意: 这里的对象应该是需要引用app-server工程中的，但是这个工程是个war包无法引用，所以这里直接引用了merchant-api，因为发现接口
 *     请求需要用到的几个字段刚好可以复用。后续等开发修改了之后在同步修改测试用例吧。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/28 19:59:02
 */
public class MerchantConfirmUserReceivedOrderRequestDTO extends OrderTerminalStatusUpdateQueryReqVO {
    // 测试用例需要使用的额外字段可以补充在这里。
}


