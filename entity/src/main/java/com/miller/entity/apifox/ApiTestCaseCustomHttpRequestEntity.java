package com.miller.entity.apifox;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

@Data
@Accessors(chain = true)
@TableName("api_test_case_custom_http_requests")
public class ApiTestCaseCustomHttpRequestEntity {
    /** 主键ID */
    @TableId
    private Integer id;
    /** 名称 */
    private String name;
    /** 请求方法 */
    private String method;
    /** 请求路径 */
    private String path;
    /** 请求体 */
    private String requestBody;
    /** 请求参数 */
    private String parameters;
    /** 公共参数 */
    private String commonParameters;
    /** 前置处理器 */
    private String preProcessors;
    /** 后置处理器 */
    private String postProcessors;
    /** 认证信息 */
    private String auth;
    /** 高级设置 */
    private String advancedSettings;
    /** 项目ID */
    private Integer projectId;
    /** 关联ID */
    private Integer relatedId;
    /** 创建人ID */
    private Integer creatorId;
    /** 更新人ID */
    private Integer editorId;
    /** 创建时间 */
    private java.time.LocalDateTime createdAt;
    /** 更新时间 */
    private java.time.LocalDateTime updatedAt;
    /** 删除时间 */
    private java.time.LocalDateTime deletedAt;
    /** 安全方案 */
    private String securityScheme;

    public JSONArray getPostProcessors() {
        if (ObjectUtils.isEmpty(this.postProcessors) || this.postProcessors.equals("[]")){
            return new JSONArray();
        }
        return JSONArray.parseArray(postProcessors);
    }


    public JSONArray getPreProcessors() {
        if (ObjectUtils.isEmpty(this.preProcessors) || this.preProcessors.equals("[]")){
            return new JSONArray();
        }
        return JSONArray.parseArray(preProcessors);
    }
}
