package com.yanque.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.entity.vo.TreeVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseTypeMapper;
import com.yanque.entity.CourseType;
import com.yanque.service.ICourseTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程分类业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseTypeService extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    // 注入CourseType持久层接口实现类
    @Resource
    private CourseTypeMapper courseTypeMapper;

    @Override
    public List<TreeVo> selectCourseTypeTreeData() {
        List<CourseType> courseTypes = list();
        List<TreeVo> treeVoList = courseTypes.stream().map(courseType -> TreeVo.builder()
                .id(courseType.getId())
                .pid(courseType.getPid())
                .name(courseType.getName())
                .children(Collections.emptyList())
                .build()).toList();

        // 根据父分类跟子分类一对多的特点，对课程类型对象集合按照父id进行分组，得到一个键值对，key就是父id，value就是所有pid==key的课程类型对象集合
        Map<Long, List<TreeVo>> groupByPidMap = treeVoList.stream().collect(Collectors.groupingBy(TreeVo::getPid));
        // 遍历treeVoList，将treeVo对象中的id当作父id去map中进行匹配，寻找自己的课程子分类
        treeVoList.forEach(treeVo -> treeVo.setChildren(groupByPidMap.get(treeVo.getId())));

        // 过滤出最高层父类型（pid为0）的集合
        return treeVoList.stream().filter(treeVo -> ObjUtil.equals(treeVo.getPid(), 0L)).toList();
    }

    @Override
    public ApiPageResponse<CourseType> selectPage(Map<String, Object> parameterMap) {
        // 设置分页参数对象 默认每页10条
        Page<CourseType> courseTypePage = new Page<>(Integer.parseInt(String.valueOf(parameterMap.get("page"))), 10);

        // 设置查询条件
        LambdaQueryWrapper<CourseType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ObjUtil.isNotNull(parameterMap.get("pid")), CourseType::getPid, parameterMap.get("pid"))
                .like(ObjUtil.isNotNull(parameterMap.get("keyword")), CourseType::getName, parameterMap.get("keyword"));

        // 进行分页查询
        courseTypePage = page(courseTypePage, queryWrapper);

        return ApiPageResponse.<CourseType>builder().total(courseTypePage.getTotal()).rows(courseTypePage.getRecords()).build();
    }
}
