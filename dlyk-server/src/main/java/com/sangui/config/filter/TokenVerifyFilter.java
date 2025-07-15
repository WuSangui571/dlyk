package com.sangui.config.filter;


import com.sangui.constant.Constants;
import com.sangui.model.TUser;
import com.sangui.result.CodeEnum;
import com.sangui.result.R;
import com.sangui.service.RedisService;
import com.sangui.util.JsonUtils;
import com.sangui.util.JwtUtils;
import com.sangui.util.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.sangui.config.SecurityConfig.LOGIN_URI;


/**
 * @author sangui
 */
@Component
public class TokenVerifyFilter extends OncePerRequestFilter {

    @Resource
    private RedisService redisService;

    // SpringBoot 框架的 IoC 容器中已经创建好了该线程池，可以注入直接使用
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 如果是登录请求，此时还没有生成 jwt，那不需要对登录请求进行 jwt 验证
        if (request.getRequestURI().equals(LOGIN_URI)) {
            //验证 jwt 通过了 ，让 Filter 链继续执行，也就是继续执行下一个 Filter
            filterChain.doFilter(request, response);
        } else {
            String token = null;
            if (request.getRequestURI().equals(Constants.EXPORT_EXCEL_URI)) {
                // 从请求路径的参数中获取 token
                token = request.getParameter("Authorization");
            } else {
                // 其他请求都是从请求头中获取 token
                token = request.getHeader("Authorization");
            }

            if (!StringUtils.hasText(token)) {
                // token 验证未通过的统一结果
                R result = R.fail(CodeEnum.TOKEN_IS_EMPTY);
                // 把 R 对象转成 json
                String resultJson = JsonUtils.toJson(result);
                // 把 R 以 json 返回给前端
                ResponseUtils.write(response, resultJson);
                return;
            }

            // 验证 token 有没有被篡改过
            if (!JwtUtils.verifyJwt(token)) {
                // token 验证未通过统一结果
                R result = R.fail(CodeEnum.TOKEN_IS_ERROR);
                // 把 R 对象转成 json
                String resultJson = JsonUtils.toJson(result);
                //把 R 以 json 返回给前端
                ResponseUtils.write(response, resultJson);
                return;
            }

            TUser tUser = JwtUtils.parseUserFromJWT(token);
            String redisToken = (String) redisService.getValue(Constants.REDIS_JWT_KEY + tUser.getId());

            if (!StringUtils.hasText(redisToken)) {
                // token 验证未通过统一结果
                R result = R.fail(CodeEnum.TOKEN_IS_EXPIRED);

                // 把 R 对象转成 json
                String resultJSON = JsonUtils.toJson(result);

                // 把 R 以 json返回给前端
                ResponseUtils.write(response, resultJSON);

                return;
            }

            if (!token.equals(redisToken)) {
                // token 验证未通过的统一结果
                R result = R.fail(CodeEnum.TOKEN_IS_NONE_MATCH);

                // 把 R 对象转成 json
                String resultJson = JsonUtils.toJson(result);

                // 把 R 以 json 返回给前端
                ResponseUtils.write(response, resultJson);
                return;
            }

            // jwt 验证通过了，那么在 spring security 的上下文环境中要设置一下，设置当前这个人是登录过的，你后续不要再拦截他了
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tUser, tUser.getLoginPwd(), tUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 刷新一下 token（异步处理）
            // 异步处理（更好的方式，使用线程池去执行）
            threadPoolTaskExecutor.execute(() -> {
                // 刷新 token
                String rememberMe = request.getHeader("rememberMe");
                if (Boolean.parseBoolean(rememberMe)) {
                    redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.EXPIRE_TIME, TimeUnit.SECONDS);
                } else {
                    redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
                }
            });

            // 验证 jwt 通过了 ，让 Filter 链继续执行，也就是继续执行下一个 Filter
            filterChain.doFilter(request, response);
        }
    }
}
