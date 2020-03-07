package priv.csh.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * <p>
 *     Description:测试IniRealm
 *
 * </p>
 *
 * @author chensihao
 * @date: 2020/03/06 20:20
 */
public class IniRealmTest {

    @Test
    public void testAuthentication(){

        // 3、使用IniRealm
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        // 1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

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

        // 8、校验权限，是否具备delete权限
        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");

    }

}
