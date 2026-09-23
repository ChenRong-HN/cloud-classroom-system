package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.AlipayInfoMapper;
import com.yanque.entity.AlipayInfo;
import com.yanque.service.IAlipayInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 支付宝参数信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class AlipayInfoService extends ServiceImpl<AlipayInfoMapper,AlipayInfo> implements IAlipayInfoService {

    // 注入AlipayInfo持久层接口实现类
    @Resource
    private AlipayInfoMapper alipayInfoMapper;

}
