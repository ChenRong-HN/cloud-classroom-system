package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseTeacher;
import com.yanque.service.ICourseTeacherService;
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
 * 课程老师中间控制层接口
 *
 * @author cr
 */
@Tag(name = "课程老师中间管理", description = "课程老师中间接口")
@RestController
@RequestMapping("/course/courseTeacher")
public class CourseTeacherController {

    // 注入课程老师中间服务层接口实现类
    @Resource
    private ICourseTeacherService courseTeacherService;

    /**
     * 查询课程老师中间列表
     *
     * @return 全局通用返回结果(课程老师中间所有数据})
     */
    @Operation(summary = "查询课程老师中间列表", description = "查询所有课程老师中间信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<CourseTeacher>> list() {
        // 调用课程老师中间服务层接口查询所有课程老师中间信息
        List<CourseTeacher> list = courseTeacherService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程老师中间
     *
     * @param id 课程老师中间主键Id
     * @return 全局通用返回结果(课程老师中间实体数据)
     */
    @Operation(summary = "根据Id查询课程老师中间", description = "根据主键Id查询课程老师中间详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<CourseTeacher> getById(@Parameter(description = "课程老师中间Id") @PathVariable("id") Long id) {
        // 调用课程老师中间服务层接口根据Id查询课程老师中间信息
        CourseTeacher entity = courseTeacherService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程老师中间
     *
     * @param courseTeacher 课程老师中间实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程老师中间", description = "新增课程老师中间信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseTeacher courseTeacher) {
        // 调用课程老师中间服务层接口保存课程老师中间信息
        courseTeacherService.save(courseTeacher);
        return ApiResponse.success();
    }

    /**
     * 修改课程老师中间
     *
     * @param courseTeacher 课程老师中间实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程老师中间", description = "修改课程老师中间信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseTeacher courseTeacher) {
        // 调用课程老师中间服务层接口修改课程老师中间信息
        courseTeacherService.updateById(courseTeacher);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程老师中间
     *
     * @param id 课程老师中间主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程老师中间", description = "根据Id删除课程老师中间信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "课程老师中间Id") @PathVariable("id") Long id) {
        // 调用课程老师中间服务层接口根据Id删除课程老师中间信息
        courseTeacherService.removeById(id);
        return ApiResponse.success();
    }
}