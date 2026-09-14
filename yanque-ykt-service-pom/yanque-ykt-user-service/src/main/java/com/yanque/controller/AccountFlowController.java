package com.yanque.controller;

import java.util.List;

import com.yanque.entity.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.AccountFlow;
import com.yanque.service.IAccountFlowService;
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
 * 会员账户流水控制层接口
 *
 * @author cr
 */
@Tag(name = "会员账户流水管理", description = "会员账户流水接口")
@RestController
@RequestMapping("/user/flow")
public class AccountFlowController {

    // 注入会员账户流水服务层接口实现类
    @Resource
    private IAccountFlowService accountFlowService;

    /**
     * 查询会员账户流水列表
     *
     * @return 全局通用返回结果(会员账户流水所有数据})
     */
    @Operation(summary = "查询会员账户流水列表", description = "查询所有会员账户流水信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<AccountFlow>> list() {
        // 调用会员账户流水服务层接口查询所有会员账户流水信息
        List<AccountFlow> list = accountFlowService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询会员账户流水
     *
     * @param id 会员账户流水主键Id
     * @return 全局通用返回结果(会员账户流水实体数据)
     */
    @Operation(summary = "根据Id查询会员账户流水", description = "根据主键Id查询会员账户流水详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<AccountFlow> getById(@Parameter(description = "会员账户流水Id") @PathVariable("id") Long id) {
        // 调用会员账户流水服务层接口根据Id查询会员账户流水信息
        AccountFlow entity = accountFlowService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增会员账户流水
     *
     * @param accountFlow 会员账户流水实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增会员账户流水", description = "新增会员账户流水信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody AccountFlow accountFlow) {
        // 调用会员账户流水服务层接口保存会员账户流水信息
        accountFlowService.save(accountFlow);
        return ApiResponse.success();
    }

    /**
     * 修改会员账户流水
     *
     * @param accountFlow 会员账户流水实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改会员账户流水", description = "修改会员账户流水信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody AccountFlow accountFlow) {
        // 调用会员账户流水服务层接口修改会员账户流水信息
        accountFlowService.updateById(accountFlow);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除会员账户流水
     *
     * @param id 会员账户流水主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除会员账户流水", description = "根据Id删除会员账户流水信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "会员账户流水Id") @PathVariable("id") Long id) {
        // 调用会员账户流水服务层接口根据Id删除会员账户流水信息
        accountFlowService.removeById(id);
        return ApiResponse.success();
    }
}