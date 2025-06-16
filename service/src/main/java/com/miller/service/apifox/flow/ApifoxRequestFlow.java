package com.miller.service.apifox.flow;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.miller.service.framework.http.HttpUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;


public class ApifoxRequestFlow {

    private Map<String, Object> headers = new HashMap<>();
    private Map<Integer,String> teamUsers;


    public ApifoxRequestFlow() {
        headers.put("Host", "apifox.hungrypanda.it");
        headers.put("sec-ch-ua", "Not?A_Brand;v=8, Chromium;v=108");
        headers.put("x-client-mode", "desktop");
        headers.put("accept-language", "zh-CN");
        headers.put("x-client-version", "2.7.2");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzQ1MDQyLCJ0cyI6ImZmMDUyNWVjMWViMWY2ZWIiLCJpYXQiOjE3MTk1NzAxMTk1OTF9.ZH2NnxpckfpgNbrw4k1AJxt7Wc7hPqu2QKmU71ngbSg");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) apifox-pdv/2.7.2 Chrome/108.0.5359.215 Electron/22.3.5 Safari/537.36");
        headers.put("access-control-allow-origin", "*");
        headers.put("x-branch-id", "308306");
        headers.put("x-project-id", "345145");
        headers.put("x-device-id", "e3ca1e216378367f51e60b98783844f412bb52f0868d90cff13517758b99c390");
        headers.put("sec-ch-ua-platform", "macOS");
        headers.put("accept", "*/*");
        headers.put("sec-fetch-site", "cross-site");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-dest", "empty");
    }

    public JSONArray getTestScenarioTreeList() throws Exception {
        String url = "https://apifox.hungrypanda.it/api/v1/projects/345145/test-scenario/tree-list?locale=zh-CN";
        Map<String, Object> resultMap = HttpUtils.sendGetRequest(url, null, headers, null);
        if (ObjectUtils.isEmpty(resultMap)) {
            throw new Exception("查询用例场景异常：".concat(JSONObject.toJSONString(resultMap)));
        }
        JSONObject resultJson = new JSONObject(resultMap);
        return resultJson.getJSONObject("body")
                .getJSONObject("body")
                .getJSONObject("data")
                .getJSONArray("testScenarios");
    }

    public JSONArray getCaseStep(Integer caseId) throws Exception {
        String url = "https://apifox.hungrypanda.it/api/v1/api-test/cases/" + caseId + "/steps?withCaseDetail=true&locale=zh-CN";
        Map<String, Object> resultMap = HttpUtils.sendGetRequest(url, null, headers, null);
        if (ObjectUtils.isEmpty(resultMap)) {
            throw new Exception("查询场景步骤异常：".concat(JSONObject.toJSONString(resultMap)));
        }
        JSONObject resultJson = new JSONObject(resultMap);
        return resultJson.getJSONObject("body")
                .getJSONObject("body")
                .getJSONObject("data")
                .getJSONArray("steps");
    }

    public Map<Integer,String> getTeamUsers() throws Exception {
        String url = "https://apifox.hungrypanda.it/api/v1/teams/71/members?locale=zh-CN";
        Map<String, Object> resultMap = HttpUtils.sendGetRequest(url, null, headers, null);
        if (ObjectUtils.isEmpty(resultMap)) {
            throw new Exception("查询团队成员异常：".concat(JSONObject.toJSONString(resultMap)));
        }
        JSONArray data = new JSONObject(resultMap)
                .getJSONObject("body")
                .getJSONObject("body")
                .getJSONArray("data");
        Map<Integer,String> teamUsers = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject userInfo = data.getJSONObject(i);
            final Integer userId = userInfo.getInteger("userId");
            String nickname = userInfo.getString("nickname");
            String[] splits = nickname.split("_");
            nickname = splits.length > 0 ? splits[splits.length - 1] : nickname;
            teamUsers.put(userId, nickname);
        }
        return teamUsers;
    }

    public String getUserNick(Integer userId) throws Exception {
        if (ObjectUtils.isEmpty(this.teamUsers) || this.teamUsers.isEmpty()) {
            this.teamUsers = this.getTeamUsers();
        }

        return this.teamUsers.get(userId);
    }


}
