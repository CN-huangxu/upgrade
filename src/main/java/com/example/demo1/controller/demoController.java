package com.example.demo1.controller;

import com.example.demo1.mapper.UserMapper;
import com.example.demo1.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class demoController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    public List<User> hello() {
        List<User> users = userMapper.selectUserList();
        return users;
    }
}
