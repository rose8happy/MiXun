package com.hotel_booking_app.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_booking_app.Pojo.User;
import com.hotel_booking_app.Util.JwtUtils;
import com.hotel_booking_app.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
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
    @Autowired
    private DataSource dataSource;

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
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if(users.userExists(username)){
            result.put("code", -1);
            result.put("msg", "existed the username");
            return new ObjectMapper().writeValueAsString(result);
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            //User user1 = new User(username, "{bcrypt}"+encoder.encode(password));
            //int id = userMapper.insertUser(user1);
            //userMapper.insertAuthoritie(username,"ROLE_USER");

            UserDetails newUser = org.springframework.security.core.userdetails.User.builder()
                    .username(username)
                    .password("{bcrypt}"+encoder.encode(password))
                    .roles("USER")
                    .build();
            users.createUser(newUser);

            result.put("code", 0);
            result.put("msg", "register successfully");
            return new ObjectMapper().writeValueAsString(result);
        }
    }

    @Resource
    JwtUtils jwtUtils = new JwtUtils();
    @GetMapping("/showUsername")
    public String showUsername(String token){
        String username = jwtUtils.parseToken(token);
        return username;
    }
}
