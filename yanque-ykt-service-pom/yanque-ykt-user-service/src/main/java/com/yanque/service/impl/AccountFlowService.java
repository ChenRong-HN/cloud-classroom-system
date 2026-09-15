package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.AccountFlowMapper;
import com.yanque.common.AccountFlow;
import com.yanque.service.IAccountFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 会员账户流水业务层接口实现类
 *
 * @author cr
 */
@Service
public class AccountFlowService extends ServiceImpl<AccountFlowMapper,AccountFlow> implements IAccountFlowService {

    // 注入AccountFlow持久层接口实现类
    @Resource
    private AccountFlowMapper accountFlowMapper;

}
