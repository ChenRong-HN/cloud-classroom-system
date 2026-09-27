package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.ApiResponse;
import com.yanque.common.vo.BasicPageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.KillActivity;
import com.yanque.service.IKillActivityService;
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
 * 秒杀活动信息控制层接口
 *
 * @author cr
 */
@Tag(name = "秒杀活动信息管理", description = "秒杀活动信息接口")
@RestController
@RequestMapping("/kill/killActivity")
public class KillActivityController {

    // 注入秒杀活动信息服务层接口实现类
    @Resource
    private IKillActivityService killActivityService;

    /**
     * 查询秒杀活动信息列表
     *
     * @return 全局通用返回结果(秒杀活动信息所有数据})
     */
    @Operation(summary = "查询秒杀活动信息列表", description = "查询所有秒杀活动信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<KillActivity>> list() {
        // 调用秒杀活动信息服务层接口查询所有秒杀活动信息信息
        List<KillActivity> list = killActivityService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询秒杀活动信息
     *
     * @param id 秒杀活动信息主键Id
     * @return 全局通用返回结果(秒杀活动信息实体数据)
     */
    @Operation(summary = "根据Id查询秒杀活动信息", description = "根据主键Id查询秒杀活动信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<KillActivity> getById(@Parameter(description = "秒杀活动信息Id") @PathVariable("id") Long id) {
        // 调用秒杀活动信息服务层接口根据Id查询秒杀活动信息信息
        KillActivity entity = killActivityService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增秒杀活动信息
     *
     * @param killActivity 秒杀活动信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增秒杀活动信息", description = "新增秒杀活动信息信息")
    @PostMapping("/save")
    public ApiResponse<Void> save(@Valid @RequestBody KillActivity killActivity) {
        // 调用秒杀活动信息服务层接口保存秒杀活动信息信息
        killActivityService.save(killActivity);
        return ApiResponse.success();
    }

    /**
     * 修改秒杀活动信息
     *
     * @param killActivity 秒杀活动信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改秒杀活动信息", description = "修改秒杀活动信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody KillActivity killActivity) {
        // 调用秒杀活动信息服务层接口修改秒杀活动信息信息
        killActivityService.updateById(killActivity);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除秒杀活动信息
     *
     * @param id 秒杀活动信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除秒杀活动信息", description = "根据Id删除秒杀活动信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "秒杀活动信息Id") @PathVariable("id") Long id) {
        // 调用秒杀活动信息服务层接口根据Id删除秒杀活动信息信息
        killActivityService.removeById(id);
        return ApiResponse.success();
    }

    @PostMapping("/pagelist")
    public ApiResponse<ApiPageResponse<KillActivity>> selectPage(@RequestBody BasicPageVo basicPageVo){
        // 调用秒杀活动信息服务层接口分页查询秒杀活动信息
        return ApiResponse.success(killActivityService.pagelist(basicPageVo));
    }

    @PostMapping("/publish/{killActivityId}")
    public ApiResponse<Void> publish(@PathVariable Long killActivityId){
        killActivityService.publish(killActivityId);
        return ApiResponse.success();
    }
}