package com.yanque.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.MediaFile;

/**
 * 课程媒体文件业务层接口
 *
 * @author cr
 */
public interface IMediaFileService extends IService<MediaFile> {

    ApiPageResponse<MediaFile> pageList(BasicPageVo basicPageVo);
}
