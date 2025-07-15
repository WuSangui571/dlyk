package com.sangui.query;


import lombok.Data;

/**
 * @Author: sangui
 * @CreateTime: 2025-07-15
 * @Description: 父类，也是 Query，让其他 Query 继承我这个类
 * @Version: 1.0
 */
@Data
public class BaseQuery {
    /**
     * 也就是 jwt
     */
    private String token;
}
