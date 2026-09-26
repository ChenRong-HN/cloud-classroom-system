package com.yanque.entity.vo;

import lombok.Data;

/**
 * 支付宝异步回调请求参数
 *
 * @author cr
 */
@Data
public class AliPayNotifyReqVo {
    /**
     * 编码集
     */
    private String charset;
    /**
     * 交易创建时间格式(yyyy-MM-dd HH:mm:ss)
     */
    private String gmt_create;
    /**
     * 交易付款时间 格式(yyyy-MM-dd HH:mm:ss)
     */
    private String gmt_payment;
    /**
     * 通知时间 格式(yyyy-MM-dd HH:mm:ss)
     */
    private String notify_time;
    /**
     * 订单标题
     */
    private String subject;
    /**
     * 签名
     */
    private String sign;
    /**
     * 买家支付宝用户号
     */
    private String buyer_id;
    /**
     * 开票金额
     */
    private String invoice_amount;
    /**
     * 接口版本
     */
    private String version;
    /**
     * 通知校验Id
     */
    private String notify_id;
    /**
     * 支付金额信息
     */
    private String fund_bill_list;
    /**
     * 通知类型
     */
    private String notify_type;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 订单总金额
     */
    private String total_amount;
    /**
     * 交易状态(TRADE_SUCCESS:交易成功 / TRADE_FINISHED:交易结束)
     */
    private String trade_status;
    /**
     * 支付宝交易号
     */
    private String trade_no;
    /**
     * 授权方AppId
     */
    private String auth_app_id;
    /**
     * 实收金额
     */
    private String receipt_amount;
    /**
     * 积分支付的金额
     */
    private String point_amount;
    /**
     * 应用AppId
     */
    private String app_id;
    /**
     * 买家实付金额
     */
    private String buyer_pay_amount;
    /**
     * 签名类型(RSA2)
     */
    private String sign_type;
    /**
     * 卖家支付宝用户号
     */
    private String seller_id;
    /**
     * 错误码(通知成功时为空)
     */
    private String code;
    /**
     * 错误信息(通知成功时为空)
     */
    private String msg;
    /**
     * 回传参数(商户发送请求时透传的透传参数)
     */
    private String passback_params;
}