package com.miller.controller.tools.product;

import com.miller.config.ExecutePoolConfig;
import com.miller.controller.tools.ResultVO;
import com.miller.controller.tools.product.dao.BatchProductDao;
import com.miller.controller.tools.product.service.LoginService;
import com.miller.controller.tools.product.service.ProductService;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/tools/product")
public class ProductToolsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ExecutePoolConfig executePoolConfig ;

    @PostMapping("/batch/add")
    public ResultVO batchAddProduct(@RequestBody BatchProductDao reqDao) {
        final String taskId = UUID.randomUUID().toString();

        ERPLoginFlow.loginByDefaultUser();
//        String pcToken = loginService.pcLogin(reqDao.getShopId());

        int forCount = 10;
        int threadCount = reqDao.getCount() / forCount;

        // 异步执行，多线程
        for (int i = 0; i <= threadCount; i++) {
            if (i == threadCount) {
                forCount = reqDao.getCount() - forCount * threadCount;
            }
            productService.batchProduct( reqDao, forCount, taskId);
        }
        executePoolConfig.setTaskPool(taskId);
        log.info("taskId:{}",taskId);
        return ResultVO.success(taskId);
    }

    @GetMapping("/getThreadRes")
    public ResultVO getThreadRes(@RequestParam String taskId) {
        return ResultVO.success(executePoolConfig.getTaskPool(taskId));
    }



}
