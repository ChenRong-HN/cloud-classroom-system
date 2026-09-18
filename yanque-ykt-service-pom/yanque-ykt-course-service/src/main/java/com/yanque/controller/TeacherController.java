package com.yanque.controller;

import java.util.List;
import java.util.Map;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.ApiResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.Course;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.Teacher;
import com.yanque.service.ITeacherService;
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
 * 老师控制层接口
 *
 * @author cr
 */
@Tag(name = "老师管理", description = "老师接口")
@RestController
@RequestMapping("/course/teacher")
public class TeacherController {

    // 注入老师服务层接口实现类
    @Resource
    private ITeacherService teacherService;

    /**
     * 查询老师列表
     *
     * @return 全局通用返回结果(老师所有数据})
     */
    @Operation(summary = "查询老师列表", description = "查询所有老师信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<Teacher>> list() {
        // 调用老师服务层接口查询所有老师信息
        List<Teacher> list = teacherService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询老师
     *
     * @param id 老师主键Id
     * @return 全局通用返回结果(老师实体数据)
     */
    @Operation(summary = "根据Id查询老师", description = "根据主键Id查询老师详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<Teacher> getById(@Parameter(description = "老师Id") @PathVariable("id") Long id) {
        // 调用老师服务层接口根据Id查询老师信息
        Teacher entity = teacherService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增老师
     *
     * @param teacher 老师实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增老师", description = "新增老师信息")
    @PostMapping("/save_teacher")
    public ApiResponse<Void> save(@RequestBody Teacher teacher) {
        // 调用老师服务层接口保存老师信息
        teacherService.save(teacher);
        return ApiResponse.success();
    }

    /**
     * 修改老师
     *
     * @param teacher 老师实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改老师", description = "修改老师信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Teacher teacher) {
        // 调用老师服务层接口修改老师信息
        teacherService.updateById(teacher);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除老师
     *
     * @param id 老师主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除老师", description = "根据Id删除老师信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "老师Id") @PathVariable("id") Long id) {
        // 调用老师服务层接口根据Id删除老师信息
        teacherService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 讲师信息分页查询
     *
     * @param basicPageVo 分页查询参数模型
     * @return 全局通用返回结果
     */
    @Operation(summary = "讲师信息分页查询", description = "讲师信息分页查询")
    @PostMapping("/pagelist")
    public ApiResponse<ApiPageResponse<Teacher>> pageList(@RequestBody BasicPageVo basicPageVo) {
        ApiPageResponse<Teacher> pageR = teacherService.pageList(basicPageVo);
        return ApiResponse.success(pageR);
    }
}