package com.hotel_booking_app.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
//非登陆情况下的访问控制
@Component
public class MovieAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", "-1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            jsonObject.put("msg", "no permited");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String json = objectMapper.writeValueAsString(jsonObject);
        PrintWriter out = response.getWriter();
        out.write(json);
        out.flush();
        out.close();
    }

}