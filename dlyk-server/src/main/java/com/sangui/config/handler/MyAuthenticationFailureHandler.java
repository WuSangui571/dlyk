package com.sangui.config.handler;

import com.sangui.result.R;
import com.sangui.util.JSONUtils;
import com.sangui.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description: 登录失败的处理器
 * @Version: 1.0
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //登录失败，执行该方法，在该方法中返回json给前端，就行了
        //登录失败的统一结果
        R result = R.FAIL(exception.getMessage());

        //把R对象转成json
        String resultJSON = JSONUtils.toJSON(result);

        //把R以json返回给前端
        ResponseUtils.write(response, resultJSON);
    }
}
