package com.miller.service.util;

import com.miller.service.dto.XXLResponseDTO;
import com.miller.service.framework.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * XXLJob工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @link <a href="http://8.210.167.35:8122/hp-job-admin/toLogin">XXL Job</a>
 * @since 2024/8/6 20:44:16
 */
@Slf4j
public class XXLJobUtils {

    private static final String XXL_JOB_ADMIN_URL = "http://8.210.167.35:8122/hp-job-admin";
    private static HashMap<String, Object> headers = new HashMap<>();
    private static HashMap<String, Object> responseCookies;



    private static volatile boolean initialized = false;


    public static synchronized void init() {
        if (initialized) return;
        try {
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            login();
            initialized = true;
        } catch (Exception e) {
            throw new RuntimeException("XXLJobUtils 初始化失败", e);
        }
    }

    private static void login() {
        try {
            var params = new HashMap<String, Object>();
            params.put("userName", "admin");
            params.put("password", "123456");
            params.put("ifRemember", "on");

            Map<String, Object> stringObjectMap = HttpUtils.sendPostRequest(XXL_JOB_ADMIN_URL + "/login", params, headers, null, null);
            responseCookies = (HashMap<String, Object>) stringObjectMap.get("cookies");
        } catch (Exception e) {
            log.error("XXLJobUtils login failed", e);
            throw new RuntimeException("Failed to initialize XXLJobUtils", e);
        }
    }

    /**
     * 触发定时任务
     *
     * @param jobId 任务ID
     * @return true:完成; false: 超时
     */
    public static synchronized boolean triggerJob(String jobId) {
        if (!initialized) {
            init();  // 按需初始化
        }
        var params = new HashMap<String, Object>();
        params.put("id", jobId);

        XXLResponseDTO XXLResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(XXL_JOB_ADMIN_URL + "/jobinfo/trigger", params, headers, null, responseCookies, XXLResponseDTO.class);
        if (XXLResponseDTO.getCode().equals(200)) {
            log.warn("定时任务执行成功, 请稍等一会自行查询是否执行完成, jobId={}", jobId);
            try {
                Thread.sleep(1000 * 20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return true;
            // 查询定时任务执行结果. 这里不查了
//            return taskStatus(jobId);
        } else {
            // log.error("触发定时任务失败，Job ID is:{}", jobId);
            return false;
        }
    }

    /**
     * 查询定时任务执行结果
     *
     * @param jobId 任务ID
     * @return true:完成; false: 超时
     */
    private static boolean taskStatus(String jobId) {
        long timeout = 1000 * 60 * 5;  // 超时时间设置为5分钟
        while (true) {
            try {
                Thread.sleep(1000 * 30);    // 休眠30秒
                timeout -= 1000 * 30;   // 减去休眠时间
                // System.out.println(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (timeout <= 0) {
                // log.error("定时任务执行超时，Job ID is:{}", jobId);
                // System.out.println(timeout);
                return false;
            }
            // 查询定时任务执行结果 TODO 待开发完成之后补充
            boolean isDone = false;
            if (isDone) {
                // 定时任务执行完成
                log.info("定时任务执行完成，Job ID is:{}", jobId);
                return true;
            }
        }
    }


    public static void main(String[] args) {
        triggerJob("11");
    }

}

