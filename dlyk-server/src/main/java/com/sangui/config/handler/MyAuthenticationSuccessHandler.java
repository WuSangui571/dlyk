package com.sangui.config.handler;


import com.sangui.constant.Constants;
import com.sangui.model.TUser;
import com.sangui.result.R;
import com.sangui.service.RedisService;
import com.sangui.util.JsonUtils;
import com.sangui.util.JwtUtils;
import com.sangui.util.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description: 登录成功的处理器
 * @Version: 1.0
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private RedisService redisService;

    /**
     * 登录成功，执行该方法，在该方法中返回 json 给前端，就行了
     * @param request the request which caused the successful authentication
     * @param response the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     * the authentication process.
     * @throws IOException IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TUser tUser = (TUser) authentication.getPrincipal();

        // 把 tUser 对象转成 json 作为负载数据放入 jwt
        String userJson = JsonUtils.toJson(tUser);
        String jwt = JwtUtils.createJwt(userJson);

        // 在 Redis 中放入此 jwt
        redisService.setValue(Constants.REDIS_JWT_KEY + tUser.getId(),jwt);

        // 设置 jwt 的过期时间（如果选择了记住我，过期时间是 7 天，否则是 30 min）
        String rememberMe = request.getParameter("rememberMe");
        if (Boolean.parseBoolean(rememberMe)){
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(),Constants.EXPIRE_TIME, TimeUnit.SECONDS);
        }else {
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(),Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        // 登录成功的统一结果
        R result = R.ok(jwt);

        // 把 R 对象转成 json
        String resultJson = JsonUtils.toJson(result);

        // 把 R 以 json 返回给前端
        ResponseUtils.write(response, resultJson);
    }
}

