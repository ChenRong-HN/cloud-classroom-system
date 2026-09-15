package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.UserGrowLog;
import com.yanque.service.IUserGrowLogService;
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
 * 会员成长值记录控制层接口
 *
 * @author cr
 */
@Tag(name = "会员成长值记录管理", description = "会员成长值记录接口")
@RestController
@RequestMapping("/user/growLog")
public class UserGrowLogController {

    // 注入会员成长值记录服务层接口实现类
    @Resource
    private IUserGrowLogService userGrowLogService;

    /**
     * 查询会员成长值记录列表
     *
     * @return 全局通用返回结果(会员成长值记录所有数据})
     */
    @Operation(summary = "查询会员成长值记录列表", description = "查询所有会员成长值记录信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<UserGrowLog>> list() {
        // 调用会员成长值记录服务层接口查询所有会员成长值记录信息
        List<UserGrowLog> list = userGrowLogService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询会员成长值记录
     *
     * @param id 会员成长值记录主键Id
     * @return 全局通用返回结果(会员成长值记录实体数据)
     */
    @Operation(summary = "根据Id查询会员成长值记录", description = "根据主键Id查询会员成长值记录详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<UserGrowLog> getById(@Parameter(description = "会员成长值记录Id") @PathVariable("id") Long id) {
        // 调用会员成长值记录服务层接口根据Id查询会员成长值记录信息
        UserGrowLog entity = userGrowLogService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增会员成长值记录
     *
     * @param userGrowLog 会员成长值记录实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增会员成长值记录", description = "新增会员成长值记录信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody UserGrowLog userGrowLog) {
        // 调用会员成长值记录服务层接口保存会员成长值记录信息
        userGrowLogService.save(userGrowLog);
        return ApiResponse.success();
    }

    /**
     * 修改会员成长值记录
     *
     * @param userGrowLog 会员成长值记录实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改会员成长值记录", description = "修改会员成长值记录信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody UserGrowLog userGrowLog) {
        // 调用会员成长值记录服务层接口修改会员成长值记录信息
        userGrowLogService.updateById(userGrowLog);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除会员成长值记录
     *
     * @param id 会员成长值记录主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除会员成长值记录", description = "根据Id删除会员成长值记录信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "会员成长值记录Id") @PathVariable("id") Long id) {
        // 调用会员成长值记录服务层接口根据Id删除会员成长值记录信息
        userGrowLogService.removeById(id);
        return ApiResponse.success();
    }
}