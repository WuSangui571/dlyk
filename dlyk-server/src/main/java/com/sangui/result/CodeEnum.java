package com.sangui.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sangui
 */
@Getter
@AllArgsConstructor
public enum CodeEnum {
    OK(200,"成功"),
    FAIL(500,"失败"),
    TOKEN_IS_EMPTY(901,"登录请求 Token 为空"),
    TOKEN_IS_ERROR(902,"登录请求 Token 有误"),
    TOKEN_IS_EXPIRED(903,"登录请求 Token 已过期"),
    TOKEN_IS_NONE_MATCH(904,"登录请求 Token 不匹配")
    ;
    // 结果码
    private int code;

    // 结果信息
    private String msg;
}
