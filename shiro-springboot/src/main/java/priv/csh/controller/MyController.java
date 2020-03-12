package priv.csh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
