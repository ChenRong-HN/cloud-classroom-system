package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.vo.UserReqVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.User;
import com.yanque.service.IUserService;
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
 * 会员登录账号控制层接口
 *
 * @author cr
 */
@Tag(name = "会员登录账号管理", description = "会员登录账号接口")
@RestController
@RequestMapping("/user/user")
public class UserController {

    // 注入会员登录账号服务层接口实现类
    @Resource
    private IUserService userService;

    /**
     * 查询会员登录账号列表
     *
     * @return 全局通用返回结果(会员登录账号所有数据})
     */
    @Operation(summary = "查询会员登录账号列表", description = "查询所有会员登录账号信息列表")
    @GetMapping("/list")
    public ApiResponse<List<User>> list() {
        // 调用会员登录账号服务层接口查询所有会员登录账号信息
        List<User> list = userService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询会员登录账号
     *
     * @param id 会员登录账号主键Id
     * @return 全局通用返回结果(会员登录账号实体数据)
     */
    @Operation(summary = "根据Id查询会员登录账号", description = "根据主键Id查询会员登录账号详细信息")
    @GetMapping("/{id}")
    public ApiResponse<User> getById(@Parameter(description = "会员登录账号Id") @PathVariable("id") Long id) {
        // 调用会员登录账号服务层接口根据Id查询会员登录账号信息
        User entity = userService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增会员登录账号
     *
     * @param user 会员登录账号实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增会员登录账号", description = "新增会员登录账号信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody User user) {
        // 调用会员登录账号服务层接口保存会员登录账号信息
        userService.save(user);
        return ApiResponse.success();
    }

    /**
     * 修改会员登录账号
     *
     * @param user 会员登录账号实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改会员登录账号", description = "修改会员登录账号信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody User user) {
        // 调用会员登录账号服务层接口修改会员登录账号信息
        userService.updateById(user);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除会员登录账号
     *
     * @param id 会员登录账号主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除会员登录账号", description = "根据Id删除会员登录账号信息")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "会员登录账号Id") @PathVariable("id") Long id) {
        // 调用会员登录账号服务层接口根据Id删除会员登录账号信息
        userService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 用户注册
     *
     * @param userReqVo 用户注册信息
     * @return 全局统一返回结果
     */
    @Operation(summary = "用户注册", description = "用户注册")
    @PostMapping("/register")
    public ApiResponse<Void> register(@Valid @RequestBody UserReqVo userReqVo) {
        userService.register(userReqVo);
        return ApiResponse.success();
    }
}