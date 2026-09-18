package com.yanque.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanque.common.constant.PageConstant;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.MediaFile;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
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

    @Override
    public boolean save(MediaFile mediaFile) {
        // 校验当前章节是否已存在
        LambdaQueryWrapper<MediaFile> courseChapterLambdaQueryWrapper = Wrappers.<MediaFile>lambdaQuery().eq(StrUtil.isNotBlank(mediaFile.getName()), MediaFile::getName, mediaFile.getName());
        Assert.equals(mediaFileMapper.selectCount(courseChapterLambdaQueryWrapper), 0L, () -> new BusinessException(BusinessErrorType.MEDIA_FILE_EXISTS));
        // 判断编号是否合法
        Long maxMediaNumber = mediaFileMapper.selectMaxMediaNumber(mediaFile.getChapterId());
        Assert.isTrue(mediaFile.getNumber() > maxMediaNumber,()->new BusinessException(BusinessErrorType.MEDIA_FILE_NUMBER_ERROR));
        return super.save(mediaFile);
    }

    @Override
    public ApiPageResponse<MediaFile> pageList(BasicPageVo basicPageVo) {
        // 构建分页查询参数对象
        Page<MediaFile> page = new Page<>(basicPageVo.getPage(), PageConstant.DEFAULT_PAGE_DATA_COUNT);
        // 构建查询条件参数对象
        LambdaQueryWrapper<MediaFile> courseChapterLambdaQueryWrapper = Wrappers.<MediaFile>lambdaQuery()
                .like(StrUtil.isNotBlank(basicPageVo.getKeyword()), MediaFile::getName, basicPageVo.getKeyword())
                .or()
                .like(StrUtil.isNotBlank(basicPageVo.getKeyword()), MediaFile::getCourseName, basicPageVo.getKeyword())
                .orderByAsc(MediaFile::getNumber); // 按照章节编号从小到大排序
        // 进行分页查询
        page = page(page, courseChapterLambdaQueryWrapper);
        return ApiPageResponse.<MediaFile>builder().total(page.getTotal()).rows(page.getRecords()).build();
    }
}
