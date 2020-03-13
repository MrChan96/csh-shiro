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

    // 授权 用户访问页面时候默认会到该授权方法里面
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 授权给用户有权访问页面
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 获取当前用户登录的信息
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal(); //User(id=2, name=zhangsan, pwd=283538989cef48f3d7d8a1c1bdf2008f, perms=user:update)

        //设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());

        return info;
    }

    // 认证  用户登录到时候默认会到该认证方法里面
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取登录的token
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        //连接真实数据库
        User user = userService.queryUserByName(userToken.getUsername());

        if (user == null) { //没有此人
            return null;//抛出UnknownAccountException
        }

        //密码认证 shiro内部操作 SimpleAuthenticationInfo方法第一个参数 principal 可保存用户登录的资源，存入user
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPwd(), "");

        // 去盐
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));

        return simpleAuthenticationInfo;
    }
}
