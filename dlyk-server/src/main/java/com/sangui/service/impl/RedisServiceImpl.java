package com.sangui.service.impl;

import com.sangui.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description: RedisServiceImpl
 * @Version: 1.0
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void removeValue(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void expire(String key, Long timeOut, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeOut, timeUnit);
    }
}
