package com.sangui.result;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: sangui
 * @CreateTime: 2025-05-11
 * @Description: 统一封装 web 层向前端页面返回的结果，即 VO（View Object）
 * @Version: 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class R {
    //表示返回的结果码，比如200成功，500失败
    private int code;

    //表示返回的结果信息，比如 用户登录状态失效了，请求数格式有误......
    private String msg;

    //表示返回的结果数据，数据可能是一个对象，也可以是一个int集合.....
    private Object data;

    public static R OK(Object data) {
        return R.builder()
                .code(CodeEnum.OK.getCode())
                .msg(CodeEnum.OK.getMsg())
                .data(data)
                .build();
    }

    public static R FAIL() {
        return R.builder()
                .code(CodeEnum.FAIL.getCode())
                .msg(CodeEnum.FAIL.getMsg())
                .build();
    }

    public static R FAIL(String msg) {
        return R.builder()
                .code(CodeEnum.FAIL.getCode())
                .msg(msg)
                .build();
    }

    public static R FAIL(CodeEnum codeEnum) {
        return R.builder()
                .code(codeEnum.getCode())
                .msg(codeEnum.getMsg())
                .build();
    }
}
