package com.example.demo1.controller;

import com.example.demo1.mapper.UserMapper;
import com.example.demo1.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("用户列表")
    @GetMapping("/hello")
    public List<User> hello() {
        List<User> users = userMapper.selectUserList();
        return users;
    }
}
