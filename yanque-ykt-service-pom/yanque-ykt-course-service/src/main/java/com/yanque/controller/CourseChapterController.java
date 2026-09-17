package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.ApiResponse;
import com.yanque.common.vo.BasicPageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseChapter;
import com.yanque.service.ICourseChapterService;
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
 * 课程章节控制层接口
 *
 * @author cr
 */
@Tag(name = "课程章节管理", description = "课程章节接口")
@RestController
@RequestMapping("/course/courseChapter")
public class CourseChapterController {

    // 注入课程章节服务层接口实现类
    @Resource
    private ICourseChapterService courseChapterService;

    /**
     * 查询课程章节列表
     *
     * @return 全局通用返回结果(课程章节所有数据})
     */
    @Operation(summary = "查询课程章节列表", description = "查询所有课程章节信息列表")
    @GetMapping("/list")
    public ApiResponse<List<CourseChapter>> list() {
        // 调用课程章节服务层接口查询所有课程章节信息
        List<CourseChapter> list = courseChapterService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程章节
     *
     * @param id 课程章节主键Id
     * @return 全局通用返回结果(课程章节实体数据)
     */
    @Operation(summary = "根据Id查询课程章节", description = "根据主键Id查询课程章节详细信息")
    @GetMapping("/{id}")
    public ApiResponse<CourseChapter> getById(@Parameter(description = "课程章节Id") @PathVariable("id") Long id) {
        // 调用课程章节服务层接口根据Id查询课程章节信息
        CourseChapter entity = courseChapterService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程章节
     *
     * @param courseChapter 课程章节实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程章节", description = "新增课程章节信息")
    @PostMapping("/save")
    public ApiResponse<Void> save(@RequestBody CourseChapter courseChapter) {
        // 调用课程章节服务层接口保存课程章节信息
        courseChapterService.save(courseChapter);
        return ApiResponse.success();
    }

    /**
     * 修改课程章节
     *
     * @param courseChapter 课程章节实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程章节", description = "修改课程章节信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseChapter courseChapter) {
        // 调用课程章节服务层接口修改课程章节信息
        courseChapterService.updateById(courseChapter);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程章节
     *
     * @param id 课程章节主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程章节", description = "根据Id删除课程章节信息")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "课程章节Id") @PathVariable("id") Long id) {
        // 调用课程章节服务层接口根据Id删除课程章节信息
        courseChapterService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 基于条件查询课程章节的分页数据
     *
     * @param basicPageVo 基础分页参数模型
     * @return 全局通用返回结果
     */
    @Operation(summary = "查询课程章节的分页数据", description = "查询课程章节的分页数据")
    @PostMapping("/pagelist")
    public ApiResponse<ApiPageResponse<CourseChapter>> pageList(@RequestBody BasicPageVo basicPageVo) {
        ApiPageResponse<CourseChapter> pageR = courseChapterService.pageList(basicPageVo);
        return ApiResponse.success(pageR);
    }
}