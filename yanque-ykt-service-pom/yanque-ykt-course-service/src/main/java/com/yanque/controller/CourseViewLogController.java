package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseViewLog;
import com.yanque.service.ICourseViewLogService;
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
 * 课程浏览记录控制层接口
 *
 * @author cr
 */
@Tag(name = "课程浏览记录管理", description = "课程浏览记录接口")
@RestController
@RequestMapping("/course/courseViewLog")
public class CourseViewLogController {

    // 注入课程浏览记录服务层接口实现类
    @Resource
    private ICourseViewLogService courseViewLogService;

    /**
     * 查询课程浏览记录列表
     *
     * @return 全局通用返回结果(课程浏览记录所有数据})
     */
    @Operation(summary = "查询课程浏览记录列表", description = "查询所有课程浏览记录信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<CourseViewLog>> list() {
        // 调用课程浏览记录服务层接口查询所有课程浏览记录信息
        List<CourseViewLog> list = courseViewLogService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程浏览记录
     *
     * @param id 课程浏览记录主键Id
     * @return 全局通用返回结果(课程浏览记录实体数据)
     */
    @Operation(summary = "根据Id查询课程浏览记录", description = "根据主键Id查询课程浏览记录详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<CourseViewLog> getById(@Parameter(description = "课程浏览记录Id") @PathVariable("id") Long id) {
        // 调用课程浏览记录服务层接口根据Id查询课程浏览记录信息
        CourseViewLog entity = courseViewLogService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程浏览记录
     *
     * @param courseViewLog 课程浏览记录实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程浏览记录", description = "新增课程浏览记录信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseViewLog courseViewLog) {
        // 调用课程浏览记录服务层接口保存课程浏览记录信息
        courseViewLogService.save(courseViewLog);
        return ApiResponse.success();
    }

    /**
     * 修改课程浏览记录
     *
     * @param courseViewLog 课程浏览记录实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程浏览记录", description = "修改课程浏览记录信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseViewLog courseViewLog) {
        // 调用课程浏览记录服务层接口修改课程浏览记录信息
        courseViewLogService.updateById(courseViewLog);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程浏览记录
     *
     * @param id 课程浏览记录主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程浏览记录", description = "根据Id删除课程浏览记录信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "课程浏览记录Id") @PathVariable("id") Long id) {
        // 调用课程浏览记录服务层接口根据Id删除课程浏览记录信息
        courseViewLogService.removeById(id);
        return ApiResponse.success();
    }
}