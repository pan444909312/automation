package com.miller.service.apifox.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.miller.entity.platform.User;
import com.miller.service.apifox.ApifoxToolsService;
import com.miller.service.platform.UserService;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ApifoxToolsServiceImpl implements ApifoxToolsService {

    private String WEBHOOK_URL = "https://oapi.dingtalk.com/robot/send";


    @Autowired
    private UserService userService;

    /**
     * 一次性代码，apifox 钉钉通知二次包装，实现 @ 指定人
     */
    @Override
    public boolean sendDingDing(String accessToken, String timestamp, String sign, String body) {
        JSONObject jsonBody = JSONObject.parseObject(body);

        // 透传
        DingTalkClient client = new DefaultDingTalkClient(
                WEBHOOK_URL = WEBHOOK_URL
                        .concat("?access_token=").concat(accessToken)
                        .concat("&timestamp=").concat(String.valueOf(timestamp))
                        .concat("&sign=").concat(sign)
        );

        OapiRobotSendRequest req = new OapiRobotSendRequest();
        req.setMsgtype(jsonBody.getString("msgtype"));

        OapiRobotSendRequest.Actioncard actioncard = new OapiRobotSendRequest.Actioncard();
        JSONObject actionCardJson = jsonBody.getJSONObject("actionCard");
        actioncard.setTitle(actionCardJson.getString("title"));
        actioncard.setText(actionCardJson.getString("text"));
        req.setActionCard(actioncard);


        List<OapiRobotSendRequest.Btns> btns = new LinkedList<>();
        JSONArray btnsJson = actionCardJson.getJSONArray("btns");
        JSONObject btnJson = btnsJson.getJSONObject(0);
        OapiRobotSendRequest.Btns btn = new OapiRobotSendRequest.Btns();
        btn.setTitle(btnJson.getString("title"));
        String actionURL = btnJson.getString("actionURL");
        btn.setActionURL(actionURL);
        btns.add(btn);
        actioncard.setBtns(btns);
        req.setActionCard(actioncard);

        try {
            client.execute(req);
        } catch (ApiException e) {
            log.error(e.getErrMsg());
            return false;
        }

        req.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("请及时查看执行结果：".concat(actionURL));
        req.setText(text);

        // Text类型可用：定义钉钉通知到指定成员：
        // 有执行报错才会通知
        String messageContext = actionCardJson.getString("text");
        if (!messageContext.contains("失败数：  <font color=\"#FF0000\">0</font> 个")) {

            // 正则匹配执行人，查找不到默认为：王晓皓
            Pattern compile = Pattern.compile("测试人：QA_(.*?) ");
            Matcher matcher = compile.matcher(messageContext);
            String name = "王晓皓";
            if (matcher.find()) {
                String group = matcher.group(1);
                name = group.split("_")[1];
            }

            if (ObjectUtils.isEmpty(name)) return false;

            User userByName = userService.getUserByName(name);
            if (ObjectUtils.isEmpty(userByName)) return false;

            // 设置 @人
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(List.of(userByName.getMobile()));
            req.setAt(at);

            try {
                client.execute(req);
            } catch (ApiException e) {
                log.error(e.getErrMsg());
                return false;
            }
        }
        return true;
    }






}
