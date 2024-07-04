package com.td.todoapp.configruation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] PUBLIC_PATH_GET = {"/works",
            "/users", "/users/{id}"};
    private final String[] PUBLIC_PATH_POST = {"/works", "/users"};
    private final String[] PUBLIC_PATH_DELETE = {"/users/{id}"};
    private final String[] PUBLIC_PATH_PUT = {"/users/{id}"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.GET, PUBLIC_PATH_GET).permitAll()
                        .requestMatchers(HttpMethod.POST, PUBLIC_PATH_POST).permitAll()
                        .requestMatchers(HttpMethod.DELETE,PUBLIC_PATH_DELETE).permitAll()
                        .requestMatchers(HttpMethod.PUT,PUBLIC_PATH_PUT).permitAll()
                        .anyRequest().authenticated()
        );

        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()); // csrf dùng cho chống tấn công giả mạo
        return http.build();
    }

}
