package com.sangui.config;


import com.sangui.config.filter.TokenVerifyFilter;
import com.sangui.config.handler.MyAuthenticationFailureHandler;
import com.sangui.config.handler.MyAuthenticationSuccessHandler;
import com.sangui.config.handler.MyLogoutSuccessHandler;
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
import java.util.List;


/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description: Security 配置文件
 * @Version: 1.0
 */
@Configuration
public class SecurityConfig {
    // 静态文本，后端处理登录的登录地址
    public static final String LOGIN_URI = "/api/login";
    // 静态文本，后端处理退出登录的地址
    private static final String LOGOUT_URI = "/api/logout";
    // 静态文本，是在 tUser 实体类中，登录账号的属性名
    private static final String NAME_OF_USERNAME_IN_TUSER = "loginAct";
    // 静态文本，是在 tUser 实体类中，密码的属性名
    private static final String NAME_OF_PASSWORD_IN_TUSER = "loginPwd";


    @Resource
    TokenVerifyFilter tokenVerifyFilter;

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    // 将加密器纳入 IoC 容器
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 返回默认的 BCrypt 加密器
        return new BCryptPasswordEncoder();
    }

    // 将 corsConfigurationSource 纳入 IoC 容器
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许任何来源，http://localhost:8080
        corsConfiguration.setAllowedOrigins(List.of("*"));
        // 运行任何方式，post,get,delete,put
        corsConfiguration.setAllowedMethods(List.of("*"));
        // 设置运行的请求头
        corsConfiguration.setAllowedHeaders(List.of("*"));

        // 选择 UrlBasedCorsConfigurationSource 作为返回的具体实现
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 设置该 source 的配置，并且它的匹配路径是多级目录
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource corsConfigurationSource, TokenVerifyFilter tokenVerifyFilter) throws Exception {
        // 禁用跨站请求伪造
        return httpSecurity
                .formLogin((formLogin)->{
                    // 设置后端处理登录的登录地址
                    formLogin.loginProcessingUrl(LOGIN_URI)
                            // 设置用户名的参数名
                            .usernameParameter(NAME_OF_USERNAME_IN_TUSER)
                            // 设置密码的参数名
                            .passwordParameter(NAME_OF_PASSWORD_IN_TUSER)
                            // 设置登录成功后的 Handler
                            .successHandler(myAuthenticationSuccessHandler)
                            // 设置登录失败后的 Handler
                            .failureHandler(myAuthenticationFailureHandler);

        })
                // 对除登录以外的请求进行拦截
                .authorizeHttpRequests((authorizeHttpRequests)->{
                    authorizeHttpRequests
                            // 除了 LOGIN_URI = "/api/login" 这个地址
                            .requestMatchers(LOGIN_URI).permitAll()
                            // 任何请求都需要登录后才能访问
                            .anyRequest().authenticated();
                })
                // 禁用跨站伪造请求
                .csrf(AbstractHttpConfigurer::disable)

                // 支持跨域请求
                .cors((cors) -> {
                    cors.configurationSource(corsConfigurationSource);
                })

                // 设置禁用 Session
                .sessionManagement((sessionManagement) ->{
                    // Session 创建策略，设置为无 Session 状态
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })

                // 添加自定义 Filter
                .addFilterBefore(tokenVerifyFilter, LogoutFilter.class)

                // 退出登录
                .logout((logout) ->{
                    logout.logoutUrl(LOGOUT_URI)
                            .logoutSuccessHandler(myLogoutSuccessHandler);
                })

                .build();
    }
}
