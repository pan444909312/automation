package com.miller.mapper;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MyBatisPlus 逆向工程，代码生成器
 * <p>
 * 执行 main 方法控制台输入模块表名回车自动生成对应项目目录中。
 * </p>
 *
 * <p>
 * <ur>
 * <li>
 * 测试环境: 127.0.0.1:3306
 * </li>
 * <li>
 * username:xxx
 * </li>
 * <li>
 * password:xxx
 * </li>
 * </ur>
 * <ur>
 * <li>
 * 测试环境:
 * </li>
 *
 * </ur>
 *
 * </p>
 *
 * @author Miller Shan
 */
public class CodeGenerator {

    /**
     * 包名: 直接使用数据库库名
     */
    private static String packageName = "statistics";
    private static String dbName = "xxx_" + packageName;


    private static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/" + dbName + "?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private static final String username = "xxx";
    // xxxDB密码：RQqedQ&GLW
    private static final String password = "xxx";
    /**
     * 生成代码的目录
     */
    private static final String codePath = "/src/main/java";
    /**
     * 生成代码的父包名，会在这个包目录下生成 entity, controller, mapper, service.
     */
    private static String parentPackage = "com.miller.bean" + packageName;

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in, Charset.defaultCharset().name());
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 读取数据库所有的表名称
     */
    public static String[] getAllTableName(Connection connection) {
        StringBuilder stringBuilder = new StringBuilder();
        DatabaseMetaData databaseMetaData;
        try {
            databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = databaseMetaData.getTables(connection.getCatalog(), null, "%", types);
            while (rs.next()) {
                // System.out.println("table name:" + rs.getString(3));
                stringBuilder.append(rs.getString(3) + ",");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String result = stringBuilder.toString();
        String[] split = result.split(",");
        return split;
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + codePath);
//        gc.setOutputDir("D:\\test");
        gc.setAuthor("Miller Shan");
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置,这里需要改成自己的ip
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(jdbcUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        // 数据库的账号密码自己改一下
        dsc.setUsername(username);
        dsc.setPassword(password);

        mpg.setDataSource(dsc);


        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null);
        pc.setParent(parentPackage);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(Boolean.TRUE);
        strategy.setRestControllerStyle(Boolean.TRUE);
        // 读取控制台输入的表名
        // strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // 读取数据库的所有表名
        strategy.setInclude(getAllTableName(dsc.getConn()));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("m_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}