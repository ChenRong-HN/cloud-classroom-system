package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.PayFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付流水持久层接口
 *
 * @author cr
 */
@Mapper
public interface PayFlowMapper extends BaseMapper<PayFlow> {

}
