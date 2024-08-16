package com.miller.erp.moudle.manage.merchant.auditIdentity.request;


import com.hungrypanda.app.server.common.annotation.FieldName;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求对象_商家管理-商家认证
 * 这个是前后端不分离的接口,老的服务，代码工程在 platform 里面，无法直接引入jar包，这个是一个war包，而且请求参数是字符串，而不是对象
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/13 14:49:23
 */
@Data
public class AuditIdentityMerchantRequestDTO implements Serializable {
    private long userId;
    private long shopId;//商家ID
    @FieldName(desc="商家名称")
    private String shopName;//商家名称
    @FieldName(desc="店主名称")
    private String shopUserName;//店主名称
    @FieldName(desc="店主身份证号")
    private String  IdCardNo;//店主身份证号
    private int userStatus;//店主认证状态
    @FieldName(desc="营业执照号")
    private String  BusinessLicenseNo;//营业执照号
    private int BusinessLicenseStatus;//营业执照认证状态
    @FieldName(desc="餐饮执照号")
    private String CateringLicenseNo;//餐饮执照号
    private int CateringLicenseStatus;//餐饮执照认证状态
    private String createTime;//创建时间
    private int status;//整体认证状态
    private long startTime;
    private long endTime;
    @FieldName(desc="身份证照片")
    private String idCardPic;//身份证照片
    @FieldName(desc="商铺图片")
    private String merchantPic;//商铺图片
    @FieldName(desc="营业执照照片")
    private String BusinessLicensePic;//营业执照照片
    @FieldName(desc="餐饮执照照片")
    private String CateringLicensePic;//餐饮执照照片
    private long updateTime;
    private long lCreateTime;
    private String shopRealPic;//店铺实景图
    private Integer identityStatus;
    private String fixPoint;
}