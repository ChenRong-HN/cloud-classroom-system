package com.yanque.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j文档元数据、项目元数据声明
 *
 * @author cr
 */
@Configuration
public class Knife4jConfiguration {
    /**
     * 声明业务分组参数元数据
     */
    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder()
                .group("用户服务")        // 分组名称
                .pathsToMatch("/user/**") // 分组默认访问前缀
                .build();
    }

    /**
     * 声明OpenAPI参数
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("燕雀云课堂 - 用户服务API")   // 文档标题
                        .version("1.0.0")                       // 文档版本
                        .description("燕雀教育云课堂用户服务接口文档") // 文档描述
                        .contact(new Contact().name("cr"))); // 联系人信息
    }
}
