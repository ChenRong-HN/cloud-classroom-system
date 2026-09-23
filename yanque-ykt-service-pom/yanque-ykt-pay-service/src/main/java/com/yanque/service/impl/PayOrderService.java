package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.PayOrderMapper;
import com.yanque.entity.PayOrder;
import com.yanque.service.IPayOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 支付订单业务层接口实现类
 *
 * @author cr
 */
@Service
public class PayOrderService extends ServiceImpl<PayOrderMapper,PayOrder> implements IPayOrderService {

    // 注入PayOrder持久层接口实现类
    @Resource
    private PayOrderMapper payOrderMapper;

}
