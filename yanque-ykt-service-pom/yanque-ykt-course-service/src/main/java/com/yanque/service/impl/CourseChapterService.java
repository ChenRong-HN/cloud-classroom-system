package com.yanque.service.impl;

import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanque.common.constant.PageConstant;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseChapterMapper;
import com.yanque.entity.CourseChapter;
import com.yanque.service.ICourseChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程章节业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseChapterService extends ServiceImpl<CourseChapterMapper, CourseChapter> implements ICourseChapterService {

    // 注入CourseChapter持久层接口实现类
    @Resource
    private CourseChapterMapper courseChapterMapper;

    @Override
    public boolean save(CourseChapter courseChapter) {
        // 查询当前课程中的最大章节编号
        Long maxChapterNumber = courseChapterMapper.selectMaxChapterNumber(courseChapter.getCourseId());
        Assert.isTrue(courseChapter.getNumber() > maxChapterNumber, () -> new BusinessException(BusinessErrorType.COURSE_CHAPTER_NUMBER_ERROR));
        LambdaQueryWrapper<CourseChapter> courseChapterLambdaQueryWrapper = Wrappers.<CourseChapter>lambdaQuery().eq(StrUtil.isNotBlank(courseChapter.getName()), CourseChapter::getName, courseChapter.getName());
        // 校验当前章节是否已存在
        Assert.equals(courseChapterMapper.selectCount(courseChapterLambdaQueryWrapper), 0L, () -> new BusinessException(BusinessErrorType.COURSE_CHAPTER_EXISTS));
        return super.save(courseChapter);
    }

    @Override
    public ApiPageResponse<CourseChapter> pageList(BasicPageVo basicPageVo) {
        // 构建分页查询参数对象
        Page<CourseChapter> page = new Page<>(basicPageVo.getPage(), PageConstant.DEFAULT_PAGE_DATA_COUNT);
        // 构建查询条件参数对象
        LambdaQueryWrapper<CourseChapter> courseChapterLambdaQueryWrapper = Wrappers.<CourseChapter>lambdaQuery()
                .like(StrUtil.isNotBlank(basicPageVo.getKeyword()), CourseChapter::getName, basicPageVo.getKeyword())
                .or()
                .like(StrUtil.isNotBlank(basicPageVo.getKeyword()), CourseChapter::getCourseName, basicPageVo.getKeyword())
                .orderByAsc(CourseChapter::getNumber); // 按照章节编号从小到大排序
        // 进行分页查询
        page = page(page, courseChapterLambdaQueryWrapper);
        return ApiPageResponse.<CourseChapter>builder().total(page.getTotal()).rows(page.getRecords()).build();
    }
}
