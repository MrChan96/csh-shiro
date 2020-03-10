package priv.csh.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import priv.csh.shiro.realm.CustomerRealm;

/**
 * <p>
 *     Description:学习使用自定义Realm
 *
 * </p>
 *
 * @author chensihao
 * @date: 2020/03/06 2059
 */
public class CustomRealmTest {


    @Test
    public void testAuthentication(){


        // 3、引入自定义Realm对象
        CustomerRealm customerRealm = new CustomerRealm();

        // 1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customerRealm);

        // 9、加密 ：给登录用户进行加密，使其
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5"); //加密算法名称
        hashedCredentialsMatcher.setHashIterations(1); //加密次数
        customerRealm.setCredentialsMatcher(hashedCredentialsMatcher); // 添加加密对象

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
        //subject.checkRole("admin");


        // 8、检验多个角色
        //subject.checkPermission("user:delete");

    }
}
