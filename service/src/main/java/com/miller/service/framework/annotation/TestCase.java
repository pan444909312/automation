package com.miller.service.framework.annotation;

/**
 * 自动化测试用例管理
 *
 * <p>
 * 目的：自动化测试人员编写测试用例应该在当前自己的开发环境中能独立完成，并同步到统一的用例管理平台。
 * <p>
 * 自动化测试用例与手工测试用例是属于两种不同的用例类型，所以应该分开编写，但是用例的管理应该统一。
 * 在通常的情况下“手工测试用例”编写一般会以文字的方式编写，自动化测试用例通常是通过代码进行编写，两种测试用例开发的环境可能不同。
 * 为了提高自动化测试用例开发效率，在编写自动化测试用例时应该专注在自己当前的IDE环境中。
 * 通过“测试框架”统一处理“自动化测试用例”与平台用例库中的对应关系，这样可以实现用例的统一管理，分开编写，最终实现去中心化。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/05 20:08:26
 */
public @interface TestCase {

    /**
     * 自动化测试用例ID，使用ULID工具生成唯一ID。
     */
    String testCaseID();

    /**
     * TAPD 中的测试用例唯一ID
     */
    long TAPD_ID() default 0;

    /**
     * 测试用例标题
     */
    String name();

    /**
     * 用例状态：enum('updating','abandon','normal')
     */
    String status() default "normal";

    /**
     * 用例类型:功能测试；性能测试；安全性测试；其他
     */
    String type() default "功能测试";

    /**
     *用例等级：高；中；低
     */
    String priority() default "中";

    /**
     * 用例创建人
     */
    String creator() default "";

    /**
     * 用例目录。默认值：未规划目录，需要注意区分项目，所以不作为公共注解，放在各自项目中处理。
     */
    // String categoryId() default "1136883525001008383";
}
