package priv.csh.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //shiroFilterFactoryBean 3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();

        //给页面授权：当无权限访问该页面是应该跳转到提示页面，提示用户未授权
        filterMap.put("/user/add","perms[user:add]");  // 表示拥有add权限才能否访问user/add页面
        filterMap.put("/user/update","perms[user:update]");

        //若无授权访问页面，需跳转至提示页面
        bean.setUnauthorizedUrl("/noauth");

        //支持通配符的写法，user整个目录下的页面都需要认证
        filterMap.put("/user/*", "authc");

        //若无权限访问页面，需跳转至登录页面
        bean.setLoginUrl("/toLogin");



        bean.setFilterChainDefinitionMap(filterMap);

        return bean;
    }

    //DafaultWebSecurituManager 2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //关联UserRealm
        securityManager.setRealm(userRealm);

        // shiro加密
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5"); //加密算法名称
        hashedCredentialsMatcher.setHashIterations(1); //加密次数
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher); // 添加加密对象

        return securityManager;
    }

    //创建realm对象 ，需要自定义 1
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        return new UserRealm();
    }

    //整合ShiroDialect
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
