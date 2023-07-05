package com.hotel_booking_app.Config;

import com.hotel_booking_app.Handler.SecurityAuthenticationFailureHandler;
import com.hotel_booking_app.Handler.SecurityAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfiguration {
    @Autowired
    private SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler;

    @Autowired
    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize-> {
            try {
                authorize
                        // 放行登录接口 放行get，但是不放行post
                        .requestMatchers("login","logout","/").permitAll()
                        // 放行资源目录 有点奇怪
                        .requestMatchers("/templates/**","/login.html").permitAll()
                        // 其余的都需要权限校验
                        .anyRequest().authenticated();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })
            .formLogin(Customizer.withDefaults()
            )
            .csrf(AbstractHttpConfigurer::disable)

        ;
        try {
            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().dispatcherTypeMatchers();
    }
}
