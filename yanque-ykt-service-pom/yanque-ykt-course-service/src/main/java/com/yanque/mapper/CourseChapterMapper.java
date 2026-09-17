package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.CourseChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程章节持久层接口
 *
 * @author cr
 */
@Mapper
public interface CourseChapterMapper extends BaseMapper<CourseChapter> {

    Long selectMaxChapterNumber(Long courseId);
}
