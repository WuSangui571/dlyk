package com.sangui.service.impl;

import com.sangui.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description: RedisService 具体的实现类
 * @Version: 1.0
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 在 Redis 中存数据
     * @param key Redis 中的 key
     * @param value 存入的值
     */
    @Override
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 在 Redis 中取指定 key 的数据
     * @param key Redis 中的 key
     * @return 取到的数据
     */
    @Override
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除在 Redis 中指定 key 的数据
     * @param key Redis 中的 key
     */
    @Override
    public void removeValue(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置 Redis 中指定 key 的自动过期时间
     * @param key Redis 中的 key
     * @param timeOut 设置过期时间的数值，一般默认是秒为单位
     * @param timeUnit 设置 timeOut 的单位，一般是秒，即：TimeUnit.SECONDS
     */
    @Override
    public void expire(String key, Long timeOut, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeOut, timeUnit);
    }
}
