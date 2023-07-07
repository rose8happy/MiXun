package com.hotel_booking_app.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_booking_app.Util.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    JwtUtils jwtUtils = new JwtUtils();

//    @Autowired
//    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        //System.out.println(username);
        String token = jwtUtils.generateToken(username);//这一步产生了奇怪的错误
        //System.out.println(token);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        Map<String,Object> result=new HashMap<>();
        result.put("code", "0");
        result.put("msg", "login success");
        result.put("username",username);
        result.put("token", token);
        String json = new ObjectMapper().writeValueAsString(result);
        PrintWriter out = response.getWriter();
        out.write(json);
        out.flush();
        out.close();

//        Map<String,Object> result=new HashMap<>();
//        result.put("msg", "登录成功");
//        result.put("status", 200);
//        response.setContentType("application/json;charset=UTF-8");
//        String s = new ObjectMapper().writeValueAsString(result);
//        response.getWriter().println(s);
    }
}

