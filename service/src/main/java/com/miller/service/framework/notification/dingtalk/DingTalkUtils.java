package com.miller.service.framework.notification.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 钉钉工具类。调用钉钉 Open API 完成与钉钉的交互
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/03/20
 */
@Slf4j
public class DingTalkUtils {


    /**
     * 机器人应用的 access_token 的值
     */
    public static final String CUSTOM_ROBOT_TOKEN = "121a18c07ba54967e437533ea2492e8dd25b6af0448140e487b703412f6574b1";
    /**
     * 测试token
     */
    public static final String CUSTOM_ROBOT_TOKEN_TEST = "0de7c87ac7e3e8506c142d1929068a9c15feea710152aca9c2eeb1e9aa3202ff";


    /**
     * 钉钉机器人管理中的 安全设置，加签密钥
     */
    public static final String SECRET = "SEC59acb673a2582ff2546c73be8694083cce839cd2eb1293cd062b24f0a0a73a67";
    /**
     * 测试secret
     */
    public static final String SECRET_TEST = "SECcc2d9a57ff038735ba738a2325e5623749e3016cbe931d930b8ce7253f265854";


    public static final String CUSTOM_ROBOT_TOKEN_DEBUG = "4dca2c4d767b7e2aae74c79383d542728916002bd9fb2e4051fb33306d138a15";

    public static final String SECRET_DEBUG = "SECd2f6f14f01915297b58f5b39ee0ecc09c68c677366d6058c9df8e039566d6a32";

    /**
     * 可选参数。内部群：可以填写用户的 UserId 实现 @ 能力
     */
    public static final String USER_ID = "<you need @ group user's userId>";
    /**
     * 可选参数。内部群：可以填写用户的 手机号 实现 @ 能力
     */
    public static final String MOBILE_NUMBER = "< mobile number>";

    public static void sendTextMessage(String content) {
        sendTextMessage(content, CUSTOM_ROBOT_TOKEN, SECRET);
    }

    public static void sendTextMessageTest(String content) {
        sendTextMessage(content, CUSTOM_ROBOT_TOKEN_TEST, SECRET_TEST);
    }

    public static void sendMarkdownMessage(String title, String content) {
        sendMarkdownMessage(title, content, CUSTOM_ROBOT_TOKEN, SECRET);
    }

    public static void sendMarkdownMessageTest(String title, String content) {
        sendMarkdownMessage(title, content, CUSTOM_ROBOT_TOKEN_TEST, SECRET_TEST);
    }

    public static void sendMarkdownMessageDebug(String title, String content) {
        sendMarkdownMessage(title, content, CUSTOM_ROBOT_TOKEN_DEBUG, SECRET_DEBUG);
    }


