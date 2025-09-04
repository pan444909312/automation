package com.miller.service.dto;

import lombok.Data;

/**
 * XXL config request dto
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/13 10:36:04
 */
@Data
public class XXLConfigRequestDTO {
    private String Env;
    private String key;
    private String title;
    private String value;
}
