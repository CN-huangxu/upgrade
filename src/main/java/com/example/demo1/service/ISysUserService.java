package com.example.demo1.service;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo1.entity.SysUser;


/**
 * @author Administrator
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 登录后台系统
     *
     * @param username
     * @param password
     * @return
     */
    JSONObject login(String username, String password);
}
