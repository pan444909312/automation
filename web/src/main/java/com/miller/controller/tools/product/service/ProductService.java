package com.miller.controller.tools.product.service;

import com.miller.controller.tools.product.dao.BatchProductDao;

public interface ProductService {

    void batchProduct( BatchProductDao productDao, int addCount, String taskId);

}
