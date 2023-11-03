package com.miller.demo.issuesv2.request;

import lombok.Data;

/**
 * 缺陷_请求实体类。
 * 这里的请求参数对象也可以用后端的，但是可能会出现请求参数不对应的情况，比如有些请求携带了一些加密数据放在请求体中，
 * 所以需要单独自定义，也可以通过继承后端的实体类进行扩展.
 * <p>
 * 命名规则: 首先考虑与后端对应，其次与数据库表字段对应，但不会完全对应。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 11:57:00
 */
@Data
public class IssueRequestDTO {
    private String issueId;
    private String title;
    private String description;
    /**
     * 默认项目ID: c0c42460-5945-464b-b8da-f869e979ca28
     */
    private String projectId = "";
    /**
     * 缺陷处理人
     */
    private String handler;

}
