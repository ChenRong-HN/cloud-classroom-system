package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程信息持久层接口
 *
 * @author cr
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

}
