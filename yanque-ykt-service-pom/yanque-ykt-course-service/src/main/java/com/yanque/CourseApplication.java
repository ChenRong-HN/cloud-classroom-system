package com.yanque;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 课程服务启动类
 *
 * @author cr
 */
@SpringBootApplication
@Slf4j
public class CourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class);
        log.info("用户信息服务启动成功");
    }
}
