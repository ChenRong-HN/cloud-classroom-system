package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.OperationLogMapper;
import com.yanque.entity.OperationLog;
import com.yanque.service.IOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 操作日志记录业务层接口实现类
 *
 * @author cr
 */
@Service
public class OperationLogService extends ServiceImpl<OperationLogMapper,OperationLog> implements IOperationLogService {

    // 注入OperationLog持久层接口实现类
    @Resource
    private OperationLogMapper operationLogMapper;

}
