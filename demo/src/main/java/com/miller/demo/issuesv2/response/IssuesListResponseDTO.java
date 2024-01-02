package com.miller.demo.issuesv2.response;

import com.miller.demo.dto.external.Issues;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 响应对象_查询
 * <p>
 * 可借助 IDEA 插件"GsonFormatPlus"自动将json字符串生成java类。<br>
 * 命名规则: 接口名+ResponseDTO， 比如: /issues/list/ 命名为 {@link IssuesListResponseDTO}.
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 11:59:00
 */
@Data
public class IssuesListResponseDTO {
    private Integer code;
    private String message;
    /**
     * Complex struct of data property, It contains issue records and MyBatis Page parameters.
     */
    private IssueList data;

    @Data
    public class IssueList {
        private String countId;
        private Integer current;
        private Boolean hitCount;
        private String maxLimit;
        private Boolean optimizeCountSql;
        private ArrayList orders;
        private Integer pages;
        private Boolean searchCount;
        private Integer size;
        private Integer total;
        /**
         * 记录集里面是多个查询结果对象。
         * 这里引用的Issues对象是通过项目依赖加载进来的，这样可以保持DTO对象与后端一直，保证了数据的一致性，后端修改自动更新。
         */
        private List<Issues> records;
    }
}
