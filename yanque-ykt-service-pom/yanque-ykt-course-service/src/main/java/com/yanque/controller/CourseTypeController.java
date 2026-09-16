package com.yanque.controller;

import java.util.List;
import java.util.Map;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.vo.TreeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseType;
import com.yanque.service.ICourseTypeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程分类控制层接口
 *
 * @author cr
 */
@Tag(name = "课程分类管理", description = "课程分类接口")
@RestController
@RequestMapping("/course/courseType")
public class CourseTypeController {

    // 注入课程分类服务层接口实现类
    @Resource
    private ICourseTypeService courseTypeService;

    /**
     * 查询课程分类列表
     *
     * @return 全局通用返回结果(课程分类所有数据})
     */
    @Operation(summary = "查询课程分类列表", description = "查询所有课程分类信息列表")
    @GetMapping("/list")
    public ApiResponse<List<CourseType>> list() {
        // 调用课程分类服务层接口查询所有课程分类信息
        List<CourseType> list = courseTypeService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程分类
     *
     * @param id 课程分类主键Id
     * @return 全局通用返回结果(课程分类实体数据)
     */
    @Operation(summary = "根据Id查询课程分类", description = "根据主键Id查询课程分类详细信息")
    @GetMapping("/{id}")
    public ApiResponse<CourseType> getById(@Parameter(description = "课程分类Id") @PathVariable("id") Long id) {
        // 调用课程分类服务层接口根据Id查询课程分类信息
        CourseType entity = courseTypeService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程分类
     *
     * @param courseType 课程分类实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程分类", description = "新增课程分类信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseType courseType) {
        // 调用课程分类服务层接口保存课程分类信息
        courseTypeService.save(courseType);
        return ApiResponse.success();
    }

    /**
     * 修改课程分类
     *
     * @param courseType 课程分类实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程分类", description = "修改课程分类信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseType courseType) {
        // 调用课程分类服务层接口修改课程分类信息
        courseTypeService.updateById(courseType);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程分类
     *
     * @param id 课程分类主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程分类", description = "根据Id删除课程分类信息")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "课程分类Id") @PathVariable("id") Long id) {
        // 调用课程分类服务层接口根据Id删除课程分类信息
        courseTypeService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 获取课程分类树形数据
     *
     * @return 全局通用返回结果
     */
    @Operation(summary = "获取课程分类树形数据", description = "获取课程分类树形数据")
    @GetMapping("/treeData")
    public ApiResponse<List<TreeVo>> getCourseTypeTreeData() {
        List<TreeVo> treeVoList = courseTypeService.selectCourseTypeTreeData();
        return ApiResponse.success(treeVoList);
    }

    /**
     * 多条件分页查询
     *
     * @param paramterMap 前端传来的json参数自动映射为map
     * @return 全局通用返回结果
     */
    @Operation(summary = "分页条件查询课程分类列表", description = "分页条件查询课程分类信息列表")
    @PostMapping("/pagelist")
    public ApiResponse<ApiPageResponse<CourseType>> pageList(@RequestBody Map<String, Object> paramterMap) {
        ApiPageResponse<CourseType> pageR = courseTypeService.selectPage(paramterMap);
        return ApiResponse.success(pageR);
    }
}