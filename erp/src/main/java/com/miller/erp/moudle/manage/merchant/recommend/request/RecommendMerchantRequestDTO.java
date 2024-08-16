package com.miller.erp.moudle.manage.merchant.recommend.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 请求对象_编辑商家-配送围栏,这个是html的请求对象，所以数据需要自己构造.
 * 代码工程在 platform 服务中，无法直接引入jar包，这个是一个war包
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 21:49:23
 */
@Data
public class RecommendMerchantRequestDTO implements Serializable {
    private Long shopId;
    private Integer type;
}