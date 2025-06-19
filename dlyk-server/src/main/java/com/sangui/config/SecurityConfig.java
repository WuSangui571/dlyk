package com.sangui.config;


import com.sangui.config.filter.TokenVerifyFilter;
import com.sangui.config.handler.MyAuthenticationFailureHandler;
import com.sangui.config.handler.MyAuthenticationSuccessHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.sangui.constant.Constants.LOGIN_URI;


/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description: Security 配置文件
 * @Version: 1.0
 */
@Configuration
public class SecurityConfig {
    // 在 user 表中，登录账号的属性名
    private final String NAME_OF_USERNAME_IN_USER = "loginAct";
    // 在 user 表中，密码的属性名
    private final String NAME_OF_PASSWORD_IN_USER = "loginPwd";

    @Resource
    TokenVerifyFilter tokenVerifyFilter;

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource corsConfigurationSource, TokenVerifyFilter tokenVerifyFilter) throws Exception {
        // 禁用跨站请求伪造
        return httpSecurity.formLogin((formLogin)->{
            formLogin.loginProcessingUrl(LOGIN_URI)
                    .usernameParameter(NAME_OF_USERNAME_IN_USER)
                    .passwordParameter(NAME_OF_PASSWORD_IN_USER)
                    .successHandler(myAuthenticationSuccessHandler)
                    .failureHandler(myAuthenticationFailureHandler);
        })
        .authorizeHttpRequests((authorize)->{
            // 任何请求都需要登录后才能访问，除了 LOGIN_URI = "/api/login"
            authorize.requestMatchers(LOGIN_URI).permitAll()
                    .anyRequest().authenticated();
        })
        // 方法引用 禁用跨站请求伪造
        .csrf(AbstractHttpConfigurer::disable)
        // 支持跨域请求
        .cors((cors) ->{
            cors.configurationSource(corsConfigurationSource);
        })

        .sessionManagement((session) ->{
            // Session 创建策略，无 Session 状态
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        })

        // 添加自定义 Filter
        .addFilterBefore(tokenVerifyFilter, LogoutFilter.class)

        .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许任何来源，http://localhost:8080
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        // 运行任何方式，post,get,delete,put
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        // 设置运行的请求头
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }
}
