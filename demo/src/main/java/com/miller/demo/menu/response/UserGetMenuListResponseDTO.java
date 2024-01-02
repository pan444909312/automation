package com.miller.demo.menu.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.miller.demo.dto.external.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 响应对象_菜单栏查询
 * <p>
 * 可借助 IDEA 插件"GsonFormatPlus"自动将json字符串生成java类。<br>
 * 命名规则: 接口名+ResponseDTO， 比如: /user/getMenuList/ 命名为 {@link UserGetMenuListResponseDTO}
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 20:33:32
 */
@NoArgsConstructor
@Data
public class UserGetMenuListResponseDTO {
    private Integer code;
    private String message;
    private ArrayList<MenuList> data;

    @NoArgsConstructor
    @Data
    public static class MenuList {
        @JsonProperty("menu")
        private Permission menu;
        /**
         * 这里引用的 Permission 对象是通过项目依赖加载进来的，这样可以保持 DTO 对象与后端一直，保证了数据的一致性，后端修改自动更新。
         */
        @JsonProperty("subMenu")
        private List<Permission> subMenu;
    }
}
