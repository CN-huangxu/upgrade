package com.example.demo1;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages= {"com.example.demo1.mapper"})
@SpringBootApplication
public class Demo1Application {
    private static final Logger logger = LoggerFactory.getLogger(Demo1Application.class);

    public static void main(String[] args) {
        logger.info("===============项目启动了===============");
        SpringApplication.run(Demo1Application.class, args);
        logger.info("===============启动成功了===============");
    }

}