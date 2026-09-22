package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseOrderItem;
import com.yanque.service.ICourseOrderItemService;
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
 * 订单详情控制层接口
 *
 * @author x1angwan
 */
@Tag(name = "订单详情管理", description = "订单详情接口")
@RestController
@RequestMapping("/order/orderitem")
public class CourseOrderItemController {

    // 注入订单详情服务层接口实现类
    @Resource
    private ICourseOrderItemService courseOrderItemService;

    /**
     * 查询订单详情列表
     *
     * @return 全局通用返回结果(订单详情所有数据})
     */
    @Operation(summary = "查询订单详情列表", description = "查询所有订单详情信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<CourseOrderItem>> list() {
        // 调用订单详情服务层接口查询所有订单详情信息
        List<CourseOrderItem> list = courseOrderItemService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询订单详情
     *
     * @param id 订单详情主键Id
     * @return 全局通用返回结果(订单详情实体数据)
     */
    @Operation(summary = "根据Id查询订单详情", description = "根据主键Id查询订单详情详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<CourseOrderItem> getById(@Parameter(description = "订单详情Id") @PathVariable("id") Long id) {
        // 调用订单详情服务层接口根据Id查询订单详情信息
        CourseOrderItem entity = courseOrderItemService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增订单详情
     *
     * @param courseOrderItem 订单详情实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增订单详情", description = "新增订单详情信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseOrderItem courseOrderItem) {
        // 调用订单详情服务层接口保存订单详情信息
        courseOrderItemService.save(courseOrderItem);
        return ApiResponse.success();
    }

    /**
     * 修改订单详情
     *
     * @param courseOrderItem 订单详情实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改订单详情", description = "修改订单详情信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseOrderItem courseOrderItem) {
        // 调用订单详情服务层接口修改订单详情信息
        courseOrderItemService.updateById(courseOrderItem);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除订单详情
     *
     * @param id 订单详情主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除订单详情", description = "根据Id删除订单详情信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "订单详情Id") @PathVariable("id") Long id) {
        // 调用订单详情服务层接口根据Id删除订单详情信息
        courseOrderItemService.removeById(id);
        return ApiResponse.success();
    }
}