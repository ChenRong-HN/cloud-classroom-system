package com.yanque.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.MediaFile;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 课程媒体文件业务层接口
 *
 * @author cr
 */
public interface IMediaFileService extends IService<MediaFile> {

    ApiPageResponse<MediaFile> pageList(BasicPageVo basicPageVo);

    void update2Free(Long mediaFileId);

    List<MediaFile> selectMediaList(Long courseId, Long chapterId);
}
