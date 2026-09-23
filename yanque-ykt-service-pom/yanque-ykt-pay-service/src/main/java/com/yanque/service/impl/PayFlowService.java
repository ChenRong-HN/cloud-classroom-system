package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.PayFlowMapper;
import com.yanque.entity.PayFlow;
import com.yanque.service.IPayFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 支付流水业务层接口实现类
 *
 * @author cr
 */
@Service
public class PayFlowService extends ServiceImpl<PayFlowMapper,PayFlow> implements IPayFlowService {

    // 注入PayFlow持久层接口实现类
    @Resource
    private PayFlowMapper payFlowMapper;

}
