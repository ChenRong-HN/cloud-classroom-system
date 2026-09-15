package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseUserLearn;
import com.yanque.service.ICourseUserLearnService;
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
 * 用户课程学习控制层接口
 *
 * @author cr
 */
@Tag(name = "用户课程学习管理", description = "用户课程学习接口")
@RestController
@RequestMapping("/course/userLearn")
public class CourseUserLearnController {

    // 注入用户课程学习服务层接口实现类
    @Resource
    private ICourseUserLearnService courseUserLearnService;

    /**
     * 查询用户课程学习列表
     *
     * @return 全局通用返回结果(用户课程学习所有数据})
     */
    @Operation(summary = "查询用户课程学习列表", description = "查询所有用户课程学习信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<CourseUserLearn>> list() {
        // 调用用户课程学习服务层接口查询所有用户课程学习信息
        List<CourseUserLearn> list = courseUserLearnService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询用户课程学习
     *
     * @param id 用户课程学习主键Id
     * @return 全局通用返回结果(用户课程学习实体数据)
     */
    @Operation(summary = "根据Id查询用户课程学习", description = "根据主键Id查询用户课程学习详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<CourseUserLearn> getById(@Parameter(description = "用户课程学习Id") @PathVariable("id") Long id) {
        // 调用用户课程学习服务层接口根据Id查询用户课程学习信息
        CourseUserLearn entity = courseUserLearnService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增用户课程学习
     *
     * @param courseUserLearn 用户课程学习实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增用户课程学习", description = "新增用户课程学习信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseUserLearn courseUserLearn) {
        // 调用用户课程学习服务层接口保存用户课程学习信息
        courseUserLearnService.save(courseUserLearn);
        return ApiResponse.success();
    }

    /**
     * 修改用户课程学习
     *
     * @param courseUserLearn 用户课程学习实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改用户课程学习", description = "修改用户课程学习信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseUserLearn courseUserLearn) {
        // 调用用户课程学习服务层接口修改用户课程学习信息
        courseUserLearnService.updateById(courseUserLearn);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除用户课程学习
     *
     * @param id 用户课程学习主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除用户课程学习", description = "根据Id删除用户课程学习信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "用户课程学习Id") @PathVariable("id") Long id) {
        // 调用用户课程学习服务层接口根据Id删除用户课程学习信息
        courseUserLearnService.removeById(id);
        return ApiResponse.success();
    }
}