package com.miller.service.framework.util;

import org.apache.commons.exec.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 命令行工具类单元测试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/8 11:10:47
 */
class CommandUtilsTest {

    @Test
    @DisplayName("Execute command")
    void testExecCommand() throws IOException {
        String command = "echo execute command in terminal";
        //接收正常结果流
        ByteArrayOutputStream susStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(susStream, errStream);
        exec.setStreamHandler(streamHandler);
        int code = exec.execute(commandLine);
        System.out.println("result code: " + code);
        // 不同操作系统注意编码，否则结果乱码
        String suc = susStream.toString(Charset.defaultCharset());
        String err = errStream.toString(Charset.defaultCharset());
        System.out.println(suc);
        System.out.println(err);
    }

    @Test
    @DisplayName("Execute command asynchronously")
    public void execAsync() throws IOException {
        String command = "echo async execute command in terminal";
        //接收正常结果流
        ByteArrayOutputStream susStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(susStream, errStream);
        exec.setStreamHandler(streamHandler);
        // 异步执行
        ExecuteResultHandler executeResultHandler = new ExecuteResultHandler() {
            @Override
            public void onProcessComplete(int exitValue) {
                String suc = susStream.toString(Charset.defaultCharset());
                System.out.println(suc);
                System.out.println("3. 异步执行完成");
            }

            @Override
            public void onProcessFailed(ExecuteException e) {
                String err = errStream.toString(Charset.defaultCharset());
                System.out.println(err);
                System.out.println("3. 异步执行出错");
            }
        };
        System.out.println("1. 开始执行");
        exec.execute(commandLine, executeResultHandler);
        System.out.println("2. 做其他操作");
    }

    @Test
    @DisplayName("Execute command timeout ")
    void testExecCommandTimeout() throws IOException {
        String command = "ping localhost";
        ByteArrayOutputStream susStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        //设置一分钟超时
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
        exec.setWatchdog(watchdog);
        PumpStreamHandler streamHandler = new PumpStreamHandler(susStream, errStream);
        exec.setStreamHandler(streamHandler);
        try {
            int code = exec.execute(commandLine);
            System.out.println("result code: " + code);
            // 不同操作系统注意编码，否则结果乱码
            String suc = susStream.toString(Charset.defaultCharset());
            String err = errStream.toString(Charset.defaultCharset());
            System.out.println(suc + err);
        } catch (ExecuteException e) {
            if (watchdog.killedProcess()) {
                // 被watchdog故意杀死
                System.err.println("超时了");
            }
        }
    }

    @Test
    @DisplayName("Execute command by CommandUtils")
    void testExecCommandByCommandUtils() throws InterruptedException {
        String echoExecuteCommandInTerminal =
                CommandUtils.executeCommand("echo execute command in terminal", 60 * 1000, Boolean.FALSE);
        assertThat(echoExecuteCommandInTerminal).isNotNull().isNotEmpty();


        CommandUtils.executeCommand("ping localhost", 10 * 1000, Boolean.TRUE);
    }
}


