package com.yanque.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanque.entity.MediaFile;
import com.yanque.mapper.MediaFileMapper;
import com.yanque.service.IMediaFileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 课程媒体文件业务层接口实现类
 *
 * @author cr
 */
@Service
public class MediaFileService extends ServiceImpl<MediaFileMapper,MediaFile> implements IMediaFileService {

    // 注入MediaFile持久层接口实现类
    @Resource
    private MediaFileMapper mediaFileMapper;

}
