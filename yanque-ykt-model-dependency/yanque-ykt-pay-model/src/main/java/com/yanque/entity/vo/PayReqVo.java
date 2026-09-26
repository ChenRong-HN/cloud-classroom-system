package com.yanque.entity.vo;

import lombok.Data;

/**
 * 支付请求参数
 *
 * @author cr
 */
@Data
public class PayReqVo {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 支付方式 （1 支付宝 2 微信 3 银联支付）
     */
    private String payType;
    /**
     * 同步回调地址（支付完成后跳转地址）
     */
    private String callUrl;
}
