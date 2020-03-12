package priv.csh.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        //用户名、密码 数据库中取
        String name = "root";
        String password = "123456";

        //获取登录的token
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        //登录信息与数据库信息进行匹配
        if(!userToken.getUsername().equals(name)){
            return null; //抛出UnknownAccountException
        }
        //密码认证 shiro内部操作
        return new SimpleAuthenticationInfo("",password,"");
    }
}
