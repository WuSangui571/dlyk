package com.sangui.config.handler;


import com.sangui.model.TUser;
import com.sangui.result.R;
import com.sangui.util.JSONUtils;
import com.sangui.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description: 登录成功的处理器
 * @Version: 1.0
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //登录成功，执行该方法，在该方法中返回json给前端，就行了
        TUser tUser = (TUser) authentication.getPrincipal();

        //登录成功的统一结果
        R result = R.OK(tUser);

        //把R对象转成json
        String resultJSON = JSONUtils.toJSON(result);

        //把R以json返回给前端
        ResponseUtils.write(response, resultJSON);
    }
}

