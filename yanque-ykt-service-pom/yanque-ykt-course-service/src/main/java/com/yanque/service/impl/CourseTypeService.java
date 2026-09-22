package com.yanque.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanque.common.constant.RedisConstant;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.entity.CourseType;
import com.yanque.entity.vo.CourseTypeCrumbRespVo;
import com.yanque.entity.vo.TreeVo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.mapper.CourseTypeMapper;
import com.yanque.service.ICourseTypeService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Cacheable(cacheNames = RedisConstant.COURSE_TYPE_TREE_DATA_LIST_KEY, key = "'all'") // 不加单引号则会去匹配参数列表中相同名称的参数值
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
                .like(StrUtil.isNotBlank((String) parameterMap.get("keyword")), CourseType::getName, parameterMap.get("keyword"));

        // 进行分页查询
        courseTypePage = page(courseTypePage, queryWrapper);

        return ApiPageResponse.<CourseType>builder().total(courseTypePage.getTotal()).rows(courseTypePage.getRecords()).build();
    }

    /**
     * 新增课程分类
     *
     * @param courseType 课程分类对象
     */
    @Override
    @Transactional
    // 更新操作之后删除缓存 allEntries = true:删除当前缓存名称下的所有缓存条目
    @CacheEvict(cacheNames = RedisConstant.COURSE_TYPE_TREE_DATA_LIST_KEY, allEntries = true)
    public boolean save(CourseType courseType) {
        // 填充数据
        long now = System.currentTimeMillis();
        courseType.setCreateTime(now);
        courseType.setUpdateTime(now);
        courseType.setTotalCount(0L);

        courseTypeMapper.insert(courseType);

        // 判断是否当前对象是否为顶层父类型，是则直接将id作为path更新到数据库，否则将当前id拼接到父类型的path属性后面作为当前对象的path更新
        if (courseType.getPid().equals(0L))
            // 新增操作后，基于主键回显获取到id
            courseType.setPath(String.valueOf(courseType.getId()));
        else {
            CourseType parentCourseType = courseTypeMapper.selectById(courseType.getPid());
            courseType.setPath(parentCourseType.getPath() + "." + courseType.getId());
        }
        // 将更新path字段后的对象更新
        return updateById(courseType);
    }

    /**
     * 基于Id更新课程分类数据
     *
     * @param courseType 课程分类实体对象
     * @return 是否更新成功
     */
    @Override
    @CacheEvict(cacheNames = RedisConstant.COURSE_TYPE_TREE_DATA_LIST_KEY, allEntries = true)
    public boolean updateById(CourseType courseType) {
        return super.updateById(courseType);
    }

    /**
     * 基于Id删除课程分类数据
     *
     * @param id 课程分类Id
     * @return 是否删除成功
     */
    @Override
    @CacheEvict(cacheNames = RedisConstant.COURSE_TYPE_TREE_DATA_LIST_KEY, allEntries = true)
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public List<CourseTypeCrumbRespVo> crumbs(Long courseTypeId) {
        // 根据课程类型id查询课程类型对象
        CourseType ownerProductType = getById(courseTypeId);
        Assert.notNull(ownerProductType, () -> new BusinessException(BusinessErrorType.COURSE_TYPE_NOT_EXISTS));
        // 获取该课程分类的父分类id集合
        List<Long> courseTypeParentIdList = StrUtil.split(ownerProductType.getPath(), ".").stream().map(Long::valueOf).toList();
        // 获取课程信息父分类集合
        List<CourseType> parentCourseTypeList = list(Wrappers.<CourseType>lambdaQuery().in(ObjUtil.isNotNull(courseTypeParentIdList), CourseType::getId, courseTypeParentIdList));
        // 将parentCourseTypeList转换为CourseTypeCrumbRespVoList（组装返回值）
        List<CourseTypeCrumbRespVo> courseTypeCrumbRespVoList = parentCourseTypeList.stream().map(courseType -> {
            // 创建CourseTypeCrumbRespVo对象
            CourseTypeCrumbRespVo courseTypeCrumbRespVo = new CourseTypeCrumbRespVo();
            courseTypeCrumbRespVo.setOwnerProductType(courseType);
            // 查询当前课程分类对象的兄弟课程分类并赋值
            List<CourseType> otherProducuTypeList = list(Wrappers.<CourseType>lambdaQuery().eq(CourseType::getPid, courseType.getPid()))
                    .stream()
                    // 过滤掉自己
                    .filter(c -> ObjUtil.notEqual(courseType.getId(), c.getId())).toList();
            courseTypeCrumbRespVo.setOtherProductTypes(otherProducuTypeList);
            return courseTypeCrumbRespVo;
        }).toList();
        return courseTypeCrumbRespVoList;
    }
}
