package com.miller.service.framework.db.mybatis;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.InputStream;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * MyBatisPlus配置
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/29 15:42:31
 */
@Slf4j
@Data
public class MyBatisPlusConfig {
    // Mapper 接口所在的包
    private String packageName = "com.miller";
    private String mapperPath = "mapper";
    private MybatisConfiguration configuration;
    private GlobalConfig globalConfig;
    private Environment environment;
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 获取SqlSession
     *
     * @param dataSource 数据源
     * @return SqlSession
     */
    public SqlSession getSqlSession(DataSource dataSource, Class clazz) {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 这是mybatis-plus的配置对象，对mybatis的Configuration进行增强
        configuration = new MybatisConfiguration();
        // 开启驼峰大小写转换
        configuration.setMapUnderscoreToCamelCase(true);
        // 配置添加数据自动返回数据主键
        configuration.setUseGeneratedKeys(true);
        // 配置日志实现
        configuration.setLogImpl(Slf4jImpl.class);
        // 构建mybatis-plus需要的 globalconfig
        globalConfig = GlobalConfigUtils.getGlobalConfig(configuration);
        //此参数会自动生成实现baseMapper的基础方法映射
        globalConfig.setSqlInjector(new DefaultSqlInjector());
        globalConfig.getDbConfig().setIdType(IdType.AUTO);

        // 设置超类mapper
        globalConfig.setSuperMapperClass(BaseMapper.class);
        // 设置数据源
        environment = new Environment("test", new JdbcTransactionFactory(), dataSource);
        configuration.setEnvironment(environment);
        try {
            this.registryMapperXml(configuration, mapperPath, clazz);
        } catch (IOException e) {
            log.error("解析mapper.xml文件失败", e);
            throw new RuntimeException(e);
        }
        // 这是初始化连接器，如mybatis-plus的分页插件
        configuration.addInterceptor(initInterceptor());

        // 扫描mapper接口所在包。这行代码需要放在其他配置之后，设置后就会扫描 mapper 其他配置就不生效了
        configuration.addMappers(packageName);

        //构建sqlSessionFactory
        sqlSessionFactory = builder.build(configuration);
        // 创建session, 并且设置自动提交，这样不用 sqlSession.commit();
        return sqlSessionFactory.openSession(true);
    }


    /**
     * 初始化拦截器
     *
     * @return Interceptor
     */
    private Interceptor initInterceptor() {
        //创建mybatis-plus插件对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //构建分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    /**
     * 解析mapper.xml文件
     *
     * @param configuration MybatisConfiguration
     * @param classPath     文件路径
     */
    private void registryMapperXml(MybatisConfiguration configuration, String classPath, Class clazz) throws IOException {
        // TODO 修改为从类路径获取
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> mapper = contextClassLoader.getResources(classPath);
        while (mapper.hasMoreElements()) {
            URL url = mapper.nextElement();
            if (url.getProtocol().equals("file")) {
                String path = url.getPath();
                File file = new File(path);
                File[] files = file.listFiles();
                for (File f : files) {
                    FileInputStream in = new FileInputStream(f);
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(in, configuration, f.getPath(), configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                    in.close();
                }
            } else {
                // 打包需要开启以下代码
                // jar包处理
//                try {
//                    java.net.JarURLConnection urlConnection = (java.net.JarURLConnection) url.openConnection();
//                    java.util.jar.JarFile jarFile = urlConnection.getJarFile();
//                    java.util.Enumeration<java.util.jar.JarEntry> entries = jarFile.entries();
//                    while (entries.hasMoreElements()) {
//                        java.util.jar.JarEntry jarEntry = entries.nextElement();
//                        if (jarEntry.getName().startsWith(classPath) && jarEntry.getName().endsWith(".xml")) {
//                            InputStream in = jarFile.getInputStream(jarEntry);
//                            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(in, configuration, jarEntry.getName(), configuration.getSqlFragments());
//                            xmlMapperBuilder.parse();
//                            in.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    log.error("解析JAR包中的mapper.xml文件失败", e);
//                }
            }

        }
    }

}
