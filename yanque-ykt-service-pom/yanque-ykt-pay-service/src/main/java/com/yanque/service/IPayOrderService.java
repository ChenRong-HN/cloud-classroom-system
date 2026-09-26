package com.yanque.service;

import com.yanque.entity.PayOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.entity.vo.AliPayNotifyReqVo;

/**
 * 支付订单业务层接口
 *
 * @author cr
 */
public interface IPayOrderService extends IService<PayOrder> {

    /**
     * 更新支付订单状态并保存支付流水
     *
     * @param aliPayNotifyReqVo 支付宝通知请求参数
     */
    void updateStatusAndSavePayFlow(AliPayNotifyReqVo aliPayNotifyReqVo);

}
