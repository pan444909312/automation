package com.miller.erp.manage.merchant.fence.request;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 请求对象_编辑商家-配送围栏,这个是html的请求对象，所以数据需要自己构造
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 13:49:23
 */
@Data
public class FenceRequestDTO implements Serializable {
    private String areaFenceData;
    private Integer deliveryType;
    private Integer isReplaceOld;
    private String hpFenceTemplateSubObj;
    // 这两个参数是从请求头传过去的，在参数 referer 里面
    private Long shopId;
    private Integer shopType;
}
