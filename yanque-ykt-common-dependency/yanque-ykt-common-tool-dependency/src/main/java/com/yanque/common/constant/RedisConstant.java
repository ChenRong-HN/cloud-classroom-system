package com.yanque.common.constant;

/**
 * redis从常用字符串抽取为静态常量类
 *
 * @author cr
 */
public final class RedisConstant {
    // key
    public static final String MAIL_REGISTRY_CODE_TEMPLATE_KEY = "default:mail:template:content";
    public static final String MAIL_REGISTRY_CODE_EXPIRE_MINUTES_KEY = "default:mail:template:expireMinutes";
    public static final String MAIL_REGISTRY_CODE_SUBJECT_KEY = "default:mail:template:subject";
    public static final String MAIL_LAST_SEND_FLAG_KEY = "mail:last:send:flag:"; // 后面拼接目标邮箱，作为key
    public static final String MAIL_TODAY_SEND_COUNT_KEY = "mail:today:{today}:send:count:"; // 后面拼接目标邮箱，作为key
    public static final String MAIL_REGISTRY_CODE_KEY = "mail:registry:code:";

    // value
    public static final String MAIL_REGISTRY_CODE_DEFAULT_EXPIRE_MINUTES_VALUE = "1";
    public static final String MAIL_REGISTRY_CODE_DEFAULT_SUBJECT_VALUE = "燕雀教育(云课堂) - 注册验证码";
}
