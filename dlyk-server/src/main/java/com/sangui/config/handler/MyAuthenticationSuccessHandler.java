package com.sangui.config.handler;


import com.sangui.constant.Constants;
import com.sangui.model.TUser;
import com.sangui.result.R;
import com.sangui.service.RedisService;
import com.sangui.util.JSONUtils;
import com.sangui.util.JWTUtils;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //登录成功，执行该方法，在该方法中返回json给前端，就行了
        TUser tUser = (TUser) authentication.getPrincipal();

        // 1. 生成 JWT
        // 把 tUser 对象转成 json 作为负载数据放入 jwt
        String userJSON = JSONUtils.toJSON(tUser);
        String jwt = JWTUtils.createJWT(userJSON);

        redisService.setValue(Constants.REDIS_JWT_KEY + tUser.getId(),jwt);

        // 设置 jwt 的过期时间（如果选择了记住我，过期时间是 7 天，否则是 30 min）
        String rememberMe = request.getParameter("rememberMe");
        if (Boolean.parseBoolean(rememberMe)){
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(),Constants.EXPIRE_TIME, TimeUnit.SECONDS);
        }else {
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(),Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        //登录成功的统一结果
        R result = R.OK(jwt);

        //把R对象转成json
        String resultJSON = JSONUtils.toJSON(result);

        //把R以json返回给前端
        ResponseUtils.write(response, resultJSON);
    }
}

