package priv.csh.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import priv.csh.pojo.User;
import priv.csh.service.UserServiceImpl;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserServiceImpl userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取登录的token
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        //连接真实数据库
        User user = userService.queryUserByName(userToken.getUsername());

        if (user == null) { //没有此人
            return null;//抛出UnknownAccountException
        }

        //密码认证 shiro内部操作
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("", user.getPwd(), "");


        return simpleAuthenticationInfo;
    }
}
