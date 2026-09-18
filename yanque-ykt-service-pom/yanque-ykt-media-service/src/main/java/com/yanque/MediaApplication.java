package com.yanque;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 媒体服务启动类
 *
 * @author cr
 */
@SpringBootApplication
@Slf4j
public class MediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaApplication.class, args);
        log.info("媒体服务启动成功");
    }
}
