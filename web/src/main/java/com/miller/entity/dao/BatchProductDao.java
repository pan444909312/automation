package com.miller.entity.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BatchProductDao {
    private String shopId;
    private Long menuId;
    private String productName;
    private int count;
}
