package com.yanque.feign.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * openfeign配置类
 *
 * @author cr
 */
@Configuration
@EnableFeignClients("com.yanque.feign.client")
public class FeignConfiguration {
}
