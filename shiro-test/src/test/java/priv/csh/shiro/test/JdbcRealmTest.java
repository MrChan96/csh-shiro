package priv.csh.shiro.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * <p>
 *     Description:测试JdbcRealm
 *     数据库用户角色表和角色权限表的字段需要与JdbcRealm定义的字段保持一致，详情见文档
 *
 * </p>
 *
 * @author chensihao
 * @date: 2020/03/06 20:25
 */
public class JdbcRealmTest {

    // 9、定义数据源
    DruidDataSource dataSource =  new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }


    @Test
    public void testAuthentication(){

        // 3、使用JdbcRealm
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        // 9、设置权限开启状态
        jdbcRealm.setPermissionsLookupEnabled(true);


        // 10、自定义sql语句:用户表
        String sql = "select password from test_user where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        // 11、自定义sql语句:用户角色表
        String roleSql = "select password from test_user_role where user_name = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        // 12、自定义sql语句:用户角色权限表
        String permissionSql = "select password from table_name where xxxx = ?";
        jdbcRealm.setPermissionsQuery(permissionSql);


        // 1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);



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
        // 若报错：Subject does not have permission [xxxx]    注意权限开启状态
        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");




    }

}
