package priv.csh.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 *     Description:一、简单的shiro认证和授权：用户信息校验、权限授权
 *
 * </p>
 *
 * @author chensihao
 * @date: 2020/03/06 19:39
 */
public class AuthenticationTest {

    /**
     * 使用SimpleAccountRealm设置静态缓存校验信息
     */
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("Mark","123456","admin");
    }


    @Test
    public void testAuthentication(){

        // 1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        // 3、引入需要验证的缓存数据
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // 4、设置用户进行登录的用户名密码
        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");

        // 5、登录
        subject.login(token);

        // 6、校验用户登录信息
        System.out.println("isAuthenticated:"+subject.isAuthenticated()); //若匹配成功，返回true

        // 7、校验用户角色
        subject.checkRole("admin");


        // 8、检验多个角色
        //subject.checkRoles("","");

    }
}
