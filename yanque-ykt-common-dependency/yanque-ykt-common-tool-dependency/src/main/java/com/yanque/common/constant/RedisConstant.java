package com.yanque.common.constant;

/**
 * redis从常用字符串抽取为静态常量类
 *
 * @author cr
 */
public final class RedisConstant {
    // key
    // 邮箱模版默认内容
    public static final String MAIL_REGISTRY_CODE_TEMPLATE_KEY = "default:mail:template:content";
    // 邮箱模版默认过期时间
    public static final String MAIL_REGISTRY_CODE_EXPIRE_MINUTES_KEY = "default:mail:template:expireMinutes";
    // 默认邮箱主题模版
    public static final String MAIL_REGISTRY_CODE_SUBJECT_KEY = "default:mail:template:subject";
    // 邮箱最后发送标志
    public static final String MAIL_LAST_SEND_FLAG_KEY = "mail:last:send:flag:"; // 后面拼接目标邮箱，作为key
    // 邮箱今日发送次数
    public static final String MAIL_TODAY_SEND_COUNT_KEY = "mail:today:{today}:send:count:"; // 后面拼接目标邮箱，作为key
    // 用户注册邮箱验证码
    public static final String MAIL_REGISTRY_CODE_KEY = "mail:registry:code:";
    // 课程类型树形结构数据列表
    public static final String COURSE_TYPE_TREE_DATA_LIST_KEY = "course_type:tree:list";
    // 订单防重复提交确认令牌
    public static final String ORDER_CONFIRM_TOKEN_KEY = "order:token:";
    // 秒杀活动大Key
    public static final String KILL_ACTIVITY_KEY = "seckill:activity";
    // 秒杀活动包含课程大Key
    public static final String KILL_ACTIVITY_COURSE_KEY = "seckill:activity:";
    // 秒杀课程库存信号量Key（预留了两个占位符）
    public static final String KILL_ACTIVITY_COURSE_STOCK_SEMAPHORE_KEY = "seckill:semaphore:activity:%s:course:%s";
    // 用户秒杀的分布式锁Key（预留了三个占位符：秒杀活动ID、秒杀课程ID、用户ID）
    public static final String USER_KILL_KEY = "seckill:user:lock:%s:%s:%s";
    // 预秒杀订单Key（预留了两个占位符：秒杀活动ID、秒杀课程ID）
    public static final String PRE_SECKILL_ORDER = "pre:kill:order:%s:%s";

    // value
    public static final Integer MAIL_REGISTRY_CODE_DEFAULT_EXPIRE_MINUTES_VALUE = 5;
    public static final String MAIL_REGISTRY_CODE_DEFAULT_SUBJECT_VALUE = "燕雀教育(云课堂) - 注册验证码";
    public static final Integer ORDER_CONFIRM_DEFAULT_EXPIRE_TIME = 30;
}
