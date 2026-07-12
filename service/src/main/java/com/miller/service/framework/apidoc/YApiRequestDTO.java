package com.miller.service.framework.apidoc;

import lombok.Data;

import java.util.List;

/**
 * 请求传输对象
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/7 12:30:58
 */
@Data
public class YApiRequestDTO {
    private String token, title, status, desc, markdown, id;
    private List<String> tag;
}
