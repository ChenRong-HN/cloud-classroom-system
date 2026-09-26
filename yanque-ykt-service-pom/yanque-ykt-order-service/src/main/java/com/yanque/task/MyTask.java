package com.yanque.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务测试类
 *
 * @author cr
 */
@Slf4j
@Component
public class MyTask {
    /**
     * 定时任务1 fixedRate = 60000表示每隔60000毫秒(1分钟)执行1次
     */
    @Scheduled(fixedRate = 60000)
    public void runMethod1() {
        log.info("定时任务1执行了、当前的时间是 {}", DateUtil.now());
    }

    /**
     * 定时任务2 cron = "0 29 22 ? * *"表示每天晚上10点29分执行1次
     */
    @Scheduled(cron = "0 29 22 ? * *")
    public void runMethod2() {
        log.info("定时任务2执行了、当前的时间是 {}", DateUtil.now());
    }
}