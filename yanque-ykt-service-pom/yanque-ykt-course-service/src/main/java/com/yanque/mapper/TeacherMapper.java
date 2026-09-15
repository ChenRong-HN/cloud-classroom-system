package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 老师持久层接口
 *
 * @author cr
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

}
