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
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
        } else if(username == null){
            result.put("code", -2);
            result.put("msg", "username is null!");
            return new ObjectMapper().writeValueAsString(result);
        }
        else {
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

    @PostMapping("/updatePassword")
    public String updatePassword(String username,String old_pwd,String new_pwd){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        //System.out.println(SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().toString());
        // 密码错误失败要返回-1 然后的话没有账号是怎么区分账号的呢……？
        // jwt的filter里面设置了账号，故可以识别账号
        User user = userMapper.getByUserName(username);
        if(Objects.equals(user.getPassword(), "{bcrypt}" + encoder.encode(old_pwd))){
            users.changePassword("{bcrypt}"+encoder.encode(old_pwd),
                    "{bcrypt}"+encoder.encode(new_pwd));
            return "0";
        } else {
            return "-1";
        }
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(String username, String password){
        /**
         * 验证账号密码，正确则注销
         */
        User user = userMapper.getByUserName(username);
        if(Objects.equals(user.getPassword(), "{bcrypt}" + encoder.encode(password))){
            JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
            users.deleteUser(username);
            return "0";
        } else {
            return "-1";
        }

    }
}
