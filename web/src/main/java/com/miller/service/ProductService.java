package com.miller.service;

import com.miller.entity.dao.BatchProductDao;

public interface ProductService {

    void batchProduct(String token, BatchProductDao productDao, int addCount, String taskId);

}
