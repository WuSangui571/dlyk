package com.sangui.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description:
 * @Version: 1.0
 */
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain springSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.formLogin((formLogin)->{
            formLogin.loginProcessingUrl("/api/login")
                    .usernameParameter("loginAct")
                    .passwordParameter("loginPwd")
                    .successHandler()
                    .failureHandler()
        })
        .authorizeHttpRequests((authorize)->{
            // 任何请求都需要登录后才能访问
            authorize.anyRequest().authenticated();
        })
        .build();
    }
}
