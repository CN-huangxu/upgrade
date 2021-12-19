package com.example.demo1.controller;

import com.example.demo1.mapper.UserMapper;
import com.example.demo1.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@Api(tags="用户管理")
@RestController
@RequestMapping("/user")
public class DemoController {
    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @ApiOperation("用户列表")
    @GetMapping("/hello")
    public List<User> hello() {
        logger.debug("=====>测试日志debug级别打印<====");
        logger.info("=====>测试日志info级别打印<=====");
        logger.error("=====>测试日志error级别打印<====");
        logger.warn("=====>测试日志warn级别打印<=====");

        List<User> users = userMapper.selectUserList();
        return users;
    }
    
}