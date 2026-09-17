package com.yanque.controller;

import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.MediaFile;
import com.yanque.service.IMediaFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程媒体文件控制层接口
 *
 * @author cr
 */
@Tag(name = "课程媒体文件管理", description = "课程媒体文件接口")
@RestController
@RequestMapping("/media/mediaFile")
public class MediaFileController {

    // 注入课程媒体文件服务层接口实现类
    @Resource
    private IMediaFileService mediaFileService;

    /**
     * 查询课程媒体文件列表
     *
     * @return 全局通用返回结果(课程媒体文件所有数据})
     */
    @Operation(summary = "查询课程媒体文件列表", description = "查询所有课程媒体文件信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<MediaFile>> list() {
        // 调用课程媒体文件服务层接口查询所有课程媒体文件信息
        List<MediaFile> list = mediaFileService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询课程媒体文件
     *
     * @param id 课程媒体文件主键Id
     * @return 全局通用返回结果(课程媒体文件实体数据)
     */
    @Operation(summary = "根据Id查询课程媒体文件", description = "根据主键Id查询课程媒体文件详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<MediaFile> getById(@Parameter(description = "课程媒体文件Id") @PathVariable("id") Long id) {
        // 调用课程媒体文件服务层接口根据Id查询课程媒体文件信息
        MediaFile entity = mediaFileService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增课程媒体文件
     *
     * @param mediaFile 课程媒体文件实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增课程媒体文件", description = "新增课程媒体文件信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody MediaFile mediaFile) {
        // 调用课程媒体文件服务层接口保存课程媒体文件信息
        mediaFileService.save(mediaFile);
        return ApiResponse.success();
    }

    /**
     * 修改课程媒体文件
     *
     * @param mediaFile 课程媒体文件实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改课程媒体文件", description = "修改课程媒体文件信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody MediaFile mediaFile) {
        // 调用课程媒体文件服务层接口修改课程媒体文件信息
        mediaFileService.updateById(mediaFile);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除课程媒体文件
     *
     * @param id 课程媒体文件主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除课程媒体文件", description = "根据Id删除课程媒体文件信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "课程媒体文件Id") @PathVariable("id") Long id) {
        // 调用课程媒体文件服务层接口根据Id删除课程媒体文件信息
        mediaFileService.removeById(id);
        return ApiResponse.success();
    }
}