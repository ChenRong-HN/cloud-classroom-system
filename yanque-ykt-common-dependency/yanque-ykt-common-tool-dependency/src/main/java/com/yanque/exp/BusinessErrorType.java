package com.yanque.exp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义业务异常类型
 *
 * @author cr
 */
@Getter
@AllArgsConstructor
public enum BusinessErrorType {
    USER_NOT_FOUND(501, "用户不存在"),
    USERNAME_IS_NULL(502, "用户名不能为空");

    // 状态码
    private final Integer code;
    // 提示信息
    private final String message;
}
