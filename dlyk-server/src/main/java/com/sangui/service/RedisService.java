package com.sangui.service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: sangui
 * @CreateTime: 2025-06-15
 * @Description: RedisService
 * @Version: 1.0
 */
public interface RedisService {
    void setValue(String key, Object value);

    Object getValue(String key);

    void removeValue(String key);

    void expire(String key, Long timeOut, TimeUnit timeUnit);
}
