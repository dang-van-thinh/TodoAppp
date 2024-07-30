package com.td.todoapp.configruation;

import com.td.todoapp.services.servicesImp.AuthService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    private AuthService authService;

    // CONFIG MỚI NHẤT CHO SPRING BOOT 3 , SPRING SECURITY 6

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(cors -> cors.disable())// csrf dùng cho chống tấn công giả mạo
                .authorizeHttpRequests(request ->
                                request.requestMatchers("auth/**").permitAll()
                                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/users/works")
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }


    // lưu thông tin account
//    @Autowired
//    private AuthService authService;
//    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
//    }




}
