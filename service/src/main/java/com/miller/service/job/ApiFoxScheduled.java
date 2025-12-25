package com.miller.service.job;

import com.miller.service.apifox.ApiFoxConfigService;
import com.miller.service.apifox.ApiFoxRunReportService;
import com.miller.service.apifox.ApifoxToolsService;
import com.miller.service.apifox.enums.AttributionGroupEnum;
import com.miller.service.enums.ApiFoxEnvEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ApiFox 定时任务
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/11/6 10:23:44
 */
@Component
public class ApiFoxScheduled {

    @Autowired
    private ApifoxToolsService apifoxToolsService;

    @Autowired
    private ApiFoxConfigService apiFoxConfigService;

    @Autowired
    private ApiFoxRunReportService runReportService;

    @Scheduled(cron = "0 10 1 * * ?")
    public void scheduledTaskB() {
        this.scheduledTask(AttributionGroupEnum.B);
    }

    @Scheduled(cron = "0 10 3 * * ?")
    public void scheduledTaskP() {
        this.scheduledTask(AttributionGroupEnum.P);
    }


    @Scheduled(cron = "0 10 4 * * ?")
    public void scheduledTaskD() {
        this.scheduledTask(AttributionGroupEnum.D);
    }


    /**
     * 异步执行
     */
    @Async
    public void scheduledTaskAsync(AttributionGroupEnum attributionGroupEnum) {
        this.scheduledTask(attributionGroupEnum);
    }

    public void scheduledTask(AttributionGroupEnum attributionGroupEnum) {

        // 获取当前时间并格式化输出
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String format = sdf.format(new Date());

        // 根据小组获取对应的执行命令配置
        String groupConfig = apiFoxConfigService.getGroupConfig(attributionGroupEnum);
        groupConfig = groupConfig.replace("{{date}}", format);

        if (ObjectUtils.isEmpty(groupConfig)){
            throw new RuntimeException("无法查询到 ApiFox config 配置信息：".concat(attributionGroupEnum.toString()));
        }

        int executeShell = JavaShellUtil.executeShell(groupConfig);

        // 避免执行的时候，响应报告文件还没创建，导致报错。
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.apifoxToolsService.parsingReport(attributionGroupEnum);
    }

    @Async
    public void scheduledTask(String taskId){
        // 根据小组获取对应的执行命令配置
        String groupConfig = apiFoxConfigService.getGroupConfig(AttributionGroupEnum.DEBUG);
        groupConfig = groupConfig.replace("{{taskId}}", taskId);

        final String belongingGroup = runReportService.queryBelongingGroup(Long.valueOf(taskId));
        if (belongingGroup.equals("D")){
            groupConfig = groupConfig.replace("{{envId}}", ApiFoxEnvEnum.D_ENV.getEnvId());
        }else{
            groupConfig = groupConfig.replace("{{envId}}", ApiFoxEnvEnum.TEST_ENV.getEnvId());
        }

         JavaShellUtil.executeShell(groupConfig);
    }


    /**
     * ApiFox Cli shell 命令执行方法
     */
    public static class JavaShellUtil {
        // 基本路径
        private static final String basePath = "./";

        // 记录Shell执行状况的日志文件的位置(绝对路径)
        private static final String executeShellLogFile = basePath + "executeShell.log";

        // 发送文件到系统的Shell的文件名(绝对路径)
        //        private static final String sendShellName = basePath + "test.sh";
        public static int executeShell(String shellCommand) {
            System.out.println("shellCommand:" + shellCommand);
            int success = 0;
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = null;
            BufferedReader stdError = null;
            // 格式化日期时间，记录日志时使用
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS ");

            try {
                stringBuffer.append(dateFormat.format(new Date())).append("准备执行Shell命令 ").append(shellCommand).append(" \r\n");

                Process pid = null;
                String[] cmd = {"/bin/sh", "-c", shellCommand};
                // 执行Shell命令
                pid = Runtime.getRuntime().exec(cmd);
                if (pid != null) {
                    stringBuffer.append("进程号：").append(pid.toString()).append("\r\n");

                    // bufferedReader用于读取Shell的输出内容
                    bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream()));
                    //读到标准出错的信息
                    stdError = new BufferedReader(new InputStreamReader(pid.getErrorStream()));
                    //这个是或得脚本执行的返回值
                    int status = pid.waitFor();


                    //如果脚本执行的返回值不是0,则表示脚本执行失败，否则（值为0）脚本执行成功。
                    if (status != 0) {
                        stringBuffer.append("shell脚本执行失败！");
                    } else {
                        stringBuffer.append("shell脚本执行成功！");
                    }
                } else {
                    stringBuffer.append("没有pid\r\n");
                }
                stringBuffer.append(dateFormat.format(new Date())).append("Shell命令执行完毕\r\n执行结果为：\r\n");

                //将标准输入流上面的内容写到stringBuffer里面
                String line = null;
                // 读取Shell的输出内容，并添加到stringBuffer中
                while (bufferedReader != null && (line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line).append("\r\n");
                }

                //将标准输入流上面的内容写到stringBuffer里面
                String line1 = null;
                while (stdError != null && (line1 = stdError.readLine()) != null) {
                    stringBuffer.append(line1).append("\r\n");
                }
            } catch (Exception ioe) {
                stringBuffer.append("执行Shell命令时发生异常：\r\n").append(ioe.getMessage()).append("\r\n");
            } finally {
                if (bufferedReader != null) {
                    OutputStreamWriter outputStreamWriter = null;

                    try {
                        bufferedReader.close();
                        // 将Shell的执行情况输出到日志文件中
                        OutputStream outputStream = new FileOutputStream(executeShellLogFile);
                        outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                        outputStreamWriter.write(stringBuffer.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            outputStreamWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                success = 1;
            }
            return success;
        }

    }
}
