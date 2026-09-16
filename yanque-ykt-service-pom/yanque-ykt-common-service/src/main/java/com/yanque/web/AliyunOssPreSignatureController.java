package com.yanque.web;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.yanque.common.vo.ApiResponse;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云Oss对象存储预签名Web层
 *
 * @author cr
 */
@Slf4j
@RestController
@RequestMapping("/common/oss")
public class AliyunOssPreSignatureController {

    // 地域节点地址
    @Value("${aliyun.oss.endPoint}")
    private String endPoint;

    // 目标桶名称
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    // 地域名称
    @Value("${aliyun.oss.region}")
    private String region;

    // Oss客户端,默认在initOssClient方法中完成后初始化
    private OSS ossClient;

    /**
     * 初始化Oss客户端
     */
    @SneakyThrows
    @PostConstruct // 会在类初始化完成后执行
    public void initOssClient() {
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        ossClient = OSSClientBuilder.create()
                .endpoint(endPoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();
    }

    /**
     * 获取阿里云Oss预签名URL
     *
     * @param dir         文件在阿里云Oss的桶中的路径
     * @param fileSuffix  文件后缀名
     * @param contentType 文件MIME-TYPE内容类型
     * @return 阿里云Oss预签名URL
     */
    @Operation(summary = "获取阿里云Oss预签名URL", description = "获取阿里云Oss预签名URL")
    @GetMapping("/getPreSignUpload")
    public ApiResponse<Map<String, String>> getAliyunOssPreSignatureURL(@RequestParam String dir, @RequestParam String fileSuffix, @RequestParam String contentType) {
        // 通过UUID生成一个随机文件名称
        String objectName = dir.concat("/").concat(UUID.randomUUID().toString(true)).concat(fileSuffix);
        URL signedUrl = null;
        try {
            // 生成预签名URL请求对象 -> 之后基于URL上传文件 默认会上传到指定的Bucket,文件在桶中的名称就是指定的objectName,必须使用PUT方式
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.PUT);
            // 指定生成的预签名URL过期时间
            Date expiration = new Date(new Date().getTime() + 3600 * 1000L);
            request.setExpiration(expiration);
            // 通过HttpPut请求生成预签名URL
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", contentType);  // 本次请求的请求体数据是什么类型 MIME-TYPE
            request.setHeaders(headers);
            signedUrl = ossClient.generatePresignedUrl(request);
            Map<String, String> rMap = MapUtil.<String, String>builder().put("preSignUoLoadUrl", signedUrl.toString()).put("ossObjectKey", objectName).build();
            return ApiResponse.success(rMap);
        } catch (OSSException oe) {
            log.error("获取阿里云Oss预签名URL失败 失败原因 {}", oe.getMessage());
            throw new BusinessException(BusinessErrorType.ALIYUN_OSS_ERROR);
        }
    }
}
