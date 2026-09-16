package com.yanque.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.entity.*;
import com.yanque.entity.vo.*;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 课程信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    // 注入Course持久层接口实现类
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ICourseTypeService courseTypeService;

    @Resource
    private ITeacherService teacherService;

    @Resource
    private ICourseDetailService courseDetailService;

    @Resource
    private ICourseMarketService courseMarketService;

    @Resource
    private ICourseResourceService courseResourceService;

    @Resource
    private ICourseTeacherService courseTeacherService;

    @Override
    @Transactional
    public void saveCourse(AddCourseReqVo addCourseReqVo) {
        // 获取请求数据中的各个对象
        CourseDetailReqVo courseDetailReqVo = addCourseReqVo.getCourseDetail();
        CourseResourceReqVo courseResourceReqVo = addCourseReqVo.getCourseResource();
        CourseMarketReqVo courseMarketReqVo = addCourseReqVo.getCourseMarket();
        CourseReqVo courseReqVo = addCourseReqVo.getCourse();
        List<Long> idList = addCourseReqVo.getTeacharIds();

        // 校验1：课程名称不能重复
        Assert.equals(count(Wrappers.<Course>lambdaQuery().eq(Course::getName, courseReqVo.getName())), 0L, () -> new BusinessException(BusinessErrorType.COURSE_NAME_EXISTS));
        // 校验2：分类Id必须存在
        Assert.equals(courseTypeService.count(Wrappers.<CourseType>lambdaQuery().eq(CourseType::getId, courseReqVo.getCourseTypeId())), 1L, () -> new BusinessException(BusinessErrorType.COURSE_TYPE_NOT_EXISTS));
        // 校验3：老师Id必须存在
        List<Teacher> teacherList = teacherService.list(Wrappers.<Teacher>lambdaQuery().in(Teacher::getId, idList));
        Assert.equals(idList.size(), teacherList.size(), () -> new BusinessException(BusinessErrorType.TEACHER_NOT_EXISTS));
        // 校验4：课程开始时间、结束时间遵守顺序
        Assert.isTrue(courseReqVo.getStartTime().isBefore(courseReqVo.getEndTime()), () -> new BusinessException(BusinessErrorType.COURSE_TIME_ERROR));

        // 添加课程基本信息
        Course course = BeanUtil.copyProperties(courseReqVo, Course.class);
        course.setStatus(0L);
        course.setLoginId(2L);
        course.setLoginUserName("李狗蛋");
        course.setTotalMinute(0L);
        course.setChapterCount(0L);
        course.setTeacherNames(teacherList.stream().map(Teacher::getName).collect(Collectors.joining(",")));

        // 添加课程基础信息
        save(course);
        // 基于主键回显,获取添加课程的主键Id
        Long courseId = course.getId();

        // 添加课程详情信息
        CourseDetail courseDetail = BeanUtil.copyProperties(courseDetailReqVo, CourseDetail.class);
        courseDetail.setId(courseId); // 主键共享
        courseDetailService.save(courseDetail);

        // 添加课程销售信息
        CourseMarket courseMarket = BeanUtil.copyProperties(courseMarketReqVo, CourseMarket.class);
        courseMarket.setId(courseId); // 主键共享
        courseMarketService.save(courseMarket);

        // 添加课程课件资源信息
        CourseResource courseResource = BeanUtil.copyProperties(courseResourceReqVo, CourseResource.class);
        courseResource.setCourseId(courseId); // 一对多关系维护
        courseResourceService.save(courseResource);

        // 添加课程与老师的关联关系信息
        List<CourseTeacher> courseTeacherList = teacherList.stream().map(teacher -> new CourseTeacher(null, teacher.getId(), courseId)).toList();
        courseTeacherService.saveBatch(courseTeacherList); // 批量添加
    }

    @Override
    public ApiPageResponse<Course> pageList(Map<String, Object> parameterMap) {
        String key = (String) parameterMap.get("keyword");
        long page = Long.parseLong(String.valueOf(parameterMap.get("page")));

        // 构建分页参数对象
        Page<Course> coursePage = new Page<Course>(page, 10L);

        // 构建查询条件参数对象
        LambdaQueryWrapper<Course> queryWrapper = Wrappers.<Course>lambdaQuery().like(StrUtil.isNotBlank(key), Course::getName, key);

        coursePage = page(coursePage, queryWrapper);
        return ApiPageResponse.<Course>builder().total(coursePage.getTotal()).rows(coursePage.getRecords()).build();
    }
}
