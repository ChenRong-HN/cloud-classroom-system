package com.yanque.common.constant;

/**
 * 支付状态常量类
 *
 * @author cr
 */
public class PayStatusConstant {
    // 未支付
    public static final Long UNPAY = 0L;
    // 支付成功
    public static final Long PAY_SUCCESS = 1L;
    // 支付取消
    public static final Long PAY_CANCEL = 2L;
    // 支付失败
    public static final Long PAY_FAIL = 3L;
    // 支付超时
    public static final Long PAY_TIMEOUT = 4L;
}
