package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseCollect;
import com.yanque.service.ICourseCollectService;
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
 * 课程收藏控制层接口
 *
 * @author cr
 */
@Tag(name = "课程收藏管理", description = "课程收藏接口")
@RestController
@RequestMapping("/course/collect")
public class CourseCollectController {

    // 注入课程收藏服务层接口实现类
    @Resource
    private ICourseCollectService courseCollectService;

    /**
     * 查询课程收藏列表
     *
     * @return 全局通用返回结果(课程收藏所有数据})
     */
    @Operation(summary = "查询课程收藏列表", description = "查询所有课程收藏信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<CourseCollect>> list() {
        // 调用课程收藏服务层接口查询所有课程收藏信息
        List<CourseCollect> list = courseCollectService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程收藏
     *
     * @param id 课程收藏主键Id
     * @return 全局通用返回结果(课程收藏实体数据)
     */
    @Operation(summary = "根据Id查询课程收藏", description = "根据主键Id查询课程收藏详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<CourseCollect> getById(@Parameter(description = "课程收藏Id") @PathVariable("id") Long id) {
        // 调用课程收藏服务层接口根据Id查询课程收藏信息
        CourseCollect entity = courseCollectService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程收藏
     *
     * @param courseCollect 课程收藏实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程收藏", description = "新增课程收藏信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseCollect courseCollect) {
        // 调用课程收藏服务层接口保存课程收藏信息
        courseCollectService.save(courseCollect);
        return ApiResponse.success();
    }

    /**
     * 修改课程收藏
     *
     * @param courseCollect 课程收藏实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程收藏", description = "修改课程收藏信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseCollect courseCollect) {
        // 调用课程收藏服务层接口修改课程收藏信息
        courseCollectService.updateById(courseCollect);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程收藏
     *
     * @param id 课程收藏主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程收藏", description = "根据Id删除课程收藏信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "课程收藏Id") @PathVariable("id") Long id) {
        // 调用课程收藏服务层接口根据Id删除课程收藏信息
        courseCollectService.removeById(id);
        return ApiResponse.success();
    }
}