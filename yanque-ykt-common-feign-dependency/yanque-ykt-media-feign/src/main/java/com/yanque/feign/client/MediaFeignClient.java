package com.yanque.feign.client;

import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.MediaFile;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * media服务login的feign客户端
 *
 * @author cr
 */
@FeignClient(name = "yanque-ykt-media-service") // 使用feign客户端发送请求时，去nacos注册中心拉取yanque-ykt-media-service的服务实例
public interface MediaFeignClient {

    /**
     * 查询课程媒体文件列表
     *
     * @param courseId  课程Id
     * @param chapterId 章节Id
     * @return 全局通用返回结果(课程媒体文件列表数据)
     */
    @Operation(summary = "查询课程媒体文件列表", description = "查询课程媒体文件信息列表")
    @GetMapping("/media/mediaFile/selectMediaList/{courseId}/{chapterId}")
    public ApiResponse<List<MediaFile>> selectMediaList(@PathVariable Long courseId, @PathVariable Long chapterId);
}
