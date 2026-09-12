package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.Config;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 参数配置持久层接口
 *
 * @author cr
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

}
