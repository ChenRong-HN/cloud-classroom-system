package com.yanque.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanque.common.constant.PageConstant;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.Course;
import com.yanque.entity.CourseChapter;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.mapper.CourseChapterMapper;
import com.yanque.service.ICourseChapterService;
import com.yanque.service.ICourseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Resource
    private ICourseService courseService;

    @Override
    @Transactional
    public boolean save(CourseChapter courseChapter) {
        // 校验当前章节是否已存在
        LambdaQueryWrapper<CourseChapter> courseChapterLambdaQueryWrapper = Wrappers.<CourseChapter>lambdaQuery().eq(StrUtil.isNotBlank(courseChapter.getName()), CourseChapter::getName, courseChapter.getName());
        Assert.equals(courseChapterMapper.selectCount(courseChapterLambdaQueryWrapper), 0L, () -> new BusinessException(BusinessErrorType.COURSE_CHAPTER_EXISTS));
        // 查询当前课程中的最大章节编号
        Long maxChapterNumber = courseChapterMapper.selectMaxChapterNumber(courseChapter.getCourseId());
        Assert.isTrue(courseChapter.getNumber() > maxChapterNumber, () -> new BusinessException(BusinessErrorType.COURSE_CHAPTER_NUMBER_ERROR));

        // 更新课程的章节数量字段
        long chapterCount = count(Wrappers.<CourseChapter>lambdaQuery().eq(CourseChapter::getCourseId, courseChapter.getCourseId()));
        Course course = courseService.getById(courseChapter.getCourseId());
        course.setChapterCount(chapterCount + 1);
        courseService.updateById(course);
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

    @Override
    public List<CourseChapter> listByCourseId(Long courseId) {
        LambdaQueryWrapper<CourseChapter> courseChapterLambdaQueryWrapper = Wrappers.<CourseChapter>lambdaQuery()
                .eq(ObjUtil.isNotNull(courseId),CourseChapter::getCourseId,courseId);
        return list(courseChapterLambdaQueryWrapper);
    }
}
