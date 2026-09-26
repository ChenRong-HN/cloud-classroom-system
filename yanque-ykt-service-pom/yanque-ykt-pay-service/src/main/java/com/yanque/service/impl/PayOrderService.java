package com.yanque.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanque.common.constant.PayStatusConstant;
import com.yanque.entity.PayFlow;
import com.yanque.entity.PayOrder;
import com.yanque.entity.vo.AliPayNotifyReqVo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.mapper.PayOrderMapper;
import com.yanque.service.IPayFlowService;
import com.yanque.service.IPayOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 支付订单业务层接口实现类
 *
 * @author cr
 */
@Service
public class PayOrderService extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

    // 注入PayOrder持久层接口实现类
    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private IPayFlowService payFlowService;

    /**
     * 更新支付状态、新增订单流水记录
     *
     * @param aliPayNotifyReqVo 支付宝通知请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusAndSavePayFlow(AliPayNotifyReqVo aliPayNotifyReqVo) {
        // 获取本次交易的商家订单号(订单编号)
        String orderNo = aliPayNotifyReqVo.getOut_trade_no();
        // 更新支付订单的状态
        PayOrder payOrder = getOne(Wrappers.<PayOrder>lambdaQuery().eq(PayOrder::getOrderNo, orderNo));
        Assert.notNull(payOrder, () -> new BusinessException(BusinessErrorType.PAY_ORDER_NOT_FOUND));
        if (PayStatusConstant.UNPAY.equals(payOrder.getPayStatus())) {
            payOrder.setPayStatus(PayStatusConstant.PAY_SUCCESS);
            payOrder.setUpdateTime(LocalDateTime.now());
            updateById(payOrder);
        }
        // 添加支付流水
        PayFlow payFlow = PayFlow.builder()
                .notifyTime(DateUtil.parseLocalDateTime(aliPayNotifyReqVo.getNotify_time()))
                .subject(aliPayNotifyReqVo.getSubject())
                .outTradeNo(aliPayNotifyReqVo.getOut_trade_no())
                .totalAmount(Convert.toLong(aliPayNotifyReqVo.getTotal_amount()))
                .code(aliPayNotifyReqVo.getCode())
                .msg(aliPayNotifyReqVo.getMsg())
                .passbackParams(aliPayNotifyReqVo.getPassback_params())
                .tradeStatus(aliPayNotifyReqVo.getTrade_status())
                .paySuccess(PayStatusConstant.PAY_SUCCESS.intValue())
                .resultDesc(aliPayNotifyReqVo.getSubject() + " 支付金额 " + aliPayNotifyReqVo.getTotal_amount() + "元").build();
        payFlowService.save(payFlow);
    }
}
