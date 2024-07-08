package com.miller.service.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 命令行工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/8 11:00:47
 */
@Slf4j
public class CommandUtils {
    /**
     * 命令执行超时时间,默认10分钟
     */
    private static long timeout = 60 * 1000 * 10;

    /**
     * 在终端执行命令，并返回命令行终端的结果。默认超时10分钟。默认串行执行。
     *
     * @param command 待执行的命令
     * @return 命令行执行结果
     */
    public static String executeCommand(String command) {
        return executeCommand(command, timeout);
    }

    /**
     * 在终端执行命令，并返回命令行终端的结果。默认串行执行。
     *
     * @param command 命令
     * @param timeout 超时时间
     * @return 命令行执行结果
     */
    public static String executeCommand(String command, long timeout) {
        return executeCommand(command, timeout, false);
    }

    /**
     * 在终端执行命令，并返回命令行终端的结果
     *
     * @param command 命令
     * @param timeout 命令执行超时时间
     * @param isAsync 是否异步执行；true: 异步；false: 同步。需要注意异步执行时主线成执行结束，那么异步的线程也会结束
     * @return 命令行执行结果
     */
    public static String executeCommand(String command, long timeout, boolean isAsync) {
        // 命令执行结果
        String commandResul = null;
        //接收正常结果流
        ByteArrayOutputStream successStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        // 解析命令
        CommandLine commandLine = CommandLine.parse(command);
        // 创建执行命令执行器
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(successStream, errorStream);
        exec.setStreamHandler(streamHandler);
        //设置超时时间
        ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        exec.setWatchdog(watchdog);
        // 异步执行
        if (isAsync == Boolean.TRUE) {
            log.info("开始异步执行命令: {}", command);
            ExecuteResultHandler executeResultHandler = new ExecuteResultHandler() {
                @Override
                public void onProcessComplete(int exitValue) {
                    String commandResul = successStream.toString(Charset.defaultCharset());
                    log.info("异步执行命令结果: {}", commandResul);
                }

                @Override
                public void onProcessFailed(ExecuteException e) {
                    String err = errorStream.toString(Charset.defaultCharset());
                    log.error("异步执行命令出错: {}", err, e);
                    watchdog.destroyProcess();
                }
            };
            try {
                exec.execute(commandLine, executeResultHandler);
                // 避免主线程退出导致程序退出
//                Thread.currentThread().join();
            } catch (ExecuteException executeException) {
                if (watchdog.killedProcess()) {
                    // 执行命令超时，被 watchdog 故意杀死
                    log.error("执行命令超时,超时时间: {}", timeout);
                    watchdog.destroyProcess();
                }
            } catch (IOException e) {
                log.error("异步执行命令异常", e);
                watchdog.destroyProcess();
                throw new RuntimeException(e);
            }

        } else {
            log.info("开始串行执行命令: {}", command);
            // 顺序执行
            try {
                int execute = exec.execute(commandLine);
                if (execute != 0) {
                    commandResul = errorStream.toString(Charset.defaultCharset());
                    log.error("执行命令失败: {}", commandResul);
                    throw new RuntimeException("执行命令失败:" + commandResul);
                }
            } catch (ExecuteException executeException) {
                if (watchdog.killedProcess()) {
                    // 执行命令超时，被 watchdog 故意杀死
                    log.error("执行命令超时,超时时间: {}", timeout);
                }
            } catch (IOException e) {
                log.error("执行命令IO异常: ", e);
                throw new RuntimeException(e);
            }
            // 执行命令成功，将结果返回
            commandResul = successStream.toString(Charset.defaultCharset());
            log.info("串行执行命令结束: {}", commandResul);
            return commandResul;
        }
        return commandResul;
    }
}
