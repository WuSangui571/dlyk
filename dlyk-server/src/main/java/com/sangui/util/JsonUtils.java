package com.sangui.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: sangui
 * @CreateTime: 2025-05-11
 * @Description: 由 jackson 实现的 json 工具类。可进行 json 和 Java 对象的双向转换
 * @Version: 1.0
 */
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 把 java 对象转成 json
     *
     * @param object 待转化为 json 的对象
     * @return 转化好的 json
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把 json 转成 Java 对象
     *
     * @param json 待转化为 Java 对象的 json
     * @param clazz 想要转化的具体 Java 对象的类
     * @return 转化好的 Java 对象
     * @param <T> 想要转化的具体 Java 对象
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
