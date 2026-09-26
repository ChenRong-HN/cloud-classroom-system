package com.yanque;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 秒杀服务启动类
 *
 * @author cr
 */
@SpringBootApplication
@Slf4j
public class SecKillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class, args);
        log.info("秒杀服务启动成功");
    }
}
