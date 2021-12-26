package com.example.demo1.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo1.LoginUserDto;
import com.example.demo1.service.ISysUserService;
import com.example.demo1.utils.ResultJson;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final ISysUserService sysUserService;

    public AuthController(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PostMapping(value = "/login")
    public ResultJson login(@RequestBody LoginUserDto userDto) {
        JSONObject token = sysUserService.login(userDto.getUserName(), userDto.getPassword());
        return ResultJson.ok(token);
    }
}
