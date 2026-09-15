package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.CourseSummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程统计持久层接口
 *
 * @author cr
 */
@Mapper
public interface CourseSummaryMapper extends BaseMapper<CourseSummary> {

}
