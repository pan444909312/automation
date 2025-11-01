package com.miller.service.util;

import com.miller.service.dto.XXLResponseDTO;
import com.miller.service.framework.db.DBUtils;
import com.miller.service.framework.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.miller.service.dto.XXLJobLogResponseDTO;

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

    // 外网地址
    private static final String XXL_JOB_ADMIN_URL ;

    private static HashMap<String, Object> headers = new HashMap<>();
    private static HashMap<String, Object> responseCookies;



    private static volatile boolean initialized = false;

    static {
//        String profilesActive = System.getenv("ENV_VAR");
        String profilesActive = System.getProperty("spring.profiles.active", "test");
        if (Objects.equals(profilesActive, "prod")) {
            // 使用内网地址
            XXL_JOB_ADMIN_URL = "http://172.31.236.14:8122/hp-job-admin";

        } else {
            // 使用外网地址
            XXL_JOB_ADMIN_URL = "http://8.210.167.35:8122/hp-job-admin";


        }
    }


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
            log.warn("定时任务触发成功, 开始查询执行状态, jobId={}", jobId);
//            try {
//                Thread.sleep(1000 * 20);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            // 查询定时任务执行结果
            return taskStatus(jobId);
        } else {
            // log.error("触发定时任务失败，Job ID is:{}", jobId);
            return false;
        }
    }

    /**
     * 查询定时任务执行结果
     * 使用API轮询查询任务状态，替代Thread.sleep等待
     *
     * @param jobId 任务ID
     * @return true:完成; false: 超时或失败
     */
    private static boolean taskStatus(String jobId) {
        // 配置轮询参数
        PollingConfig config = createPollingConfig();
        
        // 开始轮询
        log.info("开始轮询查询任务状态，Job ID: {}, 超时时间: {}分钟, 轮询间隔: {}秒", 
                jobId, config.timeoutMinutes, config.pollIntervalSeconds);
        
        return pollUntilComplete(jobId, config);
    }
    
    /**
     * 轮询配置类
     */
    private static class PollingConfig {
        final long timeoutMillis;
        final long pollIntervalMillis;
        final int timeoutMinutes;
        final int pollIntervalSeconds;
        
        PollingConfig(long timeoutMillis, long pollIntervalMillis) {
            this.timeoutMillis = timeoutMillis;
            this.pollIntervalMillis = pollIntervalMillis;
            this.timeoutMinutes = (int) (timeoutMillis / (1000 * 60));
            this.pollIntervalSeconds = (int) (pollIntervalMillis / 1000);
        }
    }
    
    /**
     * 创建轮询配置
     *
     * @return 轮询配置对象
     */
    private static PollingConfig createPollingConfig() {
        long timeoutMillis = 1000 * 60 * 30;  // 30分钟超时
        long pollIntervalMillis = 1000 * 10;  // 10秒轮询间隔
        return new PollingConfig(timeoutMillis, pollIntervalMillis);
    }
    
    /**
     * 轮询直到任务完成
     *
     * @param jobId 任务ID
     * @param config 轮询配置
     * @return true:任务完成; false:超时或失败
     */
    private static boolean pollUntilComplete(String jobId, PollingConfig config) {
        long startTime = System.currentTimeMillis();
        
        while (true) {
            try {
                // 检查超时
                if (isTimeout(startTime, config.timeoutMillis)) {
                    log.error("定时任务执行超时，Job ID: {}", jobId);
                    return false;
                }
                
                // 检查任务状态
                if (queryJobExecutionStatus(jobId)) {
                    log.info("定时任务执行完成，Job ID: {}", jobId);
                    return true;
                }
                
                // 等待下次轮询
                Thread.sleep(config.pollIntervalMillis);
                
            } catch (InterruptedException e) {
                handleInterruptedException(jobId, e);
                return false;
            } catch (Exception e) {
                handlePollingException(jobId, e, config.pollIntervalMillis);
            }
        }
    }
    
    /**
     * 检查是否超时
     *
     * @param startTime 开始时间
     * @param timeoutMillis 超时时间（毫秒）
     * @return true:已超时; false:未超时
     */
    private static boolean isTimeout(long startTime, long timeoutMillis) {
        return System.currentTimeMillis() - startTime > timeoutMillis;
    }
    

    

    
    /**
     * 处理中断异常
     *
     * @param jobId 任务ID
     * @param e 中断异常
     */
    private static void handleInterruptedException(String jobId, InterruptedException e) {
        log.error("查询任务状态时被中断，Job ID: {}", jobId, e);
        Thread.currentThread().interrupt();
    }
    
    /**
     * 处理轮询异常
     *
     * @param jobId 任务ID
     * @param e 异常
     * @param pollIntervalMillis 轮询间隔（毫秒）
     */
    private static void handlePollingException(String jobId, Exception e, long pollIntervalMillis) {
        log.error("查询任务状态时发生异常，Job ID: {}", jobId, e);
        
        try {
            // 发生异常时，等待一段时间后继续轮询
            Thread.sleep(pollIntervalMillis);
        } catch (InterruptedException ie) {
            log.error("异常处理时被中断，Job ID: {}", jobId, ie);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 查询任务执行状态
     * 根据jobId查询任务的最新执行记录，判断执行状态
     *
     * @param jobId 任务ID
     * @return true:执行成功; false: 执行中或失败
     */
    private static boolean queryJobExecutionStatus(String jobId) {
        try {
            // 确保已初始化
            if (!initialized) {
                init();
            }
            
            // 查询任务日志
            XXLJobLogResponseDTO.XXLJobLogItem latestLog = queryLatestJobLog(jobId);
            if (latestLog == null) {
                log.debug("未找到任务执行记录，可能任务还未开始执行，Job ID: {}", jobId);
                return false;
            }
            
            // 判断任务执行状态
            return analyzeJobExecutionStatus(latestLog, jobId);
            
        } catch (Exception e) {
            log.error("查询任务执行状态时发生异常，Job ID: {}", jobId, e);
            return false;
        }
    }
    

    
    /**
     * 查询最新的任务执行日志
     *
     * @param jobId 任务ID
     * @return 最新的执行日志，如果未找到返回null
     */
    private static XXLJobLogResponseDTO.XXLJobLogItem queryLatestJobLog(String jobId) {
        // 构建查询参数
        var params = new HashMap<String, Object>();
        params.put("jobGroup", "1");      // 默认任务组
        params.put("jobId", jobId);       // 任务ID
        params.put("logStatus", "0");     // 查询所有状态
        params.put("filterTime", "");     // 过滤时间（空表示不过滤）
        params.put("start", "0");         // 起始位置
        params.put("length", "1");        // 只获取1条最新记录，提高查询效率
        
        // 调用XXL-Job日志查询API
        XXLJobLogResponseDTO response = HttpUtils.sendPostRequestReturnJavaObject(
            XXL_JOB_ADMIN_URL + "/joblog/pageList", 
            params, 
            headers, 
            null, 
            responseCookies, 
            XXLJobLogResponseDTO.class
        );
        
        // 检查响应数据
        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            return null;
        }
        
        return response.getData().get(0);
    }
    
    /**
     * 分析任务执行状态
     *
     * @param logItem 执行日志项
     * @param jobId 任务ID
     * @return true:执行成功; false: 执行中或失败
     */
    private static boolean analyzeJobExecutionStatus(XXLJobLogResponseDTO.XXLJobLogItem logItem, String jobId) {
        Integer handleCode = logItem.getHandleCode();
        
        // handleCode为null表示任务还在执行中
        if (handleCode == null) {
            log.debug("任务正在执行中（handleCode为null），Job ID: {}", jobId);
            return false;
        }
        
        // 根据handleCode判断执行状态
        switch (handleCode) {
            case 200:
                log.info("任务执行成功，Job ID: {}", jobId);
                return true;
                
            case 500:
                String errorMsg = logItem.getHandleMsg() != null ? logItem.getHandleMsg() : "未知错误";
                log.error("任务执行失败，Job ID: {}，错误信息: {}", jobId, errorMsg);
                return false;
                
            case 0:
                log.debug("任务正在执行中，Job ID: {}", jobId);
                return false;
                
            default:
                log.warn("任务执行状态未知，handleCode: {}，Job ID: {}", handleCode, jobId);
                return false;
        }
    }

}

