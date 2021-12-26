package com.example.demo1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class Schedule {
    //3.添加定时任务
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=5000)
    private void configureTasks() {
        log.info("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
