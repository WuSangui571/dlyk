package com.sangui.config.handler;

import com.sangui.result.R;
import com.sangui.util.JsonUtils;
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

    /**
     * 登录失败，执行该方法，在该方法中返回 json 给前端，就行了
     * @param request the request during which the authentication attempt occurred.
     * @param response the response.
     * @param exception the exception which was thrown to reject the authentication
     * request.
     * @throws IOException IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 登录失败的统一结果
        R result = R.fail("失败的异常信息：" + exception.getMessage());

        // 把 R 对象转成 json
        String resultJson = JsonUtils.toJson(result);

        // 把 R 以 json 返回给前端
        ResponseUtils.write(response, resultJson);
    }
}
