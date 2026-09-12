package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.OperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志记录持久层接口
 *
 * @author cr
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}
