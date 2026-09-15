package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseMarket;
import com.yanque.service.ICourseMarketService;
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
 * 课程销售信息控制层接口
 *
 * @author cr
 */
@Tag(name = "课程销售信息管理", description = "课程销售信息接口")
@RestController
@RequestMapping("/course/market")
public class CourseMarketController {

    // 注入课程销售信息服务层接口实现类
    @Resource
    private ICourseMarketService courseMarketService;

    /**
     * 查询课程销售信息列表
     *
     * @return 全局通用返回结果(课程销售信息所有数据})
     */
    @Operation(summary = "查询课程销售信息列表", description = "查询所有课程销售信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<CourseMarket>> list() {
        // 调用课程销售信息服务层接口查询所有课程销售信息信息
        List<CourseMarket> list = courseMarketService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程销售信息
     *
     * @param id 课程销售信息主键Id
     * @return 全局通用返回结果(课程销售信息实体数据)
     */
    @Operation(summary = "根据Id查询课程销售信息", description = "根据主键Id查询课程销售信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<CourseMarket> getById(@Parameter(description = "课程销售信息Id") @PathVariable("id") Long id) {
        // 调用课程销售信息服务层接口根据Id查询课程销售信息信息
        CourseMarket entity = courseMarketService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程销售信息
     *
     * @param courseMarket 课程销售信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程销售信息", description = "新增课程销售信息信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseMarket courseMarket) {
        // 调用课程销售信息服务层接口保存课程销售信息信息
        courseMarketService.save(courseMarket);
        return ApiResponse.success();
    }

    /**
     * 修改课程销售信息
     *
     * @param courseMarket 课程销售信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程销售信息", description = "修改课程销售信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseMarket courseMarket) {
        // 调用课程销售信息服务层接口修改课程销售信息信息
        courseMarketService.updateById(courseMarket);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程销售信息
     *
     * @param id 课程销售信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程销售信息", description = "根据Id删除课程销售信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "课程销售信息Id") @PathVariable("id") Long id) {
        // 调用课程销售信息服务层接口根据Id删除课程销售信息信息
        courseMarketService.removeById(id);
        return ApiResponse.success();
    }
}