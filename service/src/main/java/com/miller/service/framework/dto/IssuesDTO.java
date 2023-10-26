package com.miller.service.framework.dto;

import com.miller.service.framework.depend.DependsOnClassAnnotation;
import lombok.Data;

import java.io.Serializable;


/**
 * 缺陷
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/18 17:22:59
 */
@Data
public class IssuesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String issueId, title, description, status, creator, updateUser, projectId;

    private Long createTime, updateTime;
    /**
     * 缺陷处理人
     */
    private String handler;
}