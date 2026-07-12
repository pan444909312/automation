package com.miller.service.framework.apidoc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * YApi 接口信息，映射YApi接口的所有数据
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/7 13:30:58
 */
@NoArgsConstructor
@Data
public class YApiAPIInfoDTO {
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("query_path")
        private QueryPathDTO queryPath;
        @JsonProperty("edit_uid")
        private Integer editUid;
        @JsonProperty("status")
        private String status;
        @JsonProperty("type")
        private String type;
        @JsonProperty("req_body_is_json_schema")
        private Boolean reqBodyIsJsonSchema;
        @JsonProperty("res_body_is_json_schema")
        private Boolean resBodyIsJsonSchema;
        @JsonProperty("api_opened")
        private Boolean apiOpened;
        @JsonProperty("index")
        private Integer index;
        @JsonProperty("tag")
        private List<String> tag;
        @JsonProperty("_id")
        private Integer id;
        @JsonProperty("method")
        private String method;
        @JsonProperty("title")
        private String title;
        @JsonProperty("path")
        private String path;
        @JsonProperty("req_params")
        private List<?> reqParams;
        @JsonProperty("req_body_form")
        private List<?> reqBodyForm;
        @JsonProperty("req_headers")
        private List<ReqHeadersDTO> reqHeaders;
        @JsonProperty("req_query")
        private List<?> reqQuery;
        @JsonProperty("req_body_type")
        private String reqBodyType;
        @JsonProperty("res_body_type")
        private String resBodyType;
        @JsonProperty("res_body")
        private String resBody;
        @JsonProperty("req_body_other")
        private String reqBodyOther;
        @JsonProperty("project_id")
        private Integer projectId;
        @JsonProperty("catid")
        private Integer catid;
        @JsonProperty("uid")
        private Integer uid;
        @JsonProperty("add_time")
        private Integer addTime;
        @JsonProperty("up_time")
        private Integer upTime;
        @JsonProperty("__v")
        private Integer v;
        @JsonProperty("markdown")
        private String markdown;
        @JsonProperty("desc")
        private String desc;
        @JsonProperty("username")
        private String username;

        @NoArgsConstructor
        @Data
        public static class QueryPathDTO {
            @JsonProperty("path")
            private String path;
            @JsonProperty("params")
            private List<?> params;
        }

        @NoArgsConstructor
        @Data
        public static class ReqHeadersDTO {
            @JsonProperty("required")
            private String required;
            @JsonProperty("_id")
            private String id;
            @JsonProperty("name")
            private String name;
            @JsonProperty("value")
            private String value;
        }
    }
}
