package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.KillActivityMapper;
import com.yanque.entity.KillActivity;
import com.yanque.service.IKillActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 秒杀活动信息业务层接口实现类
 *
 * @author x1angwan
 */
@Service
public class KillActivityService extends ServiceImpl<KillActivityMapper,KillActivity> implements IKillActivityService {

    // 注入KillActivity持久层接口实现类
    @Resource
    private KillActivityMapper killActivityMapper;

}
