package com.yanque.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.Employee;
import com.yanque.service.IEmployeeService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工信息控制层接口
 *
 * @author cr
 */
@Tag(name = "员工信息管理", description = "员工信息接口")
@RestController
@RequestMapping("/system/employee")
public class EmployeeController {

    // 注入员工信息服务层接口实现类
    @Resource
    private IEmployeeService employeeService;

    /**
     * 查询员工信息列表
     *
     * @return 全局通用返回结果(员工信息所有数据})
     */
    @Operation(summary = "查询员工信息列表", description = "查询所有员工信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<Employee>> list() {
        // 调用员工信息服务层接口查询所有员工信息信息
        List<Employee> list = employeeService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询员工信息
     *
     * @param id 员工信息主键Id
     * @return 全局通用返回结果(员工信息实体数据)
     */
    @Operation(summary = "根据Id查询员工信息", description = "根据主键Id查询员工信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<Employee> getById(@Parameter(description = "员工信息Id") @PathVariable("id") Long id) {
        // 调用员工信息服务层接口根据Id查询员工信息信息
        Employee entity = employeeService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增员工信息
     *
     * @param employee 员工信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增员工信息", description = "新增员工信息信息")
    @PostMapping
    public ApiResponse<Void> save(@Valid @RequestBody Employee employee) {
        // 调用员工信息服务层接口保存员工信息信息
        employeeService.save(employee);
        return ApiResponse.success();
    }

    /**
     * 修改员工信息
     *
     * @param employee 员工信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改员工信息", description = "修改员工信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Employee employee) {
        // 调用员工信息服务层接口修改员工信息信息
        employeeService.updateById(employee);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除员工信息
     *
     * @param id 员工信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除员工信息", description = "根据Id删除员工信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "员工信息Id") @PathVariable("id") Long id) {
        // 调用员工信息服务层接口根据Id删除员工信息信息
        employeeService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 根据状态查询员工信息
     *
     * @param state 员工信息状态
     * @return 全局通用返回结果(员工信息实体数据)
     */
    @Operation(summary = "根据状态查询员工信息", description = "根据状态查询员工信息列表")
    @GetMapping("/selectByState/{state}")
    public ApiResponse<List<Employee>> selectByState(@PathVariable @Min(value = 0, message = "员工信息状态非法") @Max(value = 2, message = "员工信息状态非法")
                                                     @Parameter(description = "员工信息状态") Long state) {
        // 封装查询条件后查询员工数据
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = Wrappers.<Employee>lambdaQuery().eq(Employee::getState, state);
        List<Employee> list = employeeService.list(employeeLambdaQueryWrapper);
        return ApiResponse.success(list);
    }
}