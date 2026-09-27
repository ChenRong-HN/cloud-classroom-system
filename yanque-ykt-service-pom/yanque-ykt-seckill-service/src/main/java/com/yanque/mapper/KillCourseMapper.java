package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.KillCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀课程信息持久层接口
 *
 * @author cr
 */
@Mapper
public interface KillCourseMapper extends BaseMapper<KillCourse> {

}
