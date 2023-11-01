package com.miller.demo.login.response;

import lombok.Data;

/**
 * 登录_响应对象
 *
 * <p>
 * 可借助 IDEA 插件"GsonFormatPlus"自动将json字符串生成java类。<br>
 * 命名规则: 接口名+ResponseDTO， 比如: /user/login 命名为{@link LoginResponseDTO}
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 10:14:12
 */
@Data
public class LoginResponseDTO {
    private Integer code;
    private String message;
    /**
     * data 是一个复杂结构，包含 User 对象和字符串 token
     */
    private UserInfo data;

    /**
     * 建议设置为非静态的内部类，防止数据公用产生的混乱。
     * 如果设置为非 static class 使用时则需要先 new 外部类，内部类生命周期依赖外部类，外部类生命周期结束回收自动回收内部类。
     * 实例化非 static 类方法: {@code ResponseUser.UserInfo.User user = new ResponseUser().new UserInfo().new User();}
     * 设置为 public static class  则可以直接创建内部类对象实例，可以把静态内部类当做一个单独类对待。
     * 实例化 static User类方法: {@code ResponseUser.UserInfo.User user = new ResponseUser.UserInfo.User();}
     */
    @Data
    public class UserInfo {
        /**
         * 这里引用的 User 对象可以通过项目依赖加载进来的，这样可以保持 DTO 对象与后端一直，保证了数据的一致性，后端修改自动更新。
         * 之所以没有引用后端的仅仅是为了测试多层内部类嵌套。
         */
        public User user;
        public String token;

        @Data
        @Deprecated
        public class User {
            private String userId;
            private String name;
            private String email;
            private String password;
            private String status;
            private String createTime;
            private String updateTime;
            private String remark;
        }
    }
}
