package com.miller.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class OssUtils {

    private String endpoint = "https://oss-eu-central-1.aliyuncs.com";
    private String key = "LTAI5t8pE4PZiGq7vNkhSeqs";
    private String keySecret = "XRtxv9q9pT9a2ClyFGCnbK6RVVyiBQ";
    private String staticUrl = "https://static.hungrypanda.co/";
    private String bucketApp = "panda-img";
    private String basePath = "crm/";


    public String uploadToOSS(byte[] content, String fileName) {
        String uri = basePath + fileName;

        String url = "";
        OSS client = new OSSClientBuilder().build(endpoint, key, keySecret);

        try {
            if (!client.doesBucketExist(bucketApp)) {
                client.createBucket(bucketApp);
            }
            PutObjectRequest putPub = new PutObjectRequest(bucketApp, uri, new ByteArrayInputStream(content));

            client.putObject(putPub);
            url = staticUrl + uri;

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            client.shutdown();
        }

        return url;
    }



}
