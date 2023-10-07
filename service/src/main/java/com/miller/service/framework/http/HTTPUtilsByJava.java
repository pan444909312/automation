package com.miller.service.framework.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 基于Java原生包实现HTTP请求
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023-10-2 19:24:55
 */
public class HTTPUtilsByJava extends AbstractHTTPUtils {


    public static void main(String[] args) throws IOException {
        //testSendGetMethod();
        //testSendPostMethod();
    }

    public static void testSendPostMethod() throws IOException {
        String protocol = "https://";
        String host = "restapi.amap.com";
        Integer defaultPort = 443;
        String path = "/v4/geofence/meta";
        String queryParams = "?" + "key=b47b41b40ce3083eed8be7789df7f0cf";

        URL url = null;
        if (Objects.isNull(defaultPort)) {
            url = new URL(protocol + host + defaultPort + path + queryParams);
        } else {
            url = new URL(protocol + host + ":" + defaultPort + path + queryParams);
        }
        // 使用代理方式建立连接
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8087));
        // 打开和URL之间的连接
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // 设置通用请求属性
        httpURLConnection.setRequestProperty("Accept", "*/*");
        httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
        httpURLConnection.setRequestProperty("Connection", "keep-alive");

        // 设置要发送的请求方法
        httpURLConnection.setRequestMethod("POST");
        // POST方法必须设置如下
        httpURLConnection.setDoOutput(true);

        // 建立连接
        httpURLConnection.connect();

        // 发送数据：请求参数。POST请求主要是这里不同，这里需要携带body到服务器
        // 获取URLConnection对象对应的输出流
        OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream(), StandardCharsets.UTF_8);
        String body = "{\n" + "    \"name\": \"测试围栏名称\",\n" + "    \"center\": \"115.672126,38.817129\",\n" + "    \"radius\": \"1000\",\n" + "    \"enable\": \"true\",\n" + "    \"valid_time\": \"2020-05-19\",\n" + "    \"repeat\": \"Mon,Tues,Wed,Thur,Fri,Sat,Sun\",\n" + "    \"time\": \"00:00,11:59;13:00,20:59\",\n" + "    \"desc\": \"测试围栏描述\",\n" + "    \"alert_condition\": \"enter;leave\"\n" + "}";
        out.write(body);
        // flush输出流的缓冲
        out.flush();

        // 获取响应体内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        int result;
        StringBuilder stringBuilder = new StringBuilder();
        while (-1 != (result = bufferedReader.read())) {
            stringBuilder.append((char) result);
        }
        bufferedReader.close();
        inputStream.close();
        System.out.println("=============================>响应体内容:" + stringBuilder.toString());

        // 获取响应头内容
        Map<String, List<String>> headers = httpURLConnection.getHeaderFields();
        for (String key : headers.keySet()) {
            System.out.println("=============================>响应头内容: Key=" + key + ", value=" + headers.get(key));
        }

        // 关闭连接
        httpURLConnection.disconnect();
    }

    public static void testSendGetMethod() throws IOException {
        String protocol = "https://";
        String host = "restapi.amap.com";
        Integer defaultPort = 443;
        String path = "/v3/weather/weatherInfo";
        String queryParams = "?" + "key=b47b41b40ce3083eed8be7789df7f0cf&city=330100&extensions=all&output=json";
        URL url = null;
        if (Objects.isNull(defaultPort)) {
            url = new URL(protocol + host + defaultPort + path + queryParams);
        } else {
            url = new URL(protocol + host + ":" + defaultPort + path + queryParams);
        }

        System.out.println("protocol:" + url.getProtocol());
        System.out.println("host:" + url.getHost());
        System.out.println("port:" + url.getPort());                    // 这里获取的端口号是URL上写的端口号，没写则获取的是-1
        System.out.println("defaultPort:" + url.getDefaultPort());      // 应用的默认端口号
        System.out.println("path:" + url.getPath());
        System.out.println("query:" + url.getQuery());


        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // 设置通用请求属性
        httpURLConnection.setRequestProperty("Accept", "*/*");
        httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
        httpURLConnection.setRequestProperty("Connection", "keep-alive");
        //        httpURLConnection.setUseCaches(false);


        // 设置请求方法
        httpURLConnection.setRequestMethod("GET");

        // 建立连接
        httpURLConnection.connect();

        // 获取响应体内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        int result;
        StringBuilder stringBuilder = new StringBuilder();
        while (-1 != (result = bufferedReader.read())) {
            stringBuilder.append((char) result);
        }
        bufferedReader.close();
        inputStream.close();
        System.out.println("=============================>响应体内容:" + stringBuilder.toString());

        // 获取响应头内容
        Map<String, List<String>> headers = httpURLConnection.getHeaderFields();
        for (String key : headers.keySet()) {
            System.out.println("=============================>响应头内容: Key=" + key + ", value=" + headers.get(key));
        }

        // 关闭连接
        httpURLConnection.disconnect();
    }

    @Override
    public Map<String, Object> sendGetRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Map<String, Object> cookies) {
        return null;
    }

    @Override
    public Map<String, Object> sendPostRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        return null;
    }

    @Override
    public Map<String, Object> sendPutRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        return null;
    }

    @Override
    public Map<String, Object> sendDeleteRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        return null;
    }

    @Override
    public Map<String, Object> sendOtherMethodRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, String method) {
        return null;
    }
}
