package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseSummary;
import com.yanque.service.ICourseSummaryService;
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
 * 课程统计控制层接口
 *
 * @author cr
 */
@Tag(name = "课程统计管理", description = "课程统计接口")
@RestController
@RequestMapping("/course/summary")
public class CourseSummaryController {

    // 注入课程统计服务层接口实现类
    @Resource
    private ICourseSummaryService courseSummaryService;

    /**
     * 查询课程统计列表
     *
     * @return 全局通用返回结果(课程统计所有数据})
     */
    @Operation(summary = "查询课程统计列表", description = "查询所有课程统计信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<CourseSummary>> list() {
        // 调用课程统计服务层接口查询所有课程统计信息
        List<CourseSummary> list = courseSummaryService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程统计
     *
     * @param id 课程统计主键Id
     * @return 全局通用返回结果(课程统计实体数据)
     */
    @Operation(summary = "根据Id查询课程统计", description = "根据主键Id查询课程统计详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<CourseSummary> getById(@Parameter(description = "课程统计Id") @PathVariable("id") Long id) {
        // 调用课程统计服务层接口根据Id查询课程统计信息
        CourseSummary entity = courseSummaryService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程统计
     *
     * @param courseSummary 课程统计实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程统计", description = "新增课程统计信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseSummary courseSummary) {
        // 调用课程统计服务层接口保存课程统计信息
        courseSummaryService.save(courseSummary);
        return ApiResponse.success();
    }

    /**
     * 修改课程统计
     *
     * @param courseSummary 课程统计实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程统计", description = "修改课程统计信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseSummary courseSummary) {
        // 调用课程统计服务层接口修改课程统计信息
        courseSummaryService.updateById(courseSummary);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程统计
     *
     * @param id 课程统计主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程统计", description = "根据Id删除课程统计信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "课程统计Id") @PathVariable("id") Long id) {
        // 调用课程统计服务层接口根据Id删除课程统计信息
        courseSummaryService.removeById(id);
        return ApiResponse.success();
    }
}