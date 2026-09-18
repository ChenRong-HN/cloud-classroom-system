package com.yanque.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanque.entity.MediaFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 课程媒体文件持久层接口
 *
 * @author cr
 */
@Mapper
public interface MediaFileMapper extends BaseMapper<MediaFile> {

    Long selectMaxMediaNumber(@Param("chapterId") Long chapterId);
}
