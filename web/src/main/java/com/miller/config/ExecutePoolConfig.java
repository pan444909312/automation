package com.miller.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Data
public class ExecutePoolConfig {



    /**
     * TODO 临时存储线程任务 taskId 对应的结果信息, 对接落库表后完善
     *
     */
    private Map<String, LinkedList<Object>> taskPool = new LinkedHashMap<>();

    public void setTaskPool(String taskId) {
        if (this.taskPool.size() == 30){
            for(String key:  this.taskPool.keySet()){
                this.taskPool.remove(key);
                break;
            }
        }
        this.taskPool.put(taskId, new LinkedList<>());
    }


    public void setTaskPool(String taskId , String value) {
        LinkedList<Object> threadValueList = new LinkedList<>();
        if (!this.taskPool.containsKey(taskId)) {
            threadValueList.add(value);
            taskPool.put(taskId,threadValueList);
            return;
        }

        threadValueList = this.taskPool.get(taskId);
        threadValueList.add(value);
        taskPool.put(taskId,threadValueList);
    }

    /**
     * 线程池，如果有特殊的业务场景，可以自行再添加线程池
     * 使用示例   在需要使用线程池的方法上增加注解 @Async("taskExecutor")
     *
     * @return
     */
    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(3);
        //最大线程数
        executor.setMaxPoolSize(5);
        //队列容量
        executor.setQueueCapacity(10);
        //活跃时间
        executor.setKeepAliveSeconds(10);
        //线程名字前缀
        executor.setThreadNamePrefix("task-");

        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行,即变为单线程处理
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    public List<Object> getTaskPool(String taskId) {
        return taskPool.get(taskId);
    }
}
