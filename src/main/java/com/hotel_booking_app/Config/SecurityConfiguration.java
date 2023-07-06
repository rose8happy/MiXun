package com.hotel_booking_app.Config;

import com.hotel_booking_app.Handler.SecurityAuthenticationFailureHandler;
import com.hotel_booking_app.Handler.SecurityAuthenticationSuccessHandler;
import org.hibernate.dialect.MariaDBDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;


@Configuration
@EnableWebSecurity
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
                        .requestMatchers("login","/").permitAll()
                        // 放行资源目录 有点奇怪
                        .requestMatchers("/templates/**","/login.html").permitAll()
                        // 其余的都需要权限校验
                        .anyRequest().authenticated();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })
            .formLogin(form->form.successHandler(new SecurityAuthenticationSuccessHandler())
            )
            .csrf(AbstractHttpConfigurer::disable)
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

}
