package com.miller.erp.manage.merchant.fence.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 请求对象_编辑商家-配送围栏,这个是html的请求对象，所以数据需要自己构造.
 * 代码工程在 platform 服务中，无法直接引入jar包，这个是一个war包
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 13:49:23
 */
@Data
public class FenceRequestDTO implements Serializable {
    private String areaFenceData;
    private Integer deliveryType;
    /**
     * 是否替换,传1覆盖老的数据
     */
    private Integer isReplaceOld;
    private String hpFenceTemplateSubObj;
    // 这两个参数是从请求头传过去的，在参数 referer 里面
//    private Long shopId;
//    private Integer shopType;


    @Data
    public static class BaseFenceOperateDTO {

        /**
         * 父级id(可以是shopId, addressConfigId, templateId)
         */
        private Long pid;

        /**
         * 当前修改的对应数据的值
         */
        private String fenceIdStr;

        /**
         * 围栏坐标链(有序从头到尾用 | 分隔拼接)
         */
        private String fenceLatlngChain;

        /**
         * 围栏名称
         */
        private String fenceName;

        /**
         * 是否编辑过
         */
        private Integer operate;
    }
}