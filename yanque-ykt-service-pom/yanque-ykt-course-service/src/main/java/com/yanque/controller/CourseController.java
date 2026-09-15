package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.Course;
import com.yanque.service.ICourseService;
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
 * 课程信息控制层接口
 *
 * @author cr
 */
@Tag(name = "课程信息管理", description = "课程信息接口")
@RestController
@RequestMapping("/course/course")
public class CourseController {

    // 注入课程信息服务层接口实现类
    @Resource
    private ICourseService courseService;

    /**
     * 查询课程信息列表
     *
     * @return 全局通用返回结果(课程信息所有数据})
     */
    @Operation(summary = "查询课程信息列表", description = "查询所有课程信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<Course>> list() {
        // 调用课程信息服务层接口查询所有课程信息信息
        List<Course> list = courseService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程信息
     *
     * @param id 课程信息主键Id
     * @return 全局通用返回结果(课程信息实体数据)
     */
    @Operation(summary = "根据Id查询课程信息", description = "根据主键Id查询课程信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<Course> getById(@Parameter(description = "课程信息Id") @PathVariable("id") Long id) {
        // 调用课程信息服务层接口根据Id查询课程信息信息
        Course entity = courseService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程信息
     *
     * @param course 课程信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程信息", description = "新增课程信息信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody Course course) {
        // 调用课程信息服务层接口保存课程信息信息
        courseService.save(course);
        return ApiResponse.success();
    }

    /**
     * 修改课程信息
     *
     * @param course 课程信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程信息", description = "修改课程信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Course course) {
        // 调用课程信息服务层接口修改课程信息信息
        courseService.updateById(course);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程信息
     *
     * @param id 课程信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程信息", description = "根据Id删除课程信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "课程信息Id") @PathVariable("id") Long id) {
        // 调用课程信息服务层接口根据Id删除课程信息信息
        courseService.removeById(id);
        return ApiResponse.success();
    }
}