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
    FAIL(500,"失败")
    ;
    // 结果码
    private int code;

    // 结果信息
    private String msg;
}
