package com.yanque;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 订单服务启动类
 *
 * @author cr
 */
@SpringBootApplication
@Slf4j
@EnableScheduling // 开启定时任务功能（正常业务建议使用MQ的延迟消息代替）
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        log.info("订单服务启动成功");
    }
}
