package com.miller.controller.tools.file.service.impl;

import com.miller.controller.tools.file.service.FileToolsService;
import com.miller.util.OssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class FileToolsServiceImpl implements FileToolsService {

    private final Long FILE_MAX_SIZE = 1024L * 1024L * 50L;



    /**
     * 生成文件并上传到OSS
     *
     * @param size       文件大小
     * @param unit       单位（B, KB, MB, GB）
     * @param fillRandom 是否填充随机数据（true：随机，false：填充0）
     * @return 文件URL
     */
    public String generateAndUpload(long size, String unit, boolean fillRandom,String suffix) {


        try {
            // 转换大小为字节
            long sizeInBytes = convertToBytes(size, unit);

            // 生成文件内容
            byte[] content = generateFileContent(sizeInBytes, fillRandom);
            log.info("文件内容生成完成");

            // 上传到OSS
            final String fileName = String.format("%s_%s.%s",size,unit,suffix);
            String fileUrl = new OssUtils().uploadToOSS(content,fileName);
            log.info("文件上传OSS成功");
            return fileUrl;

        } catch (Exception e) {
            throw new RuntimeException("生成并上传文件失败", e);
        }
    }




    /**
     * 生成指定大小的文件内容
     */
    private byte[] generateFileContent(long sizeInBytes, boolean fillRandom) {
        if (sizeInBytes > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("文件大小不能超过 " + Integer.MAX_VALUE + " 字节");
        }

        byte[] content = new byte[(int) sizeInBytes];

        if (fillRandom) {
            new Random().nextBytes(content);
        }
        // 如果fillRandom为false，数组默认就是0，不需要额外操作

        return content;
    }


    /**
     * 大小单位转换
     */
    private long convertToBytes(long size, String unit) {
        if (unit == null) unit = "B";

        Long fileSize = switch (unit.toUpperCase()) {
            case "KB" -> size * 1024;
            case "MB" -> size * 1024 * 1024;
            default -> size; // 默认为字节
        };

        if (fileSize > FILE_MAX_SIZE) {
            throw new IllegalArgumentException(String.format("最大生成文件大小为：%s", FILE_MAX_SIZE));
        }

        return fileSize;
    }



}
