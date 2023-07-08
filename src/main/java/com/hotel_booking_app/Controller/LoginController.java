package com.hotel_booking_app.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

@RestController
public class LoginController {

    @GetMapping(path = "/")
    public String index(){
        return "index";
    }

    //这个login表示动作，不应该返回login页面，应该返回成功或失败
//    @PostMapping(path = "/login")
//    public String login(){
//        return "logina";
//    }

    @GetMapping(path = "/logout")
    //@PreAuthorize("hasAuthority('YourController:YourMethod')")
    public String logout(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         Model model){
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        return "logout";
    }

    @PostMapping(path = "/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password){
        /**
         * 实际上的处理逻辑是
         * 1.检查数据库中有无这个username
         * 2.没有则进行注册操作
         * 3.注册完返回注册成功json
         */
        return "register successfully";
    }
}
