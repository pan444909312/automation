package com.miller.service.framework.apidoc;

import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * YApi Tools
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/7 11:39:58
 */
public class YApiUtils {
    public static final String Y_API_DOMAIN = "http://10.1.6.46:3000";

    /**
     * 获取能用的token，YApi 不同项目的 token 不一样
     *
     * @param yApiUri YApi 平台的URL地址，从url地址中解析出项目ID，然后找到对应项目ID的token
     * @return 项目token
     */
    private static String getProjectToken(String yApiUri) {
        var projectTokenMap = new HashMap<String, String>();
        /*
         * user-app-server
         */
        projectTokenMap.put("60", "c9dffdce0debec9b674843d6542986d612288fb61f6d3d6b2d32366a795b75b2");
        // 从字符串中切出来ID，比如：/project/60/interface/api/123456
        String[] split = yApiUri.split("/project/");
        String[] projectId = split[1].split("/interface/");
        // 判断YApi项目的Token是否加入到本项目中
        if (projectTokenMap.containsKey(projectId[0])) {
            return projectTokenMap.get(projectId[0]);
        } else {
            throw new ArgumentAccessException(String.format("The project token with id %s must be added.", projectId[0]));
        }
    }

    /**
     * 更新YApi平台的数据
     *
     * @param yApiUri 完整的 YApi url 地址
     */
    public static void updateYApiData(String yApiUri) {
        var uri = Y_API_DOMAIN + "/api/interface/up";
        var yApiRequestDTO = new YApiRequestDTO();
        var yApiAPIInfoDTO = getYApiInfoByID(yApiUri);

        yApiRequestDTO.setToken(getProjectToken(yApiUri));

        // 更新 YApi 接口状态，暂不修改状态
        yApiRequestDTO.setStatus(yApiAPIInfoDTO.getData().getStatus());
        // 更新 YApi 中的备注
        var qaDescription = "<h3><strong>请注意，接口ID请不要变更，此备注也不要修改，可以追加备注</strong>。</h3>\n<p>这是通过自动化测试自动更新的记录，<strong>测试用例执行成功之后会自动追加Tag(\"自动化\")</strong>，表示此接口已经被自动化测试覆盖，<strong>如果更新接口需要通知QA人员</strong>。</p>\n<hr>\n";
        var qaMarkdown = "### **请注意，接口ID请不要变更，此备注也不要修改，可以追加备注**。\n\n这是通过自动化测试自动更新的记录，**测试用例执行成功之后会自动追加Tag(\"自动化\")**，表示此接口已经被自动化测试覆盖，**如果更新接口需要通知QA人员**。\n\n- - -\n\n### #发送GET请求示例\n\n```\ncurl--requestGET'http://127.0.0.1:7080/api/calc/add/1/2'\n#发送POST请求\ncurl-XPOSThttp://127.0.0.1:7080/api/calc/testPostMethod-H\"Content-type:application/json\"-d'{\"firstNumber\":\"1.0\",\"secondNumber\":2.0}'\n```";

        var yApiDesc = yApiAPIInfoDTO.getData().getDesc();
        if (null == yApiDesc || yApiDesc.length() < 1) {
            yApiRequestDTO.setDesc(qaDescription);
            yApiRequestDTO.setMarkdown(qaMarkdown);
        }
        // 如果备注中不包含QA默认的描述，则在备注中添加QA的备注信息
        //if (!yApiDesc.contains(qaDescription)) {
        // 兼容一下 YApi中的备注，防止可能修改了备注，后续考虑用文本相似度
        if (null == yApiDesc || !yApiDesc.contains("自动化测试自动更新")) {
            yApiRequestDTO.setDesc(qaDescription + yApiDesc);
            yApiRequestDTO.setMarkdown(qaMarkdown + yApiDesc);
        }
        yApiRequestDTO.setId(getYApiId(yApiUri));

        /*
        List<String> tag = yApiAPIInfoDTO.getData().getTag();
        if (tag.contains("自动化")) {
            yApiRequestDTO.setTag(tag);
        } else {
            tag.add("自动化");
            yApiRequestDTO.setTag(tag);
        }
        */
        Optional<List<String>> tagList = Optional.of(yApiAPIInfoDTO.getData().getTag());
        tagList.ifPresent((tag) -> {
            if (tag.contains("自动化")) {
                yApiRequestDTO.setTag(tag);
            } else {
                tag.add("自动化");
                yApiRequestDTO.setTag(tag);
            }
        });

        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        HttpUtils.sendPostRequestReturnBody(uri, null, headers, JSONUtils.toJSONString(yApiRequestDTO), null);
    }

    /**
     * 根据url切割出YApi的ID
     *
     * @param url 比如: /project/60/interface/api/3040
     * @return api ID
     */
    public static String getYApiId(String url) {
        return url.split("/interface/api/")[1];
    }

    /**
     * 通过 ID 查询YApi中的接口信息
     *
     * @param yApiUri 完整的 YApi url 地址
     * @return 自己封装的 YApi 实体类，字段不全。{@link YApiAPIInfoDTO}
     */
    public static YApiAPIInfoDTO getYApiInfoByID(String yApiUri) {
        var uri = Y_API_DOMAIN + "/api/interface/get";
        var params = new HashMap<String, Object>();
        params.put("token", getProjectToken(yApiUri));
        params.put("id", getYApiId(yApiUri));
        return HttpUtils.sendGetRequestReturnJavaObject(uri, params, null, null, YApiAPIInfoDTO.class);
    }
}

