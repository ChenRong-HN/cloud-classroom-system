package com.yanque.service.impl;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.func.Func1;
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

    @Resource
    private CourseUserShowListService courseUserShowListService;

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
        if (ObjUtil.isNull(parameterMap.get("page")))
            parameterMap.put("page", 1L);
        long page = Long.parseLong(String.valueOf(parameterMap.get("page")));

        // 构建分页参数对象
        Page<Course> coursePage = new Page<Course>(page, 10L);

        // 构建查询条件参数对象
        LambdaQueryWrapper<Course> queryWrapper = Wrappers.<Course>lambdaQuery().like(StrUtil.isNotBlank(key), Course::getName, key);

        coursePage = page(coursePage, queryWrapper);
        return ApiPageResponse.<Course>builder().total(coursePage.getTotal()).rows(coursePage.getRecords()).build();
    }

    @Override
    @Transactional
    public void onLineCourse(Long courseId) {
        // 1. 校验课程信息是否存在
        Course course = getById(courseId);
        Assert.notNull(course, () -> new BusinessException(BusinessErrorType.COURSE_NOT_EXISTS));
        // 校验是否重复发布
        LambdaQueryWrapper<CourseUserShowList> courseUserShowListLambdaQueryWrapper = Wrappers.<CourseUserShowList>lambdaQuery().eq(CourseUserShowList::getCourseId, courseId);
        long count = courseUserShowListService.count(courseUserShowListLambdaQueryWrapper);
        Assert.equals(count, 0L, () -> new BusinessException(BusinessErrorType.COURSE_ALREADY_UP));
        // 2. 将课程信息的状态修改为发布、更新发布时间
        course.setStatus(1L);
        course.setOnlineTime(LocalDate.now());
        updateById(course);
        // 3. 获取课程Course表中的原始数据、CourseMarket表中的原始数据、组成为CouseUserShowList对象后插入到宽表中
        CourseUserShowList courseUserShowList = BeanUtil.copyProperties(course, CourseUserShowList.class, "id");
        courseUserShowList.setCourseId(courseId);
        CourseMarket courseMarket = courseMarketService.getOne(Wrappers.<CourseMarket>lambdaQuery().eq(CourseMarket::getId, courseId));
        BeanUtil.copyProperties(courseMarket, courseUserShowList, "id");
        courseUserShowList.setSaleCount(0L);
        courseUserShowList.setViewCount(0L);
        courseUserShowList.setCommentCount(0L);
        courseUserShowListService.save(courseUserShowList);
    }

    @Override
    public void offLineCourse(Long courseId) {
        // 校验课程是否存在
        Course course = getById(courseId);
        Assert.notNull(course, () -> new BusinessException(BusinessErrorType.COURSE_NOT_EXISTS));

        // 校验课程是否已下架/不存在
        LambdaQueryWrapper<CourseUserShowList> courseUserShowListLambdaQueryWrapper = Wrappers.<CourseUserShowList>lambdaQuery().eq(CourseUserShowList::getCourseId, courseId);
        long count = courseUserShowListService.count(courseUserShowListLambdaQueryWrapper);
        Assert.equals(count, 1L, () -> new BusinessException(BusinessErrorType.COURSE_ALREADY_DOWN));
        // 更新课程的状态
        course.setStatus(0L);
        updateById(course);
        // 删除宽表数据
        courseUserShowListService.remove(Wrappers.<CourseUserShowList>lambdaQuery().eq(CourseUserShowList::getCourseId, courseId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDown(List<Long> courseIds) {
        if (CollUtil.isEmpty(courseIds)) {
            throw new BusinessException(BusinessErrorType.PARAM_ERROR);
        }

        // 过滤null元素以及去重
        courseIds = courseIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 1. 获取课程信息集合 并确保每个id对应的课程是存在的
        List<Course> courseList = listByIds(courseIds);
        if (courseList.size() != courseIds.size()) {
            throw new BusinessException(BusinessErrorType.COURSE_NOT_EXISTS);
        }

        // 2. 批量查宽表（用 courseId 查）
        List<CourseUserShowList> showList = courseUserShowListService.list(
                Wrappers.<CourseUserShowList>lambdaQuery()
                        .in(CourseUserShowList::getCourseId, courseIds)
        );

        // 每个课程必须有且只有 1 条宽表记录，否则说明存在已下架课程或数据异常
        if (showList.size() != courseIds.size()) {
            throw new BusinessException(BusinessErrorType.COURSE_ALREADY_DOWN);
        }

        // 3. 批量更新课程状态为 0（下架）
        List<Course> updateList = courseList.stream()
                .map(c -> {
                    c.setStatus(0L);
                    return c;
                })
                .collect(Collectors.toList());
        updateBatchById(updateList);

        // 4. 批量删除宽表数据（用 courseId 删）
        courseUserShowListService.remove(
                Wrappers.<CourseUserShowList>lambdaQuery()
                        .in(CourseUserShowList::getCourseId, courseIds)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchOnLine(List<Long> courseIds) {
        // 1. 参数校验
        if (CollUtil.isEmpty(courseIds)) {
            throw new BusinessException(BusinessErrorType.PARAM_ERROR);
        }

        // 过滤null元素以及去重
        courseIds = courseIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(courseIds)) {
            throw new BusinessException(BusinessErrorType.PARAM_ERROR);
        }

        // 2. 批量校验课程是否存在，并判断是否存在已经上架的
        List<Course> courseList = listByIds(courseIds);
        if (courseList.size() != courseIds.size()) {
            throw new BusinessException(BusinessErrorType.COURSE_NOT_EXISTS);
        }

        // 3. 批量校验是否重复发布
        //    单条是 count == 0，批量就是：宽表里一条都不能有
        List<CourseUserShowList> courseUserShowList = courseUserShowListService.list(
                Wrappers.<CourseUserShowList>lambdaQuery()
                        .in(CourseUserShowList::getCourseId, courseIds)
        );
        if (CollUtil.isNotEmpty(courseUserShowList)) {
            throw new BusinessException(BusinessErrorType.COURSE_ALREADY_UP);
        }

        // 4. 批量更新课程状态为发布、设置发布时间
        List<Course> updateList = courseList.stream()
                .map(c -> {
                    c.setStatus(1L);
                    c.setOnlineTime(LocalDate.now());
                    return c;
                })
                .collect(Collectors.toList());
        updateBatchById(updateList);

        // 5. 批量查 CourseMarket（一次查完，避免循环查库）
        List<CourseMarket> marketList = courseMarketService.list(
                Wrappers.<CourseMarket>lambdaQuery()
                        .in(CourseMarket::getId, courseIds)
        );
        Map<Long, CourseMarket> marketMap = CollUtil.toMap(marketList, new HashMap<>(), CourseMarket::getId);

        // 6. 批量组装宽表数据
        List<CourseUserShowList> showList = new ArrayList<>();
        for (Course course : courseList) {
            CourseUserShowList show = BeanUtil.copyProperties(course, CourseUserShowList.class, "id");
            show.setCourseId(course.getId());

            CourseMarket market = marketMap.get(course.getId());
            if (market != null) {
                BeanUtil.copyProperties(market, show, "id");
            }

            // 填充默认值
            show.setSaleCount(0L);
            show.setViewCount(0L);
            show.setCommentCount(0L);

            showList.add(show);
        }

        // 7. 批量插入宽表
        courseUserShowListService.saveBatch(showList);
    }
}
