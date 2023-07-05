package com.hotel_booking_app.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping(path = "/")
    public String index(){
        return "index";
    }

    //这个login表示动作，不应该返回login页面，应该返回成功或失败
    @GetMapping(path = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(path = "/logout")
    //@PreAuthorize("hasAuthority('YourController:YourMethod')")
    public String logout(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         Model model){
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        return "logout";
    }
}
