package com.hotel_booking_app.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_booking_app.Pojo.User;
import com.hotel_booking_app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @GetMapping(path = "/")
    public String index(){
        return "index";
    }

    //login好像被Spring Security自动处理了，不用管
//    @PostMapping(path = "/doLogin")
//    public String login(){
//        return "1234";
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

    @Autowired
    private UserMapper userMapper;

    @PostMapping(path = "/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password) throws JsonProcessingException {
        /**
         * 实际上的处理逻辑是
         * 1.检查数据库中有无这个username
         * 2.没有则进行注册操作
         * 3.注册完返回注册成功json
         */
        //return username+" "+password;
        User user = userMapper.getByUserName(username);
        Map<String,Object> result=new HashMap<>();
        if(user != null){
            result.put("code", -1);
            result.put("msg", "existed the username");
            return new ObjectMapper().writeValueAsString(result);
        } else {
            User user1 = new User(username, password);
            //emm设置权限的语法好像有点麻烦，先不设置了
            int id = userMapper.insertUser(user1);
            System.out.println(id);
            result.put("code", 0);
            result.put("msg", "register successfully");
            return new ObjectMapper().writeValueAsString(result);
        }
    }
}
