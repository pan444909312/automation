package com.miller.config;

import com.miller.security.JWTFilter;
import com.miller.security.MyCredentialsMatcher;
import com.miller.security.ShiroRealm;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 通过Shiro和JWT完成权限认证
 *
 * @author Miller Shan
 * @since 2024/12/26 20:58:37
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Autowired
    private ShiroRealm shiroRealm;

    @Autowired
    private MyCredentialsMatcher myCredentialsMatcher;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        // 确保 CORS 过滤器在 Shiro 过滤器之前执行
        Map<String, Filter> filters = new HashMap<>();
        // 可以添加自定义过滤器

        factoryBean.setFilters(filters);

        // 定义过滤器链
        Map<String, String> filterMap = new HashMap<>();

        // 放行 OPTIONS 请求（预检请求）
        filterMap.put("/**/options/**", "anon"); // 放行所有 OPTIONS 请求

        // 放行跨域接口（根据实际需求调整）
        filterMap.put("/api/**", "anon"); // 示例：放行 /api 开头的接口

        // 其他路径需要认证（目前全部放行）
//        filterMap.put("/**", "authc");

        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // 也可以在这里定义过滤器链
        return chainDefinition;
    }
    /**
     * 添加对注解的支持，使用默认的自动代理
     *
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 创建 DefaultWebSecurityManager
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 添加自定义密码验证器
        shiroRealm.setCredentialsMatcher(myCredentialsMatcher);
        // 将Realm注入到默认的web安全管理器中
        defaultWebSecurityManager.setRealm(shiroRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 创建 ShiroFilterFactoryBean
     * Shiro内置过滤器，可以实现权限相关的拦截器, 常用的过滤器:
     * anon: 无需认证（登录）可以访问
     * authc: 必须认证才可以访问
     * user: 如果使用rememberMe的功能可以直接访问
     * perms： 该资源必须得到资源权限才可以访问
     * role: 该资源必须得到角色权限才可以访问
     *
     * @param defaultWebSecurityManager DefaultWebSecurityManager
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager, JWTFilter jwtFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        // 添加自定义的过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", jwtFilter);
        shiroFilterFactoryBean.setFilters(filterMap);

        // 添加过滤条件
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //anon是系统自带的过滤器,表明无需登录验证，过滤器执行时机是从上至下,
        filterChainDefinitionMap.put("/anonymous/**", "anon");
        // 请求为 swagger 的无需登录
        filterChainDefinitionMap.put("/swagger-ui/**", "anon");
        filterChainDefinitionMap.put("/v3/api-docs/**", "anon");

        // 所有的请求都走jwt 过滤器进行拦截处理，但是如果代码里面未添加对应的权限控制注解，相当于不拦截了
        filterChainDefinitionMap.put("/**", "jwt");

        // 如果认证失败则跳转到自己定义的login
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/login");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 开启支持AOP注解，将权限注入到Spring容器中管理
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 向 Spring 容器注入自定义的 Bean, Spring 会对标记为 @Bean 的方法只调用一次，因此返回的Bean仍然是单例。
     * 将 JwtFilter 注入到Spring容器管理中,并且这个过滤器需要放在下面
     *
     * @return JwtFilter
     */
    @Bean
    public JWTFilter getJwtFilter() {
        log.info("注入自己的JWTFilter过滤器");
        return new JWTFilter();
    }
}
