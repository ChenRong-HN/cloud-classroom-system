package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.PayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付订单持久层接口
 *
 * @author cr
 */
@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {

}
