package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseResource;
import com.yanque.service.ICourseResourceService;
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
 * 课件信息控制层接口
 *
 * @author cr
 */
@Tag(name = "课件信息管理", description = "课件信息接口")
@RestController
@RequestMapping("/course/resource")
public class CourseResourceController {

    // 注入课件信息服务层接口实现类
    @Resource
    private ICourseResourceService courseResourceService;

    /**
     * 查询课件信息列表
     *
     * @return 全局通用返回结果(课件信息所有数据})
     */
    @Operation(summary = "查询课件信息列表", description = "查询所有课件信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<CourseResource>> list() {
        // 调用课件信息服务层接口查询所有课件信息信息
        List<CourseResource> list = courseResourceService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课件信息
     *
     * @param id 课件信息主键Id
     * @return 全局通用返回结果(课件信息实体数据)
     */
    @Operation(summary = "根据Id查询课件信息", description = "根据主键Id查询课件信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<CourseResource> getById(@Parameter(description = "课件信息Id") @PathVariable("id") Long id) {
        // 调用课件信息服务层接口根据Id查询课件信息信息
        CourseResource entity = courseResourceService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课件信息
     *
     * @param courseResource 课件信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课件信息", description = "新增课件信息信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseResource courseResource) {
        // 调用课件信息服务层接口保存课件信息信息
        courseResourceService.save(courseResource);
        return ApiResponse.success();
    }

    /**
     * 修改课件信息
     *
     * @param courseResource 课件信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课件信息", description = "修改课件信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseResource courseResource) {
        // 调用课件信息服务层接口修改课件信息信息
        courseResourceService.updateById(courseResource);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课件信息
     *
     * @param id 课件信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课件信息", description = "根据Id删除课件信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "课件信息Id") @PathVariable("id") Long id) {
        // 调用课件信息服务层接口根据Id删除课件信息信息
        courseResourceService.removeById(id);
        return ApiResponse.success();
    }
}