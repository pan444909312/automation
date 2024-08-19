package com.miller.erp.moudle.manage.merchant.auth.request;


import com.panda.merchant.server.api.dto.ShopIdDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求对象
 * <p>
 * 接口代码是在 hp-erp-server
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 16:49:23
 */
@Data
public class MerchantAuthRequestDTO {
    private Long shopId;
    private Integer status;
}
