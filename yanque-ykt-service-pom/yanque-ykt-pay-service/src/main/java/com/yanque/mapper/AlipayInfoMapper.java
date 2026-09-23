package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.AlipayInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付宝参数信息持久层接口
 *
 * @author cr
 */
@Mapper
public interface AlipayInfoMapper extends BaseMapper<AlipayInfo> {

}
