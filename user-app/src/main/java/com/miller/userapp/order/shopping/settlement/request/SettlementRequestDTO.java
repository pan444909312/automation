package com.miller.userapp.order.shopping.settlement.request;

import com.hungrypanda.app.server.api.req.order.CreateVirtualOrderReq;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求对象_结算页(由于历史原因，也叫创建虚单)
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一请求对象。
 * 这里之所以包装了一层是因为对请求字段可能需要二次处理，比如：加密、验签等.
 * 测试用例最终发送请求时需要使用{@link com.miller.userapp.util.RequestUtils}类对请求字段做统一的二次处理。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/10 14:49:02
 */
@Data
public class SettlementRequestDTO extends CreateVirtualOrderReq {
    // 测试用例需要使用的额外字段可以补充在这里。
    // 抓包发现请求会传这个字段，但是研发代码中未发现此字段，这个字段在创建订单的时候才需要传
    // private Integer verify;
}


