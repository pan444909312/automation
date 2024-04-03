package com.miller.pos.dto;


import com.panda.common.base.Result;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础_响应实体类
 *
 * @author Miller Shan
 * @version 1.0
 * @see com.panda.common.base.AppResult
 * @since 2023/12/6 18:15:26
 */
@Data
public class BasicResponseDTO<T> extends Result<T> implements Serializable {
}
