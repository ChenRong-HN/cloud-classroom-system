package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseDetail;
import com.yanque.service.ICourseDetailService;
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
 * 课程详情控制层接口
 *
 * @author cr
 */
@Tag(name = "课程详情管理", description = "课程详情接口")
@RestController
@RequestMapping("/course/courseDetail")
public class CourseDetailController {

    // 注入课程详情服务层接口实现类
    @Resource
    private ICourseDetailService courseDetailService;

    /**
     * 查询课程详情列表
     *
     * @return 全局通用返回结果(课程详情所有数据})
     */
    @Operation(summary = "查询课程详情列表", description = "查询所有课程详情信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<CourseDetail>> list() {
        // 调用课程详情服务层接口查询所有课程详情信息
        List<CourseDetail> list = courseDetailService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程详情
     *
     * @param id 课程详情主键Id
     * @return 全局通用返回结果(课程详情实体数据)
     */
    @Operation(summary = "根据Id查询课程详情", description = "根据主键Id查询课程详情详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<CourseDetail> getById(@Parameter(description = "课程详情Id") @PathVariable("id") Long id) {
        // 调用课程详情服务层接口根据Id查询课程详情信息
        CourseDetail entity = courseDetailService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程详情
     *
     * @param courseDetail 课程详情实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程详情", description = "新增课程详情信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseDetail courseDetail) {
        // 调用课程详情服务层接口保存课程详情信息
        courseDetailService.save(courseDetail);
        return ApiResponse.success();
    }

    /**
     * 修改课程详情
     *
     * @param courseDetail 课程详情实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程详情", description = "修改课程详情信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseDetail courseDetail) {
        // 调用课程详情服务层接口修改课程详情信息
        courseDetailService.updateById(courseDetail);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程详情
     *
     * @param id 课程详情主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程详情", description = "根据Id删除课程详情信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "课程详情Id") @PathVariable("id") Long id) {
        // 调用课程详情服务层接口根据Id删除课程详情信息
        courseDetailService.removeById(id);
        return ApiResponse.success();
    }
}