    /**
     * 发送文本消息
     *
     * @param content 文本内容
     */
    private static void sendTextMessage(String content, String token, String secret) {
        try {
            Long timestamp = System.currentTimeMillis();
//            String secret = SECRET;
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            // System.out.println(sign);

            //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?sign=" + sign + "&timestamp=" + timestamp);
            OapiRobotSendRequest req = new OapiRobotSendRequest();
            /**
             * 发送文本消息
             */
            //定义文本内容
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(content);
            //定义 @ 对象
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtUserIds(Arrays.asList(USER_ID));
            //设置消息类型
            req.setMsgtype("text");
            req.setText(text);
            req.setAt(at);
            OapiRobotSendResponse rsp = client.execute(req, token);
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            log.error("钉钉发送消息失败", e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            log.error("编码错误", e);
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            log.error("加密错误", e);
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            log.error("验证key错误", e);
            throw new RuntimeException(e);
        }
        try {
            // 每个机器人每分钟最多发送20条消息到群里，如果超过20条，会限流10分钟。
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
        } catch (InterruptedException e) {
            log.error("线程休眠错误", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送 Markdown 消息，Markdown 语法支持有限
     *
     * @param title   标题
     * @param content 正文
     * @see <a href="https://open.dingtalk.com/document/orgapp/enterprise-internal-robots-send-markdown-messages#title-w87-omz-3es">代码示例</a>
     * @see <a href="https://open.dingtalk.com/document/orgapp/custom-bot-send-message-type">custom-bot-send-message-type</a>
     */
    public static void sendMarkdownMessage(String title, String content, String token, String secret) {
        try {
            Long timestamp = System.currentTimeMillis();
//            String secret = SECRET;
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            // System.out.println(sign);

            //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?sign=" + sign + "&timestamp=" + timestamp);

            /**
             * 发送Marddown消息
             */
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle(title);
            markdown.setText(content);

            //定义 @ 对象
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(Arrays.asList(MOBILE_NUMBER));
            at.setAtUserIds(Arrays.asList(USER_ID));
            at.setIsAtAll(Boolean.FALSE);

            // 设置消息类型
            request.setMsgtype("markdown");
            request.setMarkdown(markdown);
            request.setAt(at);
            // 发送消息
            OapiRobotSendResponse response = client.execute(request, token);
            if (response.getErrcode() != 0) System.err.println("发送钉钉推送消息失败:" + response.getBody());
            // System.out.println(rsp.getBody());
        } catch (ApiException e) {
            log.error("钉钉发送消息失败", e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            log.error("编码错误", e);
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            log.error("加密错误", e);
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            log.error("验证key错误", e);
            throw new RuntimeException(e);
        }
        try {
            // 每个机器人每分钟最多发送20条消息到群里，如果超过20条，会限流10分钟。
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
        } catch (InterruptedException e) {
            log.error("线程休眠错误", e);
            throw new RuntimeException(e);
        }
    }

    public static void senOaMessage(
            String title,
            List<OapiMessageCorpconversationAsyncsendV2Request.Form> formList,
            OapiMessageCorpconversationAsyncsendV2Request.Rich rich,
            String token,
            String secret
    ) {


        try {
            Long timestamp = System.currentTimeMillis();
//            String secret = SECRET;
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");

            //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?sign=" + sign + "&timestamp=" + timestamp);

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();

            OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();

            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            request.setToAllUser(true);

            OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
            if (ObjectUtils.isEmpty(formList)) {
                formList = new ArrayList<>();
            }
            body.setForm(formList);

            // 富文本数据
            if (ObjectUtils.isEmpty(rich)) {
                rich = new  OapiMessageCorpconversationAsyncsendV2Request.Rich();
            }
            body.setRich(rich);

            OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
            head.setText("");

            body.setTitle(title);
            oa.setHead(head);
            oa.setBody(body);
            msg.setOa(oa);
            msg.setMsgtype("oa");
            request.setMsg(msg);
            OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, token);
            System.out.println(rsp.getBody());


        } catch (ApiException e) {
            log.error("钉钉发送消息失败", e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            log.error("编码错误", e);
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            log.error("加密错误", e);
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            log.error("验证key错误", e);
            throw new RuntimeException(e);
        }
        try {
            // 每个机器人每分钟最多发送20条消息到群里，如果超过20条，会限流10分钟。
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
        } catch (InterruptedException e) {
            log.error("线程休眠错误", e);
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        sendTextMessage("测试消息请忽略。");
        var content = "标题\n" +
                "# 一级标题\n" +
                "## 二级标题\n" +
                "### 三级标题\n" +
                "#### 四级标题\n" +
                "##### 五级标题\n" +
                "###### 六级标题\n" +
                " \n" +
                "引用\n" +
                "> A man who stands for nothing will fall for anything.\n" +
                " \n" +
                "文字加粗、斜体\n" +
                "**bold**\n" +
                "*italic*\n" +
                " \n" +
                "链接\n" +
                "[this is a link](https://www.dingtalk.com/)\n" +
                " \n" +
                "图片\n" +
                "![](https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png)\n" +
                " \n" +
                "无序列表\n" +
                "- item1\n" +
                "- item2\n" +
                " \n" +
                "有序列表\n" +
                "1. item1\n" +
                "2. item2\n" +
                "\n" +
                "换行(建议\\n前后各添加两个空格)\n" +
                "  \\n  ";
        sendMarkdownMessage("自动测试报告", content);
    }
}
