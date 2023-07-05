package com.hotel_booking_app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize-> {
            try {
                authorize
                        // 放行登录接口 放行get，但是不放行post
                        .requestMatchers("login","logout").permitAll()
                        // 放行资源目录
                        .requestMatchers("/templates/**", "/resources/**").permitAll()
                        // 其余的都需要权限校验
                        .anyRequest().authenticated();
                        // 防跨站请求伪造
                        //.and().csrf(csrf -> csrf.disable());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })
            .formLogin(
                    formlogin ->
                            formlogin.loginPage("/resources/templates/login.html")
                                    .permitAll());
        try {
            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
