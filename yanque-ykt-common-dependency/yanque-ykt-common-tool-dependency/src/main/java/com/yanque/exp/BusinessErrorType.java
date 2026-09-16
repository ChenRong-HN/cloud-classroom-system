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
    // 表示当前业务中不同错误信息的枚举项
    USER_NOT_LOGIN(501, "用户当前处于未登录状态"),
    USER_NOT_EXISTS(502, "用户不存在"),
    PASSWORD_ERROR(503, "用户登录密码错误"),
    MAIL_SEND_COUNT_GT3(505, "今日发送邮件次数已超过限制"),
    MAIL_LAST_SEND_IN_1MIN(506, "邮件发送间隔不能小于1分钟"),
    USER_ACCOUNT_EXISTS(507,"用户已存在"),
    EMAIL_VALIDATE_CODE_EXPIRED(508,"验证码已过期"),
    EMAIL_VALIDATE_CODE_ERROR(509,"验证码有误，请重新输入"),
    REMOTE_SERVICE_ERROR(510, "远程服务调用错误"),
    COURSE_NAME_EXISTS(511, "课程名称已存在"),
    COURSE_TYPE_NOT_EXISTS(512, "课程分类数据不存在"),
    TEACHER_NOT_EXISTS(513, "老师数据不存在"),
    COURSE_TIME_ERROR(514, "课程时间声明错误"),
    ALIYUN_OSS_ERROR(515, "阿里云Oss服务调用异常"),
    ;

    // 状态码
    private final Integer code;
    // 提示信息
    private final String message;
}
