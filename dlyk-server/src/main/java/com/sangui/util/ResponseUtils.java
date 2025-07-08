package com.sangui.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: sangui
 * @CreateTime: 2025-05-11
 * @Description: 响应工具类
 * @Version: 1.0
 */
public class ResponseUtils {

    /**
     * 使用 response，把结果写出到前端
     * @param response HttpServletResponse
     * @param result 返回数据，是由 Java 对象转化而成的 json
     */
    public static void write(HttpServletResponse response, String result) {
        // 设置响应的格式为 json，设置字符集为 UTF-8
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
