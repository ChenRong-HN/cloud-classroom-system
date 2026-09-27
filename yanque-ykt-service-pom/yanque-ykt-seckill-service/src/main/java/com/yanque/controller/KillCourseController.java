package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.ApiResponse;
import com.yanque.common.vo.BasicPageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.KillCourse;
import com.yanque.service.IKillCourseService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀课程信息控制层接口
 *
 * @author cr
 */
@Tag(name = "秒杀课程信息管理", description = "秒杀课程信息接口")
@RestController
@RequestMapping("/kill/killCourse")
public class KillCourseController {

    // 注入秒杀课程信息服务层接口实现类
    @Resource
    private IKillCourseService killCourseService;

    /**
     * 查询秒杀课程信息列表
     *
     * @return 全局通用返回结果(秒杀课程信息所有数据})
     */
    @Operation(summary = "查询秒杀课程信息列表", description = "查询所有秒杀课程信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<KillCourse>> list() {
        // 调用秒杀课程信息服务层接口查询所有秒杀课程信息信息
        List<KillCourse> list = killCourseService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询秒杀课程信息
     *
     * @param id 秒杀课程信息主键Id
     * @return 全局通用返回结果(秒杀课程信息实体数据)
     */
    @Operation(summary = "根据Id查询秒杀课程信息", description = "根据主键Id查询秒杀课程信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<KillCourse> getById(@Parameter(description = "秒杀课程信息Id") @PathVariable("id") Long id) {
        // 调用秒杀课程信息服务层接口根据Id查询秒杀课程信息信息
        KillCourse entity = killCourseService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增秒杀课程信息
     *
     * @param killCourse 秒杀课程信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增秒杀课程信息", description = "新增秒杀课程信息信息")
    @PostMapping("/save")
    public ApiResponse<Void> save(@Valid @RequestBody KillCourse killCourse) {
        // 调用秒杀课程信息服务层接口保存秒杀课程信息信息
        killCourseService.save(killCourse);
        return ApiResponse.success();
    }

    /**
     * 修改秒杀课程信息
     *
     * @param killCourse 秒杀课程信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改秒杀课程信息", description = "修改秒杀课程信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody KillCourse killCourse) {
        // 调用秒杀课程信息服务层接口修改秒杀课程信息信息
        killCourseService.updateById(killCourse);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除秒杀课程信息
     *
     * @param id 秒杀课程信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除秒杀课程信息", description = "根据Id删除秒杀课程信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "秒杀课程信息Id") @PathVariable("id") Long id) {
        // 调用秒杀课程信息服务层接口根据Id删除秒杀课程信息信息
        killCourseService.removeById(id);
        return ApiResponse.success();
    }

    @PostMapping("/pagelist")
    public ApiResponse<ApiPageResponse<KillCourse>> pagelist(@RequestBody BasicPageVo basicPageVo) {
        // 调用秒杀课程信息服务层接口分页查询秒杀课程信息
        return ApiResponse.success(killCourseService.pagelist(basicPageVo));
    }
}