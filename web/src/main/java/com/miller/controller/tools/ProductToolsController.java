package com.miller.controller.tools;

import com.miller.config.ExecutePoolConfig;
import com.miller.entity.dao.BatchProductDao;
import com.miller.entity.report.vo.ResultVo;
import com.miller.service.LoginService;
import com.miller.service.ProductService;
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
    public ResultVo batchAddProduct(@RequestBody BatchProductDao reqDao) {
        final String taskId = UUID.randomUUID().toString();
        //  登录并获取 Pc 站 Token
        String pcToken = loginService.pcLogin(reqDao.getShopId());

        int forCount = 10;
        int threadCount = reqDao.getCount() / forCount;

        // 异步执行，多线程
        for (int i = 0; i <= threadCount; i++) {
            if (i == threadCount) {
                forCount = reqDao.getCount() - forCount * threadCount;
            }
            productService.batchProduct(pcToken, reqDao, forCount, taskId);
        }
        executePoolConfig.setTaskPool(taskId);
        log.info("taskId:{}",taskId);
        return ResultVo.success(taskId);
    }

    @GetMapping("/getThreadRes")
    public ResultVo getThreadRes(@RequestParam String taskId) {
        return ResultVo.success(executePoolConfig.getTaskPool(taskId));
    }



}
