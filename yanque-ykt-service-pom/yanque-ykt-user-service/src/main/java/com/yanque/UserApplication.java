package com.yanque;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户信息服务启动类
 *
 * @author cr
 */
@SpringBootApplication
@Slf4j
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
        log.info("用户信息服务启动成功");
    }
}
