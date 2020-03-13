package priv.csh.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.csh.config.UserRealm;
import priv.csh.service.UserServiceImpl;

@Controller
public class MyController {

    /**
     * 创建访问首页路径,首页不止一个，可以按照以下的写法
     */
    @RequestMapping({"/", "/index", "index.html"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "hello");
        return "index"; //跳转到index页面
    }

    @RequestMapping("/user/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update() {
        return "user/update";
    }


    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username,String password,Model model){
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录的信息
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        //执行登录
        try{
            subject.login(token);
            return "index";
        }catch (UnknownAccountException e){//用户名不存在
            model.addAttribute("msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){ //密码不存在
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }


    /**
     * 测试方法，测试查询数据库user的方法
     */

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/getUser")
    public String GetUser(){
        System.out.println(userService.queryUserByName("lisi"));//User(id=2, name=zhangsan, pwd=283538989cef48f3d7d8a1c1bdf2008f, perms=user:update)

        return null;
    }


    /**
     * 提示页面：提示用户该页面未授权
     */
    @RequestMapping("/noauth")
    @ResponseBody
    public String unautorized(){
        return "未经授权无法访问此页面";
    }
}
