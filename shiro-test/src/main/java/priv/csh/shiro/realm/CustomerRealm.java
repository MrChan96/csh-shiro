package priv.csh.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *     Description:学习自定义Realm进行认证与授权，使用CustomerRealmTest进行测试
 *
 * </p>
 *
 * @author chensihao
 * @date: 2020/03/06 2030
 */
public class CustomerRealm extends AuthorizingRealm {

    /**
     * 构造数据
     */
    Map<String,String> userMap = new HashMap<String, String>(16);
    {
        userMap.put("Mark","123456");

        super.setName("customRealm");
    }

    /**
     * 该方法用于授权
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String userName = (String) principals.getPrimaryPrincipal();
        // 从数据库或者缓存中获取角色数据
        Set<String> roles = getRoleByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);

        return simpleAuthorizationInfo;
    }

    /**
     * 设置角色权限
     * @param userName
     * @return
     */
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:update");
        return sets;
    }

    /**
     *  设置角色
     * @param userName
     * @return
     */
    private Set<String> getRoleByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    /**
     * 该方法用于认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //1、从主体传过来的认证信息中，获得用户名
        String userName = (String) token.getPrincipal();

        //2、通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(userName);

        //3、判断密码是否存在
        if(password == null){
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo
                = new SimpleAuthenticationInfo("Mark",password,"customRealm");


        return simpleAuthenticationInfo;
    }

    /**
     * 模拟数据库
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {

        // 由于只是测试，模拟获取数据库值
        return userMap.get(userName);
    }
}
