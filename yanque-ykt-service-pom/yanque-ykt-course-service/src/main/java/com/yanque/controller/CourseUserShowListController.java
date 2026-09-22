package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.vo.CourseQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseUserShowList;
import com.yanque.service.ICourseUserShowListService;
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
 * 用户端课程展示列控制层接口
 *
 * @author cr
 */
@Tag(name = "用户端课程展示列管理", description = "用户端课程展示列接口")
@RestController
@RequestMapping("/course/courseUserShowList")
public class CourseUserShowListController {

    // 注入用户端课程展示列服务层接口实现类
    @Resource
    private ICourseUserShowListService courseUserShowListService;

    /**
     * 查询用户端课程展示列列表
     *
     * @return 全局通用返回结果(用户端课程展示列所有数据})
     */
    @Operation(summary = "查询用户端课程展示列列表", description = "查询所有用户端课程展示列信息列表")
    @GetMapping("/list")
    public ApiResponse<List<CourseUserShowList>> list() {
        // 调用用户端课程展示列服务层接口查询所有用户端课程展示列信息
        List<CourseUserShowList> list = courseUserShowListService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询用户端课程展示列
     *
     * @param id 用户端课程展示列主键Id
     * @return 全局通用返回结果(用户端课程展示列实体数据)
     */
    @Operation(summary = "根据Id查询用户端课程展示列", description = "根据主键Id查询用户端课程展示列详细信息")
    @GetMapping("/{id}")
    public ApiResponse<CourseUserShowList> getById(@Parameter(description = "用户端课程展示列Id") @PathVariable("id") Long id) {
        // 调用用户端课程展示列服务层接口根据Id查询用户端课程展示列信息
        CourseUserShowList entity = courseUserShowListService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增用户端课程展示列
     *
     * @param courseUserShowList 用户端课程展示列实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增用户端课程展示列", description = "新增用户端课程展示列信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseUserShowList courseUserShowList) {
        // 调用用户端课程展示列服务层接口保存用户端课程展示列信息
        courseUserShowListService.save(courseUserShowList);
        return ApiResponse.success();
    }

    /**
     * 修改用户端课程展示列
     *
     * @param courseUserShowList 用户端课程展示列实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改用户端课程展示列", description = "修改用户端课程展示列信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseUserShowList courseUserShowList) {
        // 调用用户端课程展示列服务层接口修改用户端课程展示列信息
        courseUserShowListService.updateById(courseUserShowList);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除用户端课程展示列
     *
     * @param id 用户端课程展示列主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除用户端课程展示列", description = "根据Id删除用户端课程展示列信息")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "用户端课程展示列Id") @PathVariable("id") Long id) {
        // 调用用户端课程展示列服务层接口根据Id删除用户端课程展示列信息
        courseUserShowListService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 分页查询用户端课程信息分页数据
     *
     * @param courseQueryVo 用户端课程查询参数VO
     * @return 全局通用返回结果
     */
    @Operation(summary = "分页查询用户端课程信息分页数据", description = "分页查询用户端课程信息分页数据")
    @PostMapping("/pagelist")
    public ApiResponse<ApiPageResponse<CourseUserShowList>> pageList(@RequestBody CourseQueryVo courseQueryVo) {
        ApiPageResponse<CourseUserShowList> pageR = courseUserShowListService.pageList(courseQueryVo);
        return ApiResponse.success(pageR);
    }
}