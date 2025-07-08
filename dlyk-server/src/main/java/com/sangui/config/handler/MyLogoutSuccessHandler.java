package com.sangui.config.handler;

import com.sangui.constant.Constants;
import com.sangui.model.TUser;
import com.sangui.result.CodeEnum;
import com.sangui.result.R;
import com.sangui.service.RedisService;
import com.sangui.util.JsonUtils;
import com.sangui.util.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description: 退出成功处理器
 * @Version: 1.0
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private RedisService redisService;

    /**
     * 退出成功，执行该方法，在该方法中返回 json 给前端，就行了
     * @param request request
     * @param response response
     * @param authentication authentication
     * @throws IOException IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 取出 authentication 里的 tUser
        TUser tUser = (TUser)authentication.getPrincipal();

        // 删除一下 Redis 中用户的 jwt
        redisService.removeValue(Constants.REDIS_JWT_KEY + tUser.getId());

        // 退出成功的统一结果
        R result = R.ok(CodeEnum.USER_LOGOUT);

        // 把 R 对象转成 json
        String resultJson = JsonUtils.toJson(result);

        // 把 R 以 json 返回给前端
        ResponseUtils.write(response, resultJson);
    }
}
