package com.hotel_booking_app.Config;

import com.hotel_booking_app.Filter.JwtAuthenticationFilter;
import com.hotel_booking_app.Handler.SecurityAuthenticationFailureHandler;
import com.hotel_booking_app.Handler.SecurityAuthenticationSuccessHandler;
import com.hotel_booking_app.Service.MovieUserDetailsService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    MovieUserDetailsService movieUserDetailsService;

    @Autowired
    private MovieAuthenticationEntryPoint movieAuthenticationEntryPoint;

    @Resource
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize-> {
            try {
                authorize
                        // 放行登录接口 放行get，但是不放行post
                        .requestMatchers("login","/","/register").permitAll()

                        // 放行资源目录 有点奇怪 奇怪点在于好像没有放行成功
                        .requestMatchers("/templates/**","/login.html").permitAll()

                        // 其余的都需要权限校验
                        .anyRequest().authenticated();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })
            .formLogin(form->form
                    .successHandler(new SecurityAuthenticationSuccessHandler())
                    .failureHandler(new SecurityAuthenticationFailureHandler())
                    //设置默认的的登录路径，即使指定了路径，实际上也只是制定了名字呢，实际的验证操作依然由Spring Security自行完成
                    .loginProcessingUrl("/doLogin")
                    //登录成功之后跳转的路径
                    //.successForwardUrl("/index")
            )
                //这行代码好像没有起作用？？？既然没有起到应有的作用那么还是删了好
            .csrf(AbstractHttpConfigurer::disable)
            .userDetailsService(movieUserDetailsService)
                // 更新密码...意义不明
                .passwordManagement((management) -> management
                        .changePasswordPage("/update-password")
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint(movieAuthenticationEntryPoint)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        //禁用Session
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        try {
            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private DataSource dataSource;
    @Bean
    UserDetailsManager users(DataSource dataSource) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //String result = encoder.encode("myPassword");

        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}"+encoder.encode("abcd"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}"+encoder.encode("1234"))
                .roles("USER", "ADMIN")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        //users.createUser(user);
        //users.createUser(admin);
        return users;
    }
    //会让我密码验证不通过的诡异代码
//    @Bean(name = "passwordEncoder")
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